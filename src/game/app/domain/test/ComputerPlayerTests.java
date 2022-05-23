package game.app.domain.test;

import game.app.domain.Dictionary;
import game.app.domain.GameModel;
import game.app.domain.factory.ComputerPlayerFactory;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.GameField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayerTests {
    @Test
    void PassiveComputerPlayerMakeTurn() {
        String name = "Player";
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "white"));
        var dict = new Dictionary("Test", words);

        var logicModel = new GameModel(new GameField(5, 5), dict, name, ComputerPlayerFactory::createPassiveComputerPlayer);
        logicModel.start();
        logicModel.activePlayer().skipCurrentTurn();

        Assertions.assertTrue(logicModel.determineEndOfGame());
    }

    @Test
    void DishonestComputerPlayerMakeTurn() {
        String name = "Player";
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "white"));
        var dict = new Dictionary("Test", words);

        var logicModel = new GameModel(new GameField(5, 5), dict, name, ComputerPlayerFactory::createDishonestComputerPlayer);
        logicModel.start();
        logicModel.activePlayer().skipCurrentTurn();

        int numberCellsWithSymbol = 0;
        for (Cell cell: logicModel.field()) {
            if (cell.label() != null) numberCellsWithSymbol++;
        }
        Assertions.assertTrue(numberCellsWithSymbol > 5);
    }

    @Test
    void DishonestComputerPlayerFillField() {
        String name = "Player";
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "luck", "tan", "white"));
        var dict = new Dictionary("Test", words);

        var logicModel = new GameModel(new GameField(5, 5), dict, name, ComputerPlayerFactory::createDishonestComputerPlayer);
        logicModel.start();

        while (!logicModel.determineEndOfGame()) {
            logicModel.activePlayer().skipCurrentTurn();
        }

        int numberCellsWithSymbol = 0;
        for (Cell cell: logicModel.field()) {
            if (cell.label() != null) numberCellsWithSymbol++;
        }
        Assertions.assertEquals(25, numberCellsWithSymbol);
    }

    @Test
    void SillyComputerPlayerMakeTurn() {
        String name = "Player";
        Set<String> words = new HashSet<>(Arrays.asList("ваня", "аня", "кол", "лодка"));
        var dict = new Dictionary("Test", words);

        var logicModel = new GameModel(new GameField(5, 5), dict, name, ComputerPlayerFactory::createSillyComputerPlayer);
        logicModel.start();

        logicModel.activePlayer().skipCurrentTurn();

        Assertions.assertEquals(2, logicModel.dictionary().getAllWords().size());
        Assertions.assertFalse(logicModel.dictionary().contains("кол"));
    }
}
