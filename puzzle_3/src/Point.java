import java.util.Objects;

public class Point {
    int x;
    int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //methods for deep comparing two objects
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        else if(obj instanceof Point) {
            Point other = (Point) obj;
            if(Objects.equals(this.x, other.x) && Objects.equals(this.y, other.y)) {
                return true;
            }
            return false;
        }
        else return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
