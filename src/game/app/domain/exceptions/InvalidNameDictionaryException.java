package game.app.domain.exceptions;

public class InvalidNameDictionaryException extends CustomGameException{
    public InvalidNameDictionaryException(){
        super("Недопустимый выбранный словарь!");
    }
}
