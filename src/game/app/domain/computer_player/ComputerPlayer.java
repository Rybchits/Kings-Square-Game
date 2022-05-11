package game.app.domain.computer_player;

import game.app.domain.Player;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.LabeledCellSequence;
import org.jetbrains.annotations.NotNull;

public class ComputerPlayer extends Player {
    public static String COMPUTER_PLAYER_NAME = "Компьютерный бот";

    private ComputerPlayerStrategy _strategy;

    public ComputerPlayer(@NotNull ComputerPlayerStrategy strategy) {
        super(COMPUTER_PLAYER_NAME);
        _strategy = strategy;
    }

    public void makeTurnByStrategy() {
        ComputerPlayerTurn currentTurn = _strategy.makeTurn();

        if (currentTurn == null || currentTurn.getSequence().isEmpty()) {
            skipCurrentTurn();
        } else {
            setSelectedCell(currentTurn.getNewCell());
            setSelectedSymbol(currentTurn.getSymbol());

            var newSequence = new LabeledCellSequence();
            for (Cell cell: currentTurn.getSequence()) { newSequence.addCell(cell); }
            setCurrentSequence(newSequence);

            defineSequenceCells();
        }
    }

    void setStrategy(@NotNull ComputerPlayerStrategy strategy) {
        _strategy = strategy;
    }
}
