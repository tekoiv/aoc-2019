import java.util.ArrayList;

public class Robot {

    char panelColor, direction;
    int x, y;

    public Robot(char panelColor, char direction, int x, int y) {
        this.panelColor = panelColor;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public char getPanelColor() {
        return panelColor;
    }

    public void setPanelColor(char panelColor) {
        this.panelColor = panelColor;
    }

    public char getDir() {
        return direction;
    }

    public void setDir(char dir) {
        this.direction = dir;
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

    public void turn(char direction, ArrayList<Point> paintedPoints) {
        if(this.direction == 'U') {
            if (direction == 'L') this.x -= 1;
            else this.x += 1;
            this.direction = direction;
        }
        else if(this.direction == 'R') {
            if(direction == 'L') {
                this.y -= 1;
                this.direction = 'U';
            } else {
                this.y += 1;
                this.direction = 'D';
            }
        }
        else if(this.direction == 'D') {
            if(direction == 'L') {
                this.x += 1;
                this.direction = 'R';
            } else {
                this.x -= 1;
                this.direction = 'L';
            }
        } else {
            if(direction == 'L') {
                this.y += 1;
                this.direction = 'D';
            } else {
                this.y -= 1;
                this.direction = 'U';
            }
        }
        int newX = this.x;
        int newY = this.y;
        boolean isFound = false;
        for(Point p: paintedPoints) {
            if(p.x == newX && p.y == newY) {
                isFound = true;
                this.panelColor = p.getColor();
                break;
            }
        }
        if(!isFound) {
            paintedPoints.add(new Point(this.x, this.y, 'B'));
            this.panelColor = 'B';
        }
    }
}
