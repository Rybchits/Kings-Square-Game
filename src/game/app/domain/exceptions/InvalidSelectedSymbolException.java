package game.app.domain.exceptions;

public class InvalidSelectedSymbolException extends CustomGameException{
    public InvalidSelectedSymbolException(){ super("Указанный символ отсутствует в словаре!"); }
}
