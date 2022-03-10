package game.app.domain.gamefield;

import org.jetbrains.annotations.NotNull;

public class CellPosition {
    private final int _row;
    private final int _column;

    public int row() { return _row; }

    public int column() { return _column; }

    private void validate(int row, int col) {
        if(row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
    }

    public CellPosition(int row, int col) {
        validate(row, col);

        _row = row;
        _column = col;
    }

    public boolean isNeighbor(@NotNull CellPosition other) {

        int row_delta = Math.abs( row() - other.row() );
        int col_delta = Math.abs( column() - other.column() );

        return  (row_delta == 0 && col_delta == 1) || (row_delta == 1 && col_delta == 0);
    }
}
