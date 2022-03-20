package game.app.domain;

import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.LabeledCellSequence;
import game.app.domain.listeners.PlayerActionListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private final String _name;

    public Player(String name) { _name = name; }

    // Выбранная ячейка
    private Cell _selectedCell;

    public void setSelectedCell(Cell cell) {
        _selectedCell = cell;
        fireCellIsSelected();
    }

    public Cell getSelectedCell() { return _selectedCell; }


    // Выбранная буква
    private Character _selectedLabel;

    public void setSelectedLabel(Character label) {
        _selectedLabel = label;
        fireLabelIsSelected();
    }

    // Составленное в этом ходу слово из ячеек
    private LabeledCellSequence _currentSequence = new LabeledCellSequence();

    public void addedCellInSequence(Cell cell){
        _currentSequence.addCell(cell);
        fireAddedCellInSequence();
    }

    public void defineSequenceCells() { fireSequenceCellIsDefined(); }


    // Все составленные игроком слова
    private final ArrayList<LabeledCellSequence> _words = new ArrayList<>();

    public ArrayList<String> getWordsOfPlayer() {
        return (ArrayList<String>) _words.stream().map(LabeledCellSequence::getWordFromCells).toList();
    }

    public void addCurrentSequenceInWordsPlayer() {
        _words.add(_currentSequence);
        _currentSequence = null;
    }


    // Пропуск хода
    private boolean _prevTurnWasSkipped = false;

    public void setPrevTurnWasSkipped(boolean value) { _prevTurnWasSkipped = value; }

    public void skipCurrentTurn() {
        _selectedLabel = null;
        _selectedCell = null;
        _currentSequence = new LabeledCellSequence();
        _prevTurnWasSkipped = true;
        fireTurnIsSkipped();
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
