package game.app.domain.exceptions;

public class InvalidSelectedCellException extends CustomGameException{
    public InvalidSelectedCellException(){ super("Указанная ячейка является недопустимой!"); }
}
