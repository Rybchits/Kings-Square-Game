package game.app.domain.gamefield;

import java.util.HashMap;

public class GameField{

    public GameField(int height, int width) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }

        _width = width;
        _height = height;
        generateCellMatrix();
    }

    // Размеры поля
    private final int _width;
    private final int _height;

    public int width() {
        return _width;
    }

    public int height() {
        return _height;
    }


    // Ячейки поля
    private final HashMap<CellPosition, Cell> _cells = new HashMap<>();

    public Cell cell(CellPosition pos) {
        return _cells.get( pos );
    }

    public Cell cell(int row, int col) {
        return cell(new CellPosition(row, col));
    }

    private void generateCellMatrix() {
        // Заполняем поле ячейками
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                CellPosition pos = new CellPosition(row, col);
                _cells.put(pos, new Cell(pos));
            }
        }

        // Связываем ячейки
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {

                Cell cell = cell(row, col);

                if (height() > 1 && row < height() - 1) {
                    cell.setNeighbor(Direction.south(), cell(row + 1, col));
                }
                if (row > 0) {
                    cell.setNeighbor(Direction.north(), cell(row - 1, col));
                }
                if (width() > 1 && col < width() - 1) {
                    cell.setNeighbor(Direction.east(), cell(row, col + 1));
                }
                if (col > 0) {
                    cell.setNeighbor(Direction.west(), cell(row, col - 1));
                }
            }
        }
    }
}