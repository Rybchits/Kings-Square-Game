package game.app.domain.listeners;

import game.app.domain.Player;
import game.app.domain.ScoreCounter;

import java.util.EventObject;
import java.util.Map;

public class GameModelEvent extends EventObject {

    Player _player;
    ScoreCounter _score;

    public void setPlayer(Player p) {
        _player = p;
    }

    public Player player(){
        return _player;
    }

    public void setScore(ScoreCounter score){
        _score = score;
    }

    public ScoreCounter score(){
        return _score;
    }

    public GameModelEvent(Object source) {
        super(source);
    }
}
