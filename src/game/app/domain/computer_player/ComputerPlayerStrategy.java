package game.app.domain.computer_player;

import game.app.domain.Dictionary;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.Direction;
import game.app.domain.gamefield.GameField;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class ComputerPlayerStrategy {
    protected final GameField _field;
    protected final Dictionary _dict;

    public ComputerPlayerStrategy(@NotNull GameField field, @NotNull Dictionary dictionary) {
        _field = field;
        _dict = dictionary;
    }

    abstract ComputerPlayerTurn makeTurn();


    protected ArrayList<Cell> getAllAvailableCells() {
        var availableCells = new ArrayList<Cell>();

        for (Cell cell : _field) {
            if (cell.label() == null) {
                var direction = Direction.north();
                boolean neighborHasLabel = false;

                for (int i = 0; i < 4; i++) {
                    Cell neighbor = cell.neighbor(direction);
                    neighborHasLabel |= neighbor != null && neighbor.label() != null;
                    direction = direction.clockwise();
                }
                if (neighborHasLabel) availableCells.add(cell);
            }
        }

        return availableCells;
    }
}
