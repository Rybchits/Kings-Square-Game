package game.app.domain;

import game.app.domain.exceptions.UnableAddCellInSequenceException;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.LabeledCellSequence;
import game.app.domain.listeners.PlayerActionListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    public Player(String name) { _name = name; }

    private final String _name;

    public String name() { return _name; }

    // Выбранная ячейка
    private Cell _selectedCell;

    public void setSelectedCell(Cell cell) {
        _selectedCell = cell;
        fireCellIsSelected();
    }

    public Cell getSelectedCell() { return _selectedCell; }


    // Выбранная буква
    private Character _selectedSymbol;

    public Character getSelectedSymbol() { return _selectedSymbol; }

    public void setSelectedSymbol(Character label) {
        _selectedSymbol = label;
        fireLabelIsSelected();
    }

    // Составленное в этом ходу слово из ячеек
    private LabeledCellSequence _currentSequence = new LabeledCellSequence();

    public void addedCellInSequence(Cell cell){
        // Если можно добавить - добавляем и уведомляем, иначе исключение
        if (_currentSequence.canAddCell(cell)){
            _currentSequence.addCell(cell);
            fireAddedCellInSequence();
        } else {
            throw new UnableAddCellInSequenceException();
        }
    }

    public LabeledCellSequence currentSequence() { return _currentSequence; }

    public void setCurrentSequence(LabeledCellSequence value) { _currentSequence = value; }

    public void defineSequenceCells() { fireSequenceCellIsDefined(); }


    // Все составленные игроком слова
    private final ArrayList<LabeledCellSequence> _words = new ArrayList<>();

    public List<String> getWordsOfPlayer() {
        return _words.stream().map(LabeledCellSequence::getWordFromCells).toList();
    }

    public void addCurrentSequenceInWordsPlayer() {
        _words.add(_currentSequence);
        _currentSequence = null;
    }


    // Пропуск хода
    private boolean _prevTurnWasSkipped = false;

    public boolean prevTurnWasSkipped() { return _prevTurnWasSkipped; }

    public void setPrevTurnWasSkipped(boolean value) { _prevTurnWasSkipped = value; }

    public void skipCurrentTurn() {
        resetCurrentTurn();
        _prevTurnWasSkipped = true;
        fireTurnIsSkipped();
    }

    public void resetCurrentTurn() {
        _selectedSymbol = null;
        _selectedCell = null;
        _currentSequence = new LabeledCellSequence();
    }


    // Уведомления слушателей игрока
    private final Set<PlayerActionListener> _listeners = new HashSet<>();

    public void addPlayerActionListener(PlayerActionListener l) {
        _listeners.add(l);
    }

    public void removePlayerActionListener(PlayerActionListener l) {
        _listeners.remove(l);
    }

    private void fireTurnIsSkipped() {
        _listeners.forEach( listener -> listener.turnIsSkipped(this));
    }

    private void fireLabelIsSelected() {
        _listeners.forEach( listener -> listener.labelIsSelected(this));
    }

    private void fireCellIsSelected() {
        _listeners.forEach( listener -> listener.cellIsSelected(this));
    }

    private void fireSequenceCellIsDefined() {
        _listeners.forEach( listener -> listener.sequenceCellIsDefined(this));
    }

    private void fireAddedCellInSequence() {
        _listeners.forEach( listener -> listener.addedCellInSequence(this));
    }
}
