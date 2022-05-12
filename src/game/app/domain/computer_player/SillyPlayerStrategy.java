package game.app.domain.computer_player;

import game.app.domain.Dictionary;
import game.app.domain.gamefield.Cell;
import game.app.domain.gamefield.Direction;
import game.app.domain.gamefield.GameField;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

// Составляет новое слово согласно словарю, но вставляет новый символ всегда в первую ячейку
public class SillyPlayerStrategy extends ComputerPlayerStrategy{

    public SillyPlayerStrategy(@NotNull GameField field, @NotNull Dictionary dictionary) {
        super(field, dictionary);
    }

    @Override
    ComputerPlayerTurn makeTurn() {
        var turn = new ComputerPlayerTurn();

        // Найти все смежные ячейки без символа
        ArrayList<Cell> availableCells = getAllAvailableCells();
        Collections.shuffle(availableCells);

        // Обойти все доступные ячейки
        for (Cell firstCell : availableCells) {
            var resultSequence = new ArrayList<Cell>();
            resultSequence.add(firstCell);
            ArrayList<String> allWords = _dict.getAllWords();
            allWords = allWords.stream().filter(word -> word.length() > 2).collect(Collectors.toCollection(ArrayList::new));

            resultSequence = findNewCellSequence((ArrayList<Cell>)resultSequence.clone(), allWords, turn);

            // Если последовательность была найдена
            if (resultSequence != null) {
                // Указать новую ячейку
                turn.setNewCell(firstCell);

                // Указать текущую последовательность
                turn.setSequence(resultSequence);

                // Вернуть ход
                return turn;
            }
        }

        // Иначе вернуть null
        return null;
    }

    private ArrayList<Cell> findNewCellSequence(ArrayList<Cell> prevSequence, ArrayList<String> prevWordsWithSubstr, ComputerPlayerTurn currentTurn) {

        // Если есть слово с текущим окончанием, вернуть последовательность и нужный символ
        String wordWithSuffix = firstWordWithSuffix(prevSequence, prevWordsWithSubstr);
        if (wordWithSuffix != null) {
            currentTurn.setSymbol(wordWithSuffix.charAt(0));
            return prevSequence;
        }

        var direction = Direction.north();
        ArrayList<Cell> newSequence;
        Cell lastCell = prevSequence.get(prevSequence.size() - 1);

        // Обойти все ячейки вокруг текущей
        for (int i = 0; i < 4; i++) {
            // Получить соседа по направлению
            Cell neighbor = lastCell.neighbor(direction);

            // Если сосед по данному направлению есть
            if (neighbor != null) {

                // Если соседняя ячейка имеет символ и она еще не в последовательности
                if (neighbor.label() != null && !prevSequence.contains(neighbor)) {
                    // Добавляем новую ячейку в последовательность
                    newSequence = (ArrayList<Cell>) prevSequence.clone();
                    newSequence.add(neighbor);

                    // Выбираем все слова у которых суффикс начинается на нужную последовательность
                    ArrayList<String> wordsWithSubstr = findAllWordsWithSuffix(newSequence, prevWordsWithSubstr);

                    // Если есть такие слова, ищем продолжение последовательности
                    if (!wordsWithSubstr.isEmpty())
                        newSequence = findNewCellSequence(newSequence, wordsWithSubstr, currentTurn);

                    if (newSequence != null && currentTurn.getSymbol() != null)
                        return newSequence;
                }
            }

            direction = direction.clockwise();
        }

        return null;
    }

    private ArrayList<String> findAllWordsWithSuffix(ArrayList<Cell> prevSequence, ArrayList<String> wordsWithSubstr) {
        String suffixStr = "";
        for (Cell cell : prevSequence.subList(1, prevSequence.size())) {
            suffixStr += cell.label();
        }

        var result = new ArrayList<String>();
        for (String word : wordsWithSubstr) {
            if (word.substring(1).startsWith(suffixStr)) {
                result.add(word);
            }
        }

        return result;
    }

    private String firstWordWithSuffix(ArrayList<Cell> prevSequence, ArrayList<String> wordsWithSubstr) {
        String suffixStr = "";
        for (Cell cell : prevSequence.subList(1, prevSequence.size())) {
            suffixStr += cell.label();
        }

        for (String word : wordsWithSubstr) {
            if (word.substring(1).equals(suffixStr)) {
                return word;
            }
        }
        return null;
    }
}
