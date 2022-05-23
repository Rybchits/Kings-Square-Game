package game.app.domain.test;

import game.app.domain.Dictionary;
import game.app.domain.GameModel;
import game.app.domain.Player;
import game.app.domain.exceptions.InvalidSelectedCellException;
import game.app.domain.exceptions.SelectedCellNotInSequenceException;
import game.app.domain.exceptions.WordIsNotInDictionaryException;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.GameField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameLogicTest {
    @Test
    void startModel() {
        var playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "white"));
        var dict = new Dictionary("Test", words);
        dict.setAlphabet("abcdefghijklmnopqrstuvwxyz");

        var logicModel = new GameModel(new GameField(5, 5), dict, playerNames);
        logicModel.start();

        Assertions.assertEquals(logicModel.field().cell(2, 0).label(), 'w');
        Assertions.assertEquals(logicModel.field().cell(2, 1).label(), 'h');
        Assertions.assertEquals(logicModel.field().cell(2, 2).label(), 'i');
        Assertions.assertEquals(logicModel.field().cell(2, 3).label(), 't');
        Assertions.assertEquals(logicModel.field().cell(2, 4).label(), 'e');

        Assertions.assertFalse(dict.contains("white"));
        Assertions.assertNotNull(logicModel.activePlayer());
    }

    @Test
    void gameWasFinishedFieldAreFull() {
        var playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "white"));
        var dict = new Dictionary("Test", words);
        dict.setAlphabet("abcdefghijklmnopqrstuvwxyz");

        var field = new GameField(5, 5);
        int i = 0;
        for (Cell cell: field){
            field.setSymbolTo(cell.position(), Integer.toString(i % 10).charAt(0));
            i++;
        }

        var logicModel = new GameModel(field, dict, playerNames);
        Assertions.assertTrue(logicModel.determineEndOfGame());
    }

    @Test
    void gameWasFinishedFieldPlayersSkipped() {
        var playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "white"));
        var dict = new Dictionary("Test", words);
        dict.setAlphabet("abcdefghijklmnopqrstuvwxyz");

        var logicModel = new GameModel(new GameField(5, 5), dict, playerNames);
        logicModel.start();
        logicModel.activePlayer().skipCurrentTurn();
        logicModel.activePlayer().skipCurrentTurn();

        Assertions.assertTrue(logicModel.determineEndOfGame());
    }

    @Test
    void checkSequenceCellIsDefinedTrue() {
        var playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "whita"));
        var dict = new Dictionary("Test", words);
        dict.setAlphabet("abcdefghijklmnopqrstuvwxyz");

        var logicModel = new GameModel(new GameField(5, 5), dict, playerNames);
        logicModel.start();

        Player firstPlayer = logicModel.activePlayer();
        logicModel.activePlayer().setSelectedCell(logicModel.field().cell(3, 4));
        logicModel.activePlayer().setSelectedSymbol('c');

        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(3, 4));
        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(2, 4));
        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(2, 3));

        logicModel.activePlayer().defineSequenceCells();

        Assertions.assertNotSame(logicModel.activePlayer(), firstPlayer);
        Assertions.assertEquals(1, firstPlayer.getWordsOfPlayer().size());
        Assertions.assertFalse(logicModel.dictionary().contains("cat"));
        Assertions.assertEquals(logicModel.scoreCounter().getScorePlayerByName(firstPlayer.name()), 3);
    }

    @Test
    void checkSequenceCellIsDefinedFalse() {
        var playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "whita"));
        var dict = new Dictionary("Test", words);
        dict.setAlphabet("abcdefghijklmnopqrstuvwxyz");

        var logicModel = new GameModel(new GameField(5, 5), dict, playerNames);
        logicModel.start();

        logicModel.activePlayer().setSelectedCell(logicModel.field().cell(3, 4));
        logicModel.activePlayer().setSelectedSymbol('c');

        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(3, 4));
        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(2, 4));
        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(2, 3));
        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(2, 2));

        try {
            logicModel.activePlayer().defineSequenceCells();
            Assertions.fail();
        } catch (WordIsNotInDictionaryException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void invalidSelectedCellTest() {
        var playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "whita"));
        var dict = new Dictionary("Test", words);
        dict.setAlphabet("abcdefghijklmnopqrstuvwxyz");

        var logicModel = new GameModel(new GameField(5, 5), dict, playerNames);
        logicModel.start();


        try {
            logicModel.activePlayer().setSelectedCell(logicModel.field().cell(4, 4));
            Assertions.fail();
        } catch (InvalidSelectedCellException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void selectedCellNotInSequenceTest() {
        var playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "whita"));
        var dict = new Dictionary("Test", words);
        dict.setAlphabet("abcdefghijklmnopqrstuvwxyz");

        var logicModel = new GameModel(new GameField(5, 5), dict, playerNames);
        logicModel.start();

        logicModel.activePlayer().setSelectedCell(logicModel.field().cell(3, 4));
        logicModel.activePlayer().setSelectedSymbol('c');

        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(2, 4));
        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(2, 3));
        logicModel.activePlayer().addedCellInSequence(logicModel.field().cell(2, 2));

        try {
            logicModel.activePlayer().defineSequenceCells();
            Assertions.fail();
        } catch (SelectedCellNotInSequenceException e) {
            Assertions.assertTrue(true);
        }
    }
}
