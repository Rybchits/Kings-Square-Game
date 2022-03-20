package game.app.domain;

import game.app.domain.exceptions.InvalidSelectedCellException;
import game.app.domain.exceptions.SelectedCellNotInSequenceException;
import game.app.domain.exceptions.WordIsNotInDictionaryException;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.GameField;
import game.app.domain.listeners.GameModelEvent;
import game.app.domain.listeners.GameModelListener;
import game.app.domain.listeners.PlayerActionListener;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;

public class GameModel {

    public GameModel(@NotNull GameField field, @NotNull Dictionary dictionary, @NotNull ArrayList<String> namesPlayers) {
        _field = field;
        _dictionary = dictionary;
        _scoreCounter = new ScoreCounter(namesPlayers);

        for (String name: namesPlayers ) {
            PlayerObserver observer = new PlayerObserver();
            Player player = new Player(name);
            player.addPlayerActionListener(observer);
            _playerList.add(player);
        }
    }

    // Игроки данной сессии
    private int _activePlayer;
    private ArrayList<Player> _playerList = new ArrayList<>();


    // Игровое поле
    private final GameField _field;
    public GameField field() { return _field; }


    // Словарь допустимых слов сессии
    private final Dictionary _dictionary;
    public Dictionary dictionary() { return _dictionary; }


    // Счет игроков
    private final ScoreCounter _scoreCounter;
    public ScoreCounter scoreCounter() { return _scoreCounter; }


    // Запуск игры
    public void start() {
        // Определяем первого игрока
        _activePlayer = getIndexRandomPlayer();

        // Устанавливаем первое слово в поле по центру
        String startWord = _dictionary.getRandomWordByLength(_field.width());
        _field.setWordInCenterRow(startWord);
        _dictionary.removeWord(startWord);

        firePlayerExchanged();
    }

    // Определение окончания игры
    public boolean determineEndOfGame() {
        boolean fieldIsFull = true;
        for (Cell cell : _field) { fieldIsFull &= cell.label() != null;}

        boolean playersAreSkipped = true;
        for (Player player : _playerList) { playersAreSkipped &= player.prevTurnWasSkipped(); }

        return fieldIsFull || playersAreSkipped;
    }


    // Передача хода другому игроку
    public Player activePlayer(){
        return _playerList.get(_activePlayer);
    }

    private void exchangePlayer() {
        _activePlayer++;
        if(_activePlayer >= _playerList.size())  _activePlayer = 0;

        activePlayer().setSelectedCell(null);
        activePlayer().setSelectedSymbol(null);
        activePlayer().setCurrentSequence(null);
        firePlayerExchanged();
    }

    private int getIndexRandomPlayer() { return (int)(Math.random() * _playerList.size()); }


    // Логика наблюдения за действиями игрока
    private class PlayerObserver implements PlayerActionListener{

        @Override
        public void turnIsSkipped(Player player) {
            // Проверить окончание игры
            if (determineEndOfGame()) fireGameFinished();

            // Переход хода
            exchangePlayer();
        }

        @Override
        public void labelIsSelected(Player player) {
            // Проверка метки в алфавите. Перерисовать поле
            _field.setSymbolTo(player.getSelectedCell().position(), player.getSelectedSymbol());
        }

        @Override
        public void cellIsSelected(Player player) {
            if (player.getSelectedCell().label() != null)
                throw new InvalidSelectedCellException();

            // Перерисовать поле
        }

        @Override
        public void addedCellInSequence(Player player) {
            // Перерисовать поле
        }

        @Override
        public void sequenceCellIsDefined(Player player) {
            // Проверка наличия выбранной ячейки в последовательности
            if (!player.currentSequence().contains(player.getSelectedCell())){
                player.setCurrentSequence(null);
                throw new SelectedCellNotInSequenceException();
            }

            // Проверка наличия слова последовательности в словаре
            if (!_dictionary.contains(player.currentSequence().getWordFromCells())) {
                player.setCurrentSequence(null);
                throw new WordIsNotInDictionaryException();
            }

            // Удаление слова последовательности из словаря
            _dictionary.removeWord(player.currentSequence().getWordFromCells());

            // Добавление указанного слова игроку
            player.addCurrentSequenceInWordsPlayer();

            // Добавление очков игроку
            _scoreCounter.addScoresToPlayer(player.name(), player.currentSequence().getWordFromCells().length());

            // Проверить окончание игры
            if (determineEndOfGame()) fireGameFinished();

            // Переход хода
            exchangePlayer();
        }
    }


    // Уведомления слушателей событий игры
    private final ArrayList<GameModelListener> _gameListeners = new ArrayList<>();

    public void addGameListener(GameModelListener l) {
        _gameListeners.add(l);
    }

    public void removeGameListener(GameModelListener l) {
        _gameListeners.remove(l);
    }

    protected void fireGameFinished() {
        var event = new GameModelEvent(this);
        event.setPlayer(activePlayer());
        event.setScore(_scoreCounter);
        for (GameModelListener listener : _gameListeners) { listener.gameFinished(event); }

        System.out.println("Игра закончилась!" + System.lineSeparator());
    }

    protected void firePlayerExchanged() {
        var event = new GameModelEvent(this);
        event.setPlayer(activePlayer());
        event.setScore(_scoreCounter);
        for (GameModelListener listener : _gameListeners) { listener.playerExchanged(event); }

        System.out.println("Ход был передан другому игроку" + System.lineSeparator());
    }

    // Todo создание списка слушателей игрока
}
