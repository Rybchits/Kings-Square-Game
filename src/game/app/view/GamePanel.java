package game.app.view;

import game.app.domain.GameModel;
import game.app.domain.Player;
import game.app.domain.listeners.GameModelEvent;
import game.app.domain.listeners.GameModelListener;
import game.app.domain.listeners.PlayerActionListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final GameFrame _owner;
    private GameFieldPanel _fieldView;
    private ArrayList<PlayersPanel> _playersPanels = new ArrayList<>();
    private GameControlPanel _gameControlPanel;
    private CellSequencePanel _cellSequencePanel;

    private GameModel _game;

    public GamePanel(@NotNull GameFrame gameFrame) {
        _owner = gameFrame;

        setLayout(new BorderLayout(30, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 50,20, 50));

        _fieldView = new GameFieldPanel(this);
        _cellSequencePanel = new CellSequencePanel(this);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));

        centerPanel.add(_fieldView, BorderLayout.CENTER);
        centerPanel.add(_cellSequencePanel, BorderLayout.NORTH);

        add(centerPanel, BorderLayout.CENTER);

        var panelPlayer1 = new PlayersPanel(this);
        _playersPanels.add(panelPlayer1);
        add(panelPlayer1, BorderLayout.WEST);

        var panelPlayer2 = new PlayersPanel(this);
        _playersPanels.add(panelPlayer2);
        add(panelPlayer2, BorderLayout.EAST);

        _gameControlPanel = new GameControlPanel(this);
        add(_gameControlPanel, BorderLayout.SOUTH);

        addKeyListener( new KeyController() );
        setVisible(true);
        setFocusable(true);
        requestFocus();
    }

    public GameModel currentGame() { return _game; }

    public void setGameModel(@NotNull GameModel model) {
        model.addGameListener(new GameObserver());
        model.addPlayerActionListener(new PlayerObserver());
        _game = model;
        _fieldView.initField();
        _playersPanels.get(0).setObservablePlayer(_game.players().get(0));
        _playersPanels.get(1).setObservablePlayer(_game.players().get(1));
    }

    public void update() {
        _fieldView.update();
        _playersPanels.forEach(PlayersPanel::update);
        _cellSequencePanel.update();
    }

    private class PlayerObserver implements PlayerActionListener {

        @Override
        public void turnIsSkipped(Player player) { }

        @Override
        public void labelIsSelected(Player player) { _fieldView.updateCell(player.getSelectedCell()); }

        @Override
        public void cellIsSelected(Player player) { _fieldView.updateCell(player.getSelectedCell());}

        @Override
        public void sequenceCellIsDefined(Player player) { }

        @Override
        public void addedCellInSequence(Player player) {
            _fieldView.updateCell(player.currentSequence().getLastCell());
            _cellSequencePanel.update();
        }
    }

    private class GameObserver implements GameModelListener{

        @Override
        public void gameFinished(GameModelEvent event) {
            // Todo вывести модальное окно об окончании игры
            _owner.toStartMenu();
        }

        @Override
        public void playerExchanged(GameModelEvent event) { update(); }
    }

    private class KeyController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) {
            boolean isTimeSetSymbol = _game.activePlayer().getSelectedCell() != null;
            isTimeSetSymbol &= _game.activePlayer().getSelectedSymbol() == null;

            if (isTimeSetSymbol && Character.isAlphabetic(e.getKeyChar())) {
                _game.activePlayer().setSelectedSymbol(e.getKeyChar());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
