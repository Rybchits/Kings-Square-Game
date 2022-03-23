package game.app.domain;

import game.app.domain.exceptions.InvalidFieldSideException;
import game.app.domain.exceptions.InvalidNameDictionaryException;
import game.app.domain.gamefield.GameField;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class GameBuilder {

    private static final String pathConfig = ".\\src\\resources\\builder_config.json";

    private static final String keyAvailableFieldSizes = "availableFieldSize";
    private static final String keyDefaultFieldSide = "defaultFieldSide";
    private static final String keyAvailableDictionaries = "availableDictionaries";

    // Единственный экземпляр класса
    private static GameBuilder instance;

    private GameBuilder() {

        // Чтение конфигураций из файла
        try(FileReader reader = new FileReader(pathConfig)) {

            JSONParser parser = new JSONParser();
            JSONObject rootJsonObject = (JSONObject) parser.parse(reader);

            // Получить конфигурации
            long defaultFieldSideValue = (long)rootJsonObject.get(keyDefaultFieldSide);
            JSONArray availableFieldSizesJson = (JSONArray)rootJsonObject.get(keyAvailableFieldSizes);
            JSONObject availableDictionariesJson = (JSONObject)rootJsonObject.get(keyAvailableDictionaries);

            // Устанавливаем список допустимых словарей
            for (String name : (Iterable<String>) availableDictionariesJson.keySet()) {
                _availableDictionaries.put(name, (String) availableDictionariesJson.get(name));
                if (nameSelectedDictionary == null) nameSelectedDictionary = name;
            }

            // Устанавливаем список допустимых значений размеров поля
            availableFieldSize.clear();
            for (Object el : availableFieldSizesJson){
                var value = (long)el;
                availableFieldSize.add((int)value);
            }

            // Устанавливаем размер поля по умолчанию
            _fieldSide = (int)defaultFieldSideValue;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

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
    private final HashMap<String, String> _availableDictionaries = new HashMap<>();
    private String nameSelectedDictionary;

    public void setDictionaryByName(String name) {
        if (!_availableDictionaries.containsKey(name)) {
            throw new InvalidNameDictionaryException();
        }
        nameSelectedDictionary = name;
    }

    public Set<String> getNamesDictionaries() { return _availableDictionaries.keySet(); }

    public Set<String> loadWordsFromSelectedDictionaries() {
        if (nameSelectedDictionary == null)
            throw new InvalidNameDictionaryException();

        HashSet<String> words = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(_availableDictionaries.get(nameSelectedDictionary)))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }


    // Порождающие методы
    public GameField buildGameField() {return new GameField(_fieldSide, _fieldSide); }

    public Dictionary buildDictionary() { return new Dictionary(nameSelectedDictionary, loadWordsFromSelectedDictionaries()); }

    public GameModel buildGameModel(@NotNull GameField field, @NotNull Dictionary dict) {
        return new GameModel(field, dict, _playerNames);
    }
}
