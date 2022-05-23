package game.app.domain.exceptions;

public class PlayerNotExistingException extends CustomGameException{
    public PlayerNotExistingException(){ super("Игрока с таким именем не существует!"); }
}