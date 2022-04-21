package game.app.view;

import game.app.domain.GameBuilder;
import game.app.view.custom_panels.CustomActionButton;
import game.app.view.custom_panels.CustomTextField;
import game.app.view.utils.AppStyles;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Set;

public class SettingsMenuPanel extends JPanel {

    // Todo получать допустимые значения из builder
    private final GameFrame _owner;
    private final JTextField _firstPlayerNameField = new CustomTextField("Игрок 1");
    private final JTextField _secondPlayerNameField = new CustomTextField("Игрок 2");
    private final JComboBox<String> _dictionarySelect;
    private final JComboBox<String> _fieldSizeSelect;
    private final JComboBox<String> _alphabetSelect = new JComboBox<>(new String[]{"ru", "en"});

    public SettingsMenuPanel(@NotNull GameFrame owner) {
        _owner = owner;

        Set<String> dictionaries = GameBuilder.getInstance().getNamesDictionaries();
        _dictionarySelect = new JComboBox<>(Arrays.stream(dictionaries.toArray()).toArray(String[]::new));

        String[] fieldSizes = GameBuilder.getInstance().availableFieldSize
                .stream().map(e -> e.toString() + "x" + e.toString()).toArray(String[]::new);

        _fieldSizeSelect = new JComboBox<>(fieldSizes);

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15,5,15,5);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0f;
        constraints.gridwidth = 4;

        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel title = new JLabel("НОВАЯ ИГРА", SwingConstants.CENTER);
        title.setFont(AppStyles.TITLE_FONT);
        add(title, constraints);

        // Имена игроков
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        var firstPlayerLabel = new JLabel("Первый игрок");
        firstPlayerLabel.setFont(AppStyles.LABEL_FONT);
        add(firstPlayerLabel, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 1;
        add(_firstPlayerNameField, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 2;
        constraints.gridx = 0;
        var secondPlayerLabel = new JLabel("Второй игрок");
        secondPlayerLabel.setFont(AppStyles.LABEL_FONT);
        add(secondPlayerLabel, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 2;
        add(_secondPlayerNameField, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        var dictionaryLabel = new JLabel("Словарь слов");
        dictionaryLabel.setFont(AppStyles.LABEL_FONT);
        add(dictionaryLabel, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 2;
        add(_dictionarySelect, constraints);

        // Размер поля
        constraints.gridy = 4;
        constraints.gridx = 0;
        var fieldSizeLabel = new JLabel("Размер поля");
        fieldSizeLabel.setFont(AppStyles.LABEL_FONT);
        add(fieldSizeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        add(_fieldSizeSelect, constraints);

        // Алфавит
        constraints.gridy = 5;
        constraints.gridx = 0;
        var alphabetLabel = new JLabel("Алфавит");
        alphabetLabel.setFont(AppStyles.LABEL_FONT);
        add(alphabetLabel, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        add(_alphabetSelect, constraints);


        // Кнопка
        constraints.gridwidth = 3;
        constraints.gridy = 6;

        var _startButton = new CustomActionButton("СОЗДАТЬ");
        _startButton.addActionListener(e -> this.onClickStart());
        add(_startButton, constraints);
    }


    private void onClickStart() {
        String first = _firstPlayerNameField.getText();
        String second = _secondPlayerNameField.getText();

        GameBuilder.getInstance().setPlayerName(0, first);
        GameBuilder.getInstance().setPlayerName(1, second);
        GameBuilder.getInstance().setDictionaryByName(_dictionarySelect.getSelectedItem().toString());
        GameBuilder.getInstance().setFieldSide(
                GameBuilder.getInstance().availableFieldSize.get(_fieldSizeSelect.getSelectedIndex()));

        var dict = GameBuilder.getInstance().buildDictionary();
        var field = GameBuilder.getInstance().buildGameField();
        _owner.runGame(GameBuilder.getInstance().buildGameModel(field, dict));
        setVisible(false);
    }
}
