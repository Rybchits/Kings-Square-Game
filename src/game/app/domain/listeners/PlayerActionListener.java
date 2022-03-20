package game.app.domain.listeners;

import game.app.domain.Player;
import java.util.EventListener;

public interface PlayerActionListener extends EventListener {
    void turnIsSkipped(Player player);
    void labelIsSelected(Player player);
    void cellIsSelected(Player player);
    void sequenceCellIsDefined(Player player);
    void addedCellInSequence(Player player);
}
