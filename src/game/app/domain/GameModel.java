package game.app.domain;

import game.app.domain.exceptions.InvalidSelectedCellException;
import game.app.domain.exceptions.SelectedCellNotInSequenceException;
import game.app.domain.exceptions.WordIsNotInDictionaryException;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.Direction;
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
    private final ArrayList<Player> _playerList = new ArrayList<>();
    public ArrayList<Player> players() { return _playerList; }


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

        activePlayer().resetCurrentTurn();
        firePlayerExchanged();
    }

    private int getIndexRandomPlayer() { return (int)(Math.random() * _playerList.size()); }


    // Логика наблюдения за действиями игрока
    private class PlayerObserver implements PlayerActionListener{

        @Override
        public void turnIsSkipped(Player player) {
            // Проверить окончание игры
            if (determineEndOfGame())
                fireGameFinished();
            else {
                // Переход хода
                exchangePlayer();
                fireTurnIsSkipped(player);
            }
        }

        @Override
        public void labelIsSelected(Player player) {
            // Todo Проверка метки в алфавите. Проверка, что символ есть куда ставить
            if (player.getSelectedCell() != null)
                _field.setSymbolTo(player.getSelectedCell().position(), player.getSelectedSymbol());

            fireLabelIsSelected(player);
        }

        @Override
        public void cellIsSelected(Player player) {
            if (player.getSelectedCell() != null) {
                // Если у ячейки уже есть метка или она не имеет соседей с меткой
                boolean hasLabelCell = player.getSelectedCell().label() != null;
                boolean neighborHasLabel = false;

                var direction = Direction.north();
                for (int i = 0; i < 4; i++) {
                    Cell neighbor = player.getSelectedCell().neighbor(direction);
                    neighborHasLabel |= neighbor != null && neighbor.label() != null;
                    direction = direction.clockwise();
                }

                if (hasLabelCell || !neighborHasLabel){
                    player.setSelectedCell(null);
                    throw new InvalidSelectedCellException();
                }

                fireCellIsSelected(player);
            }
        }

        @Override
        public void addedCellInSequence(Player player) {
            fireAddedCellInSequence(player);
        }

        @Override
        public void sequenceCellIsDefined(Player player) {
            // Проверка наличия выбранной ячейки в последовательности
            if (!player.currentSequence().contains(player.getSelectedCell())){
                throw new SelectedCellNotInSequenceException();
            }

            // Проверка наличия слова последовательности в словаре
            if (!_dictionary.contains(player.currentSequence().getWordFromCells())) {
                throw new WordIsNotInDictionaryException();
            }

            // Удаление слова последовательности из словаря
            _dictionary.removeWord(player.currentSequence().getWordFromCells());

            // Добавление очков игроку
            _scoreCounter.addScoresToPlayer(player.name(), player.currentSequence().getWordFromCells().length());

            // Добавление указанного слова игроку
            player.addCurrentSequenceInWordsPlayer();

            // Считать, что игрок совершил ход
            player.setPrevTurnWasSkipped(false);

            // Проверить окончание игры
            if (determineEndOfGame())
                fireGameFinished();

            // Иначе переход хода
            else {
                exchangePlayer();
                fireSequenceCellIsDefined(player);
            }
        }
    }


    // Уведомления слушателей событий игры
    private final ArrayList<GameModelListener> _gameListenersList = new ArrayList<>();

    public void addGameListener(GameModelListener l) {
        _gameListenersList.add(l);
    }

    public void removeGameListener(GameModelListener l) {
        _gameListenersList.remove(l);
    }

    protected void fireGameFinished() {
        var event = new GameModelEvent(this);
        event.setPlayer(activePlayer());
        event.setScore(_scoreCounter);
        for (GameModelListener listener : _gameListenersList) { listener.gameFinished(event); }
    }

    protected void firePlayerExchanged() {
        var event = new GameModelEvent(this);
        event.setPlayer(activePlayer());
        event.setScore(_scoreCounter);
        for (GameModelListener listener : _gameListenersList) { listener.playerExchanged(event); }
    }


    // Уведомление слушателей действий игроков
    private final ArrayList<PlayerActionListener> _playerListenerList = new ArrayList<>();

    public void addPlayerActionListener(PlayerActionListener l) { _playerListenerList.add(l); }

    public void removePlayerActionListener(PlayerActionListener l) {
        _playerListenerList.remove(l);
    }

    private void fireTurnIsSkipped(Player player) {
        _playerListenerList.forEach( listener -> listener.turnIsSkipped(player));
    }

    private void fireLabelIsSelected(Player player) {
        _playerListenerList.forEach( listener -> listener.labelIsSelected(player));
    }

    private void fireCellIsSelected(Player player) {
        _playerListenerList.forEach( listener -> listener.cellIsSelected(player));
    }

    private void fireSequenceCellIsDefined(Player player) {
        _playerListenerList.forEach( listener -> listener.sequenceCellIsDefined(player));
    }

    private void fireAddedCellInSequence(Player player) {
        _playerListenerList.forEach( listener -> listener.addedCellInSequence(player));
    }
}
