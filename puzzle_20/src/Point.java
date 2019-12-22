import java.util.Objects;

public class Point {
    private int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //isolated method for finding adjacent points

    public Point[] getAdjacent() {
        Point[] result = new Point[4];
        result[0] = new Point(x, y + 1);
        result[1] = new Point(x, y - 1);
        result[2] = new Point(x - 1, y);
        result[3] = new Point(x + 1, y);
        return result;
    }

    //deep comparing methods required

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
