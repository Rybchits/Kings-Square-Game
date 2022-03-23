package game.app.domain.gamefield;

import game.app.domain.exceptions.UnableAddCellInSequenceException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LabeledCellSequence {
    private final ArrayList<Cell> _cells = new ArrayList<>();


    public void addCell(@NotNull Cell cell) {
        if (!canAddCell(cell)) {
            throw new UnableAddCellInSequenceException();
        }
        _cells.add(cell);
    }

    public boolean canAddCell(Cell cell) {
        return cell.label() != null && (getLastCell() == null || (getLastCell() != null && getLastCell().isNeighbor(cell)));
    }

    public String getWordFromCells() {
        StringBuilder result = new StringBuilder();
        for(Cell cell : _cells)
            result.append(cell.label());

        return result.toString();
    }

    public void clear() { _cells.clear(); }

    public boolean contains(Cell cell) { return _cells.contains(cell); }

    public int size() { return _cells.size(); }

    public Cell getLastCell() { return _cells.size() != 0? _cells.get(_cells.size() - 1) : null; }
}
