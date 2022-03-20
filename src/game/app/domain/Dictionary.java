package game.app.domain;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;

public class Dictionary {
    private final String _nameDictionary;
    private final Set<String> _availableWords;
    private final ArrayList<String> _removedWords = new ArrayList<>();

    public Dictionary(@NotNull String name, @NotNull Set<String> availableWords) {
        _nameDictionary = name;
        _availableWords = availableWords;
    }

    public String getName() { return _nameDictionary; }

    public void addNewWord(@NotNull String newWord) {

        if (!_availableWords.contains(newWord) && !_removedWords.contains(newWord)) {
            _availableWords.add(newWord);
        }
        // Todo добавить исключение
    }

    public void removeWord(@NotNull String removableWord) {
        if (_availableWords.remove(removableWord))
            _removedWords.add(removableWord);
    }

    public boolean contains(String word) {
        return _availableWords.contains(word);
    }

    public String getRandomWordByLength(int length) {
        ArrayList<String> filterSet =
                (ArrayList<String>)_availableWords.stream().filter(word -> word.length() == length).toList();

        return filterSet.size() != 0? filterSet.get((int)(Math.random() * filterSet.size())) : null;
    }

    public ArrayList<String> getAllWords() { return (ArrayList<String>)_availableWords.stream().toList(); }
}
