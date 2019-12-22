import java.util.Objects;

public class Position {
    private Point point;
    private int level;
    public Position(Point point, int level) {
        this.point = point;
        this.level = level;
    }

    public Point getPoint() {
        return point;
    }

    public int getLevel() {
        return level;
    }

    //deep comparing methods required

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return (point.getX() == position.getPoint().getX()
        && point.getY() == position.getPoint().getY()
        && level == position.getLevel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, level);
    }
}
