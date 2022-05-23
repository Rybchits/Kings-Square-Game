package game.app.view;

import game.app.domain.GameModel;
import game.app.view.utils.AppStyles;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame{
    private GamePanel _gamePanel;
    private final SettingsMenuPanel _menu;  // Окно настроек игры

    void setSettingsSizeWindow() {
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    void setGameSizeWindow() {
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }

    // Создается при запуске приложения
    public GameFrame() {
        setTitle("Балда");
        setSettingsSizeWindow();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        _menu = new SettingsMenuPanel(this);
        add(_menu);

        setVisible(true);
    }

    // Запуск процесса игры
    public void runGame(GameModel model) {
        _gamePanel = new GamePanel(this);
        setGameSizeWindow();

        model.start();
        _gamePanel.setGameModel(model);
        add(_gamePanel);

        _gamePanel.setVisible(true);
    }

    // После окончания игры, снова открывается меню
    public void toStartMenu() {
        _gamePanel.setVisible(false);
        setSettingsSizeWindow();

        _menu.setVisible(true);
    }
}
