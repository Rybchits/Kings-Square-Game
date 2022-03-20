package game.app.domain;

import game.app.domain.exceptions.InvalidFieldSideException;
import game.app.domain.exceptions.InvalidNameDictionaryException;
import game.app.domain.gamefield.GameField;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class GameBuilder {
    // Единственный экземпляр класса
    private static GameBuilder instance;
    private GameBuilder(){}

    public static GameBuilder getInstance(){
        if(instance == null){ instance = new GameBuilder(); }
        return instance;
    }


    // Размеры поля
    public ArrayList<Integer> availableFieldSize = new ArrayList<>(Arrays.asList(5, 7, 9));

    private int _fieldSide = 5;

    public void setFieldSide(int side) {
        if (!availableFieldSize.contains(side)){
            throw new InvalidFieldSideException();
        }
        _fieldSide = side;
    }


    // Информация об игроках
    private final ArrayList<String> _playerNames = new ArrayList<>(Arrays.asList("Игрок 1", "Игрок 2"));

    public void addNewPlayer() { _playerNames.add(String.format("Игрок %d", _playerNames.size() + 1)); }

    public void removeLastPlayer() {
        if (_playerNames.size() - 1 >= 0)
            _playerNames.remove(_playerNames.size() - 1);
    }

    public void setPlayerName(int index, String name) { _playerNames.set(index, name); }

    public int getNumberPlayers() { return _playerNames.size(); }


    // Словари
    private HashMap<String, String> _availableDictionaries;
    private String nameSelectedDictionary;

    public void setDictionaryByName(String name) {
        if (!_availableDictionaries.containsKey(name)) {
            throw new InvalidNameDictionaryException();
        }
        nameSelectedDictionary = name;
    }

    public Set<String> getNamesDictionaries() { return _availableDictionaries.keySet(); }

    // Todo сделать загрузку слов из выбранного словаря
    public Set<String> loadWordsFromSelectedDictionaries() { return new HashSet<>(); }


    // Порождающие методы
    public GameField buildGameField() {return new GameField(_fieldSide, _fieldSide); }

    public Dictionary buildDictionary() { return new Dictionary(nameSelectedDictionary, loadWordsFromSelectedDictionaries()); }

    public GameModel buildGameModel(@NotNull GameField field, @NotNull Dictionary dict) {
        return new GameModel(field, dict, _playerNames);
    }
}
