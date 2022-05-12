package game.app.domain.factory;

import game.app.domain.Dictionary;
import game.app.domain.computer_player.*;
import game.app.domain.exceptions.InvalidComputerPlayerStrategyException;
import game.app.domain.gamefield.GameField;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class ComputerPlayerFactory {
    private final GameField _field;
    private final Dictionary _dict;

    public ComputerPlayerFactory(@NotNull GameField field, @NotNull Dictionary dictionary) {
        _field = field;
        _dict = dictionary;
    }

    private ArrayList<ComputerPlayerStrategy> strategies = new ArrayList<>();

    private ComputerPlayerStrategy getStrategyByName(Class classStrategy) {
        boolean alreadyHasStrategy = strategies.stream()
                .map(strategy -> (Objects.equals(strategy.getClass(), classStrategy))).reduce(false, (a, b) -> a || b);

        if (alreadyHasStrategy) {
            return strategies.stream()
                    .filter(strategy -> (Objects.equals(strategy.getClass(), classStrategy))).findFirst().orElse(null);
        } else {
            ComputerPlayerStrategy strategy;
            if (classStrategy == PassivePlayerStrategy.class)
                    strategy = new PassivePlayerStrategy(_field, _dict);
            else if (classStrategy == DishonestPlayerStrategy.class)
                strategy = new DishonestPlayerStrategy(_field, _dict);
            else if (classStrategy == SillyPlayerStrategy.class)
                strategy = new SillyPlayerStrategy(_field, _dict);
            else
                throw new InvalidComputerPlayerStrategyException();

            strategies.add(strategy);
            return strategy;
        }
    }

    public ComputerPlayer createSillyComputerPlayer() {
        ComputerPlayerStrategy strategy = getStrategyByName(SillyPlayerStrategy.class);
        return new ComputerPlayer(strategy);
    }

    public ComputerPlayer createDishonestComputerPlayer() {
        ComputerPlayerStrategy strategy = getStrategyByName(DishonestPlayerStrategy.class);
        return new ComputerPlayer(strategy);
    }

    public ComputerPlayer createPassiveComputerPlayer() {
        ComputerPlayerStrategy strategy = getStrategyByName(PassivePlayerStrategy.class);
        return new ComputerPlayer(strategy);
    }
}
