package game.app.domain.exceptions;

public class WordIsNotInDictionaryException extends CustomGameException {
    public WordIsNotInDictionaryException(){ super("Слово отсутствует в словаре!"); }
}
