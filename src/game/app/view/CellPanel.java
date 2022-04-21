package game.app.view;

import game.app.domain.gamefield.Cell;
import game.app.view.utils.AppStyles;

import javax.swing.*;
import java.util.Locale;

public class CellPanel extends JButton {
    public enum CellSelectionState { NOT_SELECTED, SELECTED_TO_WRITE_LETTER, SELECTED_TO_CREATE_WORD }

    private final Cell _cell;

    public CellPanel(Cell cell) {
        _cell = cell;
        setFont(AppStyles.SYMBOL_CELL_FONT);
        if (_cell.label() != null) setSymbol(_cell.label());
        setSelection(CellSelectionState.NOT_SELECTED);
        setFocusable(false);
    }

    public void setSelection(CellSelectionState state) {
        switch (state) {
            case NOT_SELECTED -> setBackground(AppStyles.PRIMARY_COLOR);
            case SELECTED_TO_WRITE_LETTER -> setBackground(AppStyles.SELECTED_TO_WRITE_LETTER_COLOR);
            case SELECTED_TO_CREATE_WORD -> setBackground(AppStyles.SELECTED_TO_CREATE_WORD_COLOR);
        }
    }

    public void setSymbol(Character symbol) { setText(symbol == null? "" : symbol.toString().toUpperCase(Locale.ROOT)); }
}
