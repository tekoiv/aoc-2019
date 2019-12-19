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

    public char getNextPosition(char[][] maze) {
        int width = maze.length;
        int height = maze[0].length;
        if (dir == 'L') {
            if (this.x == 0) return '.';
            else return maze[this.x - 1][this.y];
        } else if (dir == 'R') {
            if (this.x == width - 1) return '.';
            else return maze[this.x + 1][this.y];
        } else if (dir == 'U') {
            if (this.y == 0) return '.';
            else return maze[this.x][this.y - 1];
        } else {
            if (this.y == height - 1) return '.';
            else return maze[this.x][this.y + 1];
        }
    }

    public void move() {
        if (dir == 'L') this.x--;
        else if (dir == 'R') this.x++;
        else if (dir == 'U') this.y--;
        else this.y++;
    }

    public char turn(char[][] maze) {
        int width = maze.length;
        int height = maze[0].length;
        if (dir == 'L') {
            if (this.y == 0) {
                setDir('D');
                return 'L';
            } else if (this.y == height - 1) {
                setDir('U');
                return 'R';
            } else {
                if (maze[this.x][this.y - 1] == '#') {
                    setDir('U');
                    return 'R';
                } else {
                    setDir('D');
                    return 'L';
                }
            }
        } else if (dir == 'R') {
            if (this.y == 0) {
                setDir('D');
                return 'R';
            } else if (this.y == height - 1) {
                setDir('U');
                return 'L';
            } else {
                if (maze[this.x][this.y - 1] == '#') {
                    setDir('U');
                    return 'L';
                } else {
                    setDir('D');
                    return 'R';
                }
            }
        } else if (dir == 'U') {
            if (this.x == 0) {
                setDir('R');
                return 'R';
            } else if (this.x == width - 1) {
                setDir('L');
                return 'L';
            } else {
                if (maze[this.x + 1][this.y] == '#') {
                    setDir('R');
                    return 'R';
                } else {
                    setDir('L');
                    return 'L';
                }
            }
        } else {
            if (this.x == 0) {
                setDir('R');
                return 'L';
            } else if (this.x == width - 1) {
                setDir('L');
                return 'R';
            } else {
                if (maze[this.x + 1][this.y] == '#') {
                    setDir('R');
                    return 'L';
                } else {
                    setDir('L');
                    return 'R';
                }
            }
        }
    }
}
