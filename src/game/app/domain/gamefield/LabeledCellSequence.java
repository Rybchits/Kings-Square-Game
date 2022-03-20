package game.app.domain.gamefield;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LabeledCellSequence {
    private final ArrayList<Cell> _cells = new ArrayList<>();


    public boolean addCell(@NotNull Cell cell) {
        if (cell.label() == null)
            return false;

        _cells.add(cell);
        return true;
    }


    public String getWordFromCells() {
        StringBuilder result = new StringBuilder();
        for(Cell cell : _cells)
            result.append(cell.label());

        return result.toString();
    }

    public void clear() { _cells.clear(); }


    public boolean contains(Cell cell) { return _cells.contains(cell); }
}
