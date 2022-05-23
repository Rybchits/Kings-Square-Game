package game.app.domain.test;

import game.app.domain.exceptions.UnableAddCellInSequenceException;
import game.app.domain.gamefield.GameField;
import game.app.domain.gamefield.LabeledCellSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LabeledCellSequenceTest {
    @Test
    void canAddFirstCellWithLabel() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        Assertions.assertTrue(currentSequence.canAddCell(field.cell(1, 0)));
    }

    @Test
    void canAddFirstCellWithoutLabel() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        Assertions.assertFalse(currentSequence.canAddCell(field.cell(2, 0)));
    }

    @Test
    void canAddSecondCellWithoutLabel() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        currentSequence.addCell(field.cell(1, 0));
        Assertions.assertFalse(currentSequence.canAddCell(field.cell(2, 0)));
    }

    @Test
    void canAddSecondCellWithLabel() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        currentSequence.addCell(field.cell(1, 0));
        Assertions.assertTrue(currentSequence.canAddCell(field.cell(1, 1)));
    }

    @Test
    void canAddSecondCellWithLabelButNotNeighbor() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        currentSequence.addCell(field.cell(1, 0));
        Assertions.assertFalse(currentSequence.canAddCell(field.cell(1, 2)));
    }

    @Test
    void checkAddInvalidCell() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        currentSequence.addCell(field.cell(1, 0));
        try {
            currentSequence.addCell(field.cell(1, 2));
            Assertions.fail();
        } catch (UnableAddCellInSequenceException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void checkAddNullCell() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        currentSequence.addCell(field.cell(1, 0));
        try {
            currentSequence.addCell(field.cell(6, 2));
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void checkSizeSequenceCell() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        currentSequence.addCell(field.cell(1, 0));
        currentSequence.addCell(field.cell(1, 1));
        currentSequence.addCell(field.cell(1, 2));
        currentSequence.addCell(field.cell(1, 3));
        currentSequence.addCell(field.cell(1, 4));

        Assertions.assertEquals(currentSequence.size(), 5);
    }

    @Test
    void checkSizeEmptySequenceCell() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        Assertions.assertEquals(currentSequence.size(), 0);
    }

    @Test
    void checkGetLastCellAtSequenceCell() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        currentSequence.addCell(field.cell(1, 0));
        currentSequence.addCell(field.cell(1, 1));
        currentSequence.addCell(field.cell(1, 2));
        currentSequence.addCell(field.cell(1, 3));
        currentSequence.addCell(field.cell(1, 4));

        Assertions.assertEquals(currentSequence.getLastCell(), field.cell(1, 4));
    }

    @Test
    void checkGetWordSequenceCell() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        currentSequence.addCell(field.cell(1, 0));
        currentSequence.addCell(field.cell(1, 1));
        currentSequence.addCell(field.cell(1, 2));
        currentSequence.addCell(field.cell(1, 3));
        currentSequence.addCell(field.cell(1, 4));

        Assertions.assertEquals(currentSequence.getWordFromCells(), "WHITE");
    }

    @Test
    void checkGetWordFromEmptySequenceCell() {
        var field = new GameField(3, 5);
        field.setWordInCenterRow("WHITE");

        var currentSequence = new LabeledCellSequence();
        Assertions.assertEquals(currentSequence.getWordFromCells(), "");
    }
}
