package game.app.domain.listeners;

import java.util.EventListener;

public interface GameModelListener extends EventListener {
    void gameFinished(GameModelEvent event);
    void playerExchanged(GameModelEvent event);
}
