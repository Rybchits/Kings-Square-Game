package game.app.domain.exceptions;

public class InvalidComputerPlayerStrategyException extends CustomGameException{

    public InvalidComputerPlayerStrategyException() { super("Выбранная стратегия не является допустимой!"); }
}