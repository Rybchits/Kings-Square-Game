package game.app.domain.test;

import game.app.domain.ScoreCounter;
import game.app.domain.exceptions.PlayerNotExistingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class ScoreCounterTest {

    @Test
    void addScoreToPlayerCounter() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        var counter = new ScoreCounter(playerNames);
        counter.addScoresToPlayer("Player 1", 1);
        Assertions.assertEquals(counter.getScorePlayerByName("Player 1"), 1);
    }

    @Test
    void addScoreToNotExistingPlayerCounter() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        var counter = new ScoreCounter(playerNames);
        try {
            counter.addScoresToPlayer("Player 2", 1);
            Assertions.fail();
        } catch (PlayerNotExistingException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void checkGetMaxPlayer() {
        ArrayList<String> playerNames = new ArrayList<>(Arrays.asList("Player 1", "2 Игрок"));
        var counter = new ScoreCounter(playerNames);
        counter.addScoresToPlayer("Player 1", 1);
        counter.addScoresToPlayer("2 Игрок", 3);
        Assertions.assertEquals(counter.getNamePlayersWithMaxScore().get(0), "2 Игрок");
    }
}
