package game.app.domain.gamefield;

public class Direction {
    private final int _hours;

    private Direction(int hours) {
        hours = hours % 12;
        if (hours < 0) { hours += 12; }
        _hours = hours;
    }


    // Порождения
    public static Direction north() {
        return new Direction(0);
    }

    public static Direction south() {
        return new Direction(6);
    }

    public static Direction east() {
        return new Direction(3);
    }

    public static Direction west() {
        return new Direction(9);
    }


    // Повороты
    public Direction clockwise() {
        return new Direction(this._hours + 3);
    }

    public Direction anticlockwise() {
        return new Direction(this._hours - 3);
    }

    public Direction opposite() {
        return new Direction(this._hours + 6);
    }


    // Сравнения
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Direction)) {
            // если объект не совместим c Direction, возвращаем false
            return false;
        }

        Direction direct = (Direction) other;
        return _hours == direct._hours;
    }

    public boolean isOpposite(Direction other) {
        return this.opposite().equals(other);
    }
}
