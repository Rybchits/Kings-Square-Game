package game.app.domain;

import game.app.domain.exceptions.UnableAddWordToDictionaryException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Dictionary {
    private final String _nameDictionary;
    private final Set<String> _availableWords;
    private final ArrayList<String> _removedWords = new ArrayList<>();
    private String _alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public Dictionary(@NotNull String name, @NotNull Set<String> availableWords) {
        _nameDictionary = name;
        _availableWords = availableWords.stream().map(word -> word.toLowerCase(Locale.ROOT)).collect(Collectors.toSet());
    }

    public String getName() { return _nameDictionary; }

    public void addNewWord(@NotNull String newWord) {
        newWord = newWord.toLowerCase(Locale.ROOT);
        if (_availableWords.contains(newWord) || _removedWords.contains(newWord)) {
            throw new UnableAddWordToDictionaryException();
        }
        _availableWords.add(newWord);
    }

    public void removeWord(@NotNull String removableWord) {
        if (_availableWords.remove(removableWord))
            _removedWords.add(removableWord);
    }

    public boolean contains(String word) {
        word = word.toLowerCase(Locale.ROOT);
        return _availableWords.contains(word);
    }

    public String getAlphabet() { return _alphabet; }

    public void setAlphabet(String alphabet) { _alphabet = alphabet; }

    public String getRandomWordByLength(int length) {
        List<String> filterSet = _availableWords.stream().filter(word -> word.length() == length).toList();
        return filterSet.size() != 0? filterSet.get((int)(Math.random() * filterSet.size())) : null;
    }

    public boolean isAvailableSymbol(Character symbol) { return _alphabet.indexOf(symbol) != -1; }

    public ArrayList<String> getAllWords() { return new ArrayList<>(_availableWords); }
}
