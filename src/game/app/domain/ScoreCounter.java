package game.app.domain;

import game.app.domain.exceptions.PlayerNotExistingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ScoreCounter {
    private final HashMap<String, Integer> _scoresOfPlayer = new HashMap<>();

    public ScoreCounter(ArrayList<String> namesPlayers) {
        namesPlayers.forEach(name -> _scoresOfPlayer.put(name, 0));
    }

    // Добавить очки игроку
    public void addScoresToPlayer(String playerName, int score) {
        if (_scoresOfPlayer.containsKey(playerName))
            _scoresOfPlayer.put(playerName, _scoresOfPlayer.get(playerName) + score);
        else
            throw new PlayerNotExistingException();
    }

    // Получить счет игрока
    public int getScorePlayerByName(String name) { return _scoresOfPlayer.get(name); }

    // Получить всех игроков с максимальным счетом
    public ArrayList<String> getNamePlayersWithMaxScore() {
        var winners = new ArrayList<String>();
        int maxValue = Collections.max(_scoresOfPlayer.values());

        for(HashMap.Entry<String, Integer> player : _scoresOfPlayer.entrySet()) {
            String playerName = player.getKey();
            Integer value = player.getValue();

            if (maxValue == value)
                winners.add(playerName);
        }
        return winners;
    }
}
