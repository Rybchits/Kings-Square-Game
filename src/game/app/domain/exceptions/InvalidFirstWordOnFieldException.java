package game.app.domain.exceptions;

public class InvalidFirstWordOnFieldException extends CustomGameException{
    public InvalidFirstWordOnFieldException(){
        super("Недопустимое первое слово на поле!");
    }
}
