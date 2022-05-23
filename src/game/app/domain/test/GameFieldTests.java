package game.app.domain.test;

import game.app.domain.exceptions.InvalidFieldSideException;
import game.app.domain.exceptions.InvalidFirstWordOnFieldException;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.CellPosition;
import game.app.domain.gamefield.GameField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameFieldTests {

    @Test
    void squareField() {
        var field = new GameField(5, 5);
        Assertions.assertEquals(field.width(), field.height());
    }

    @Test
    void rectangularField() {
        var field = new GameField(3, 5);
        Assertions.assertEquals(3, field.height());
        Assertions.assertEquals(5, field.width());
    }

    @Test
    void invalidHeightSizes() {
        try {
            var field = new GameField(-1, 3);
            Assertions.fail();
        } catch (InvalidFieldSideException e){
            Assertions.assertTrue(true);
        }
    }

    @Test
    void invalidWidthSizes() {
        try {
            var field = new GameField(3, -1);
            Assertions.fail();
        } catch (InvalidFieldSideException e){
            Assertions.assertTrue(true);
        }
    }

    @Test
    void setLabelInCellAtTheBegin() {
        var field = new GameField(3, 5);
        field.setSymbolTo(new CellPosition(0, 0), 'a');
        Assertions.assertEquals(field.cell(0, 0).label(), 'a');
    }

    @Test
    void setLabelInCellAtTheEnd() {
        var field = new GameField(3, 5);
        field.setSymbolTo(new CellPosition(field.height() - 1, field.width() - 1), 'a');
        Assertions.assertEquals(field.cell(2, 4).label(), 'a');
    }

    @Test
    void fullFieldGetCell() {
        var field = new GameField(3, 5);

        int i = 0;
        for (Cell cell: field){
            field.setSymbolTo(cell.position(), Integer.toString(i % 10).charAt(0));
            i++;
        }
        Assertions.assertEquals(field.cell(0, 0).label(), '0');
        Assertions.assertEquals(field.cell(1, 2).label(), '7');
        Assertions.assertEquals(field.cell(2, 4).label(), '4');
    }

    @Test
    void setLabelOnCellWithLabel() {
        var field = new GameField(3, 5);
        field.setSymbolTo(new CellPosition(1, 1), 'a');
        field.setSymbolTo(new CellPosition(1, 1), 'b');
        Assertions.assertEquals(field.cell(1, 1).label(), 'b');
    }

    @Test
    void setWordOnCenterRowWithOddHeight() {
        var field = new GameField(3, 5);

        field.setWordInCenterRow("WHITE");
        Assertions.assertEquals(field.cell(1, 0).label(), 'W');
        Assertions.assertEquals(field.cell(1, 1).label(), 'H');
        Assertions.assertEquals(field.cell(1, 2).label(), 'I');
        Assertions.assertEquals(field.cell(1, 3).label(), 'T');
        Assertions.assertEquals(field.cell(1, 4).label(), 'E');
    }

    @Test
    void setWordOnCenterRowWithEvenHeight() {
        var field = new GameField(4, 5);

        field.setWordInCenterRow("WHITE");
        Assertions.assertEquals(field.cell(2, 0).label(), 'W');
        Assertions.assertEquals(field.cell(2, 1).label(), 'H');
        Assertions.assertEquals(field.cell(2, 2).label(), 'I');
        Assertions.assertEquals(field.cell(2, 3).label(), 'T');
        Assertions.assertEquals(field.cell(2, 4).label(), 'E');
    }

    @Test
    void InvalidSettingWordOnField() {
        var field = new GameField(4, 5);

        try {
            field.setWordInCenterRow("YELLOW");
            Assertions.fail();
        } catch (InvalidFirstWordOnFieldException e) {
            Assertions.assertTrue(true);
        }
    }
}
