package game.app.domain.exceptions;

public class UnableAddCellInSequenceException extends CustomGameException{
    public UnableAddCellInSequenceException(){ super("Невозможно добавить данную ячейку в последовательность!"); }
}
