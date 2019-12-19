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
        if(dir == 'L') return maze[this.x - 1][this.y];
        else if(dir == 'R') return maze[this.x + 1][this.y];
        else if(dir == 'U') return maze[this.x][this.y - 1];
        else return maze[this.x][this.y - 1];
    }

    public void move() {
        if(dir == 'L') this.x--;
        else if(dir == 'R') this.x++;
        else if(dir == 'U') this.y--;
        else this.y++;
    }

    public char turn(char[][] maze) {
        if(this.x == 0 || this.y == 0 || this.x == maze[0].length - 1 || this.y == maze.length - 1) {
            if(dir == 'L') {
                if(this.y == 0) {
                    setDir('D');
                    return 'L';
                } else if(this.y == maze.length - 1) {
                    setDir('U');
                    return 'R';
                }
            } else if(dir == 'R') {
                if(this.y == 0) {
                    setDir('D');
                    return 'R';
                } else if(this.y == maze.length - 1) {
                    setDir('U');
                    return 'L';
                }
            } else if(dir == 'U') {
                if(this.x == 0) {
                    setDir('R');
                    return 'R';
                } else if(this.x == maze[0].length - 1) {
                    setDir('L');
                    return 'L';
                }
            } else {
                if(this.x == 0) {
                    setDir('R');
                    return 'L';
                } else if(this.x == maze[0].length - 1) {
                    setDir('L');
                    return 'R';
                }
            }
        } else {
            if (dir == 'L') {
                if (maze[this.x][this.y - 1] == '#') {
                    setDir('U');
                    return 'R';
                } else {
                    setDir('D');
                    return 'L';
                }
            } else if (dir == 'R') {
                if (maze[this.x][this.y - 1] == '#') {
                    setDir('U');
                    return 'L';
                } else {
                    setDir('D');
                    return 'R';
                }
            } else if (dir == 'U') {
                if (maze[this.x + 1][this.y] == '#') {
                    setDir('R');
                    return 'R';
                } else {
                    setDir('L');
                    return 'L';
                }
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
        return 'R';
    }
}
