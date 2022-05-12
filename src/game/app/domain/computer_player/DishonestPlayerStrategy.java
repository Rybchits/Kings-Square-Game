package game.app.domain.computer_player;

import game.app.domain.Dictionary;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.Direction;
import game.app.domain.gamefield.GameField;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DishonestPlayerStrategy extends ComputerPlayerStrategy{

    public DishonestPlayerStrategy(@NotNull GameField field, @NotNull Dictionary dictionary) {
        super(field, dictionary);
    }

    @Override
    ComputerPlayerTurn makeTurn() {
        var turn = new ComputerPlayerTurn();

        // Найти все смежные ячейки без символа
        ArrayList<Cell> availableCells = getAllAvailableCells();

        if (availableCells.isEmpty()) return null;

        // Выбрать случайно любую из них
        Cell cell = availableCells.get((int)Math.floor(Math.random() * availableCells.size()));
        turn.setNewCell(cell);

        // Построить последовательность ячеек от 3 до 5 ячеек
        Character symbol = _dict.getAlphabet().charAt((int)Math.floor(Math.random() * _dict.getAlphabet().length()));
        turn.setSymbol(symbol);

        var resultSequence = new ArrayList<Cell>();
        resultSequence.add(cell);
        resultSequence = findNewCellSequence((ArrayList<Cell>)resultSequence.clone());

        // Если последовательность была найдена
        if (resultSequence != null) {

            // Получить составленное слово
            String newWord = "";

            for (Cell selectedCell : resultSequence) {
                // Если у добавленной ячейки есть символ, добавить его
                if (selectedCell.label() != null) newWord += selectedCell.label();

                // Если нет символа, добавить тот который был случайно выбран (это новая ячейка)
                else newWord += turn.getSymbol();
            }

            // Добавить слово в словарь
            _dict.addNewWord(newWord);

            // Указать текущую последовательность
            turn.setSequence(resultSequence);

            // Вернуть ход
            return turn;
        }

        // Иначе вернуть null
        return null;
    }


    private ArrayList<Cell> findNewCellSequence(ArrayList<Cell> prevSequence) {

        var direction = Direction.north();
        ArrayList<Cell> newSequence;
        Cell lastCell = prevSequence.get(prevSequence.size() - 1);

        // Обойти все ячейки вокруг текущей
        for (int i = 0; i < 4; i++) {
            // Получить соседа по направлению
            Cell neighbor = lastCell.neighbor(direction);

            // Если сосед по данному направлению есть
            if (neighbor != null) {
                newSequence = (ArrayList<Cell>) prevSequence.clone();

                // Если соседняя ячейка имеет символ и она еще не в последовательности
                if (neighbor.label() != null && !prevSequence.contains(neighbor)) {
                    newSequence.add(neighbor);
                    newSequence = findNewCellSequence(newSequence);
                }

                // Если последовательность требуемой длины (4)
                if (newSequence != null && newSequence.size() == 4) {
                    return newSequence;
                }
            }

            direction = direction.clockwise();
        }

        return null;
    }

}
