package game.app.domain.factory;

import game.app.domain.Dictionary;
import game.app.domain.computer_player.ComputerPlayerStrategy;
import game.app.domain.computer_player.DishonestPlayerStrategy;
import game.app.domain.computer_player.PassivePlayerStrategy;
import game.app.domain.exceptions.InvalidComputerPlayerStrategyException;
import game.app.domain.gamefield.GameField;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class ComputerStrategyFactory {
    private final GameField _field;
    private final Dictionary _dict;

    public ComputerStrategyFactory(@NotNull GameField field, @NotNull Dictionary dictionary) {
        _field = field;
        _dict = dictionary;
    }

    private ArrayList<ComputerPlayerStrategy> strategies = new ArrayList<>();

    public ComputerPlayerStrategy createStrategyByName(String name) {
        boolean alreadyHasStrategy = strategies.stream()
                .map(strategy -> (Objects.equals(strategy.getName(), name))).reduce(false, (a, b) -> a || b);

        if (alreadyHasStrategy) {
            return strategies.stream()
                    .filter(strategy -> (Objects.equals(strategy.getName(), name))).findFirst().orElse(null);
        } else {
            ComputerPlayerStrategy strategy = switch (name) {
                case "Пассивный" -> new PassivePlayerStrategy(_field, _dict);
                case "Нечестный" -> new DishonestPlayerStrategy(_field, _dict);
                default -> throw new InvalidComputerPlayerStrategyException();
            };
            strategies.add(strategy);
            return strategy;
        }
    }
}
