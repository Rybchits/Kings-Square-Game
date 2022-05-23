package game.app.domain.test;

import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.CellPosition;
import game.app.domain.gamefield.Direction;
import game.app.domain.gamefield.GameField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTests {
    @Test
    void checkCellPosition() {
        var cell = new Cell(new CellPosition(1, 2));
        Assertions.assertEquals(cell.position(), new CellPosition(1, 2));
    }

    @Test
    void checkLeftUpCornerNeighbors() {
        var field = new GameField(5, 5);
        Assertions.assertNull(field.cell(0, 0).neighbor(Direction.north()));
        Assertions.assertNull(field.cell(0, 0).neighbor(Direction.west()));
        Assertions.assertEquals(field.cell(0, 0).neighbor(Direction.east()), field.cell(0, 1));
        Assertions.assertEquals(field.cell(0, 0).neighbor(Direction.south()), field.cell(1, 0));
    }

    @Test
    void checkLeftDownCornerNeighbors() {
        var field = new GameField(5, 5);
        Assertions.assertNull(field.cell(4, 0).neighbor(Direction.south()));
        Assertions.assertNull(field.cell(4, 0).neighbor(Direction.west()));
        Assertions.assertEquals(field.cell(4, 0).neighbor(Direction.east()), field.cell(4, 1));
        Assertions.assertEquals(field.cell(4, 0).neighbor(Direction.north()), field.cell(3, 0));
    }

    @Test
    void checkRightDownCornerNeighbors() {
        var field = new GameField(5, 5);
        Assertions.assertNull(field.cell(4, 4).neighbor(Direction.east()));
        Assertions.assertNull(field.cell(4, 4).neighbor(Direction.south()));
        Assertions.assertEquals(field.cell(4, 4).neighbor(Direction.north()), field.cell(3, 4));
        Assertions.assertEquals(field.cell(4, 4).neighbor(Direction.west()), field.cell(4, 3));
    }

    @Test
    void checkRightUpCornerNeighbors() {
        var field = new GameField(5, 5);
        Assertions.assertNull(field.cell(0, 4).neighbor(Direction.east()));
        Assertions.assertNull(field.cell(0, 4).neighbor(Direction.north()));
        Assertions.assertEquals(field.cell(0, 4).neighbor(Direction.south()), field.cell(1, 4));
        Assertions.assertEquals(field.cell(0, 4).neighbor(Direction.west()), field.cell(0, 3));
    }

    @Test
    void checkIsNeighbor() {
        var field = new GameField(5, 5);
        Assertions.assertTrue(field.cell(1, 1).isNeighbor(field.cell(1, 2)));
        Assertions.assertFalse(field.cell(1, 1).isNeighbor(field.cell(2, 2)));
        Assertions.assertFalse(field.cell(1, 1).isNeighbor(field.cell(1, 3)));
        Assertions.assertTrue(field.cell(1, 1).isNeighbor(field.cell(2, 1)));
    }
}
