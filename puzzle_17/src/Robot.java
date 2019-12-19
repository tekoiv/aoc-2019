public class Robot {
    int x, y;
    char dir;
    public Robot(int x, int y, char dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public char getDir() {
        return dir;
    }

    public void setDir(char dir) {
        this.dir = dir;
    }
}
