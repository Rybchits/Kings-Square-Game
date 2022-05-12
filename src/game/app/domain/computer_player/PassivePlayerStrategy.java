package game.app.domain.computer_player;

import game.app.domain.Dictionary;
import game.app.domain.gamefield.GameField;
import org.jetbrains.annotations.NotNull;

// Всегда пропускает ход
public class PassivePlayerStrategy extends ComputerPlayerStrategy{

    public PassivePlayerStrategy(@NotNull GameField field, @NotNull Dictionary dictionary) {
        super(field, dictionary);
    }

    @Override
    ComputerPlayerTurn makeTurn() {
        return null;
    }
}
