package game.app.domain.exceptions;

public class SelectedCellNotInSequenceException extends CustomGameException{
    public SelectedCellNotInSequenceException(){
        super("В указанной последовательности ячеек отсутствует выбранная в этом ходу!");
    }
}
