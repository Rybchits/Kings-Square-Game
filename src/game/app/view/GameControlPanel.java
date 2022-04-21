package game.app.view;

import game.app.domain.Player;
import game.app.view.custom_panels.CustomActionButton;

import javax.swing.*;
import java.awt.*;

public class GameControlPanel extends JPanel {
    GamePanel _owner;

    public final static Dimension BUTTON_ACTION_SIZE = new Dimension(240, 35);

    public GameControlPanel(GamePanel owner) {
        _owner = owner;
        setPreferredSize(new Dimension(_owner.getWidth(), 50));

        setLayout(new FlowLayout(FlowLayout.CENTER, 45, 5));
        JButton _skipButton = new CustomActionButton("Пропуск хода");
        _skipButton.setPreferredSize(BUTTON_ACTION_SIZE);
        _skipButton.addActionListener(e -> _skipTurnCurrentPlayer());
        add(_skipButton);

        JButton _cancelButton = new CustomActionButton("Отмена");
        _cancelButton.setPreferredSize(BUTTON_ACTION_SIZE);
        _cancelButton.addActionListener(e -> _resetActionsCurrentPlayer());
        add(_cancelButton);

        JButton _confirmButton = new CustomActionButton("Подтвердить ход");
        _confirmButton.setPreferredSize(BUTTON_ACTION_SIZE);
        _confirmButton.addActionListener(e -> _confirmCurrentSequence());
        add(_confirmButton);
    }

    private void _skipTurnCurrentPlayer() {
        _owner.currentGame().activePlayer().skipCurrentTurn();
    }

    private void _confirmCurrentSequence() {
        _owner.currentGame().activePlayer().defineSequenceCells();
    }

    private void _resetActionsCurrentPlayer() {
        Player active = _owner.currentGame().activePlayer();

        if (active.getSelectedCell() != null && active.getSelectedCell().label() != null) {
            _owner.currentGame().field().setSymbolTo(active.getSelectedCell().position(), null);
        }

        _owner.currentGame().activePlayer().resetCurrentTurn();
        _owner.update();
    }
}
