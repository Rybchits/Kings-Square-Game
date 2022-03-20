package game.app.domain.exceptions;

public class UnableAddWordToDictionaryException extends CustomGameException{
    public UnableAddWordToDictionaryException(){ super("Невозможно добавить слово в словарь!"); }
}
