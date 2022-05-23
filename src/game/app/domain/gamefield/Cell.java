package game.app.domain.gamefield;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cell {

    public Cell(@NotNull CellPosition position) {
        _pos = position;
    }

    // Метка
    private Character _label;

    public Character label(){ return _label; }

    void setLabel(Character newLabel) {
        _label = newLabel;
    }


    // Позиция
    private final CellPosition _pos;

    public CellPosition position() {
        return _pos;
    }


    // Соседи
    private final Map<Direction, Cell> _neighbors = new HashMap<>();

    public Cell neighbor(@NotNull Direction direct) {

        if(_neighbors.containsKey(direct)) {
            return _neighbors.get(direct);
        }

        return null;
    }

    public Map<Direction, Cell> neighbors() {
        return Collections.unmodifiableMap(_neighbors);
    }

    void setNeighbor(@NotNull Direction direct, Cell neighbor) {
        if(neighbor != this && !isNeighbor(neighbor)) {
            _neighbors.put(direct, neighbor);
            neighbor.setNeighbor(direct.opposite(), this);
        }
    }

    public boolean isNeighbor(Cell other) {
        return _neighbors.containsValue(other);
    }
}
