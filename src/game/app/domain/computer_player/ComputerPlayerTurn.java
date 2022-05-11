package game.app.domain.computer_player;

import game.app.domain.gamefield.Cell;

import java.util.ArrayList;

class ComputerPlayerTurn {
    private ArrayList<Cell> _sequenceCell = null;
    private Character _symbol = null;
    private Cell _newCell = null;

    public void setNewCell(Cell _newCell) { this._newCell = _newCell; }

    public void setSequence(ArrayList<Cell> _sequence) { this._sequenceCell = _sequence; }

    public void setSymbol(Character _symbol) { this._symbol = _symbol; }

    public Cell getNewCell() { return _newCell; }

    public Character getSymbol() { return _symbol; }

    public ArrayList<Cell> getSequence() { return _sequenceCell; }
}
