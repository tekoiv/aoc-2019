public class Ball {
    char direction;
    int x;
    int y;
    public Ball(char direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this. y = y;
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

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }
}
