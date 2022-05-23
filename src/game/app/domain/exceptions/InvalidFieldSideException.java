package game.app.domain.exceptions;

public class InvalidFieldSideException extends CustomGameException{
    public InvalidFieldSideException(){
        super("Недопустимый размер поля!");
    }
}
