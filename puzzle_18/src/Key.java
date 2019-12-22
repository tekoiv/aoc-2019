public class Key {
    int x, y;
    char value;
    public Key(int x, int y, char value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
