public class Ball {
    String direction;
    int x;
    int y;
    public Ball(String direction, int x, int y) {
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

    public String getDirection() {
        return direction;
    }

    private void setDirection(String dir) {
        this.direction = dir;
    }

    public void move(char[][] screenMatrix) {
        String currentDirection = this.direction;
        int currentX = this.x;
        int currentY = this.y;
        screenMatrix[currentX][currentY] = 'E';
        //right up possibilities
        if(currentDirection.equals("RU")) {
            if(screenMatrix[currentX + 1][currentY] == 'B' || screenMatrix[currentX + 1][currentY] == 'W') {
                setDirection("LU");
                if(screenMatrix[currentX + 1][currentY] == 'B') screenMatrix[currentX + 1][currentY] = 'E';
            }
            else if(screenMatrix[currentX][currentY - 1] == 'B' || screenMatrix[currentX][currentY - 1] == 'W') {
                this.direction = "RD";
                if(screenMatrix[currentX][currentY - 1] == 'B') screenMatrix[currentX][currentY - 1] = 'E';
            }
            else if(screenMatrix[currentX + 1][currentY - 1] == 'B' || screenMatrix[currentX + 1][currentY - 1] == 'W') {
                this.direction = "LD";
                if(screenMatrix[currentX + 1][currentY - 1] == 'B') screenMatrix[currentX + 1][currentY - 1] = 'E';
            } else {
                this.x = currentX + 1;
                this.y = currentY - 1;
            }
        }
        //left up possibilities
        else if(currentDirection == "LU") {
            if(screenMatrix[currentX - 1][currentY] == 'B' || screenMatrix[currentX - 1][currentY] == 'W') {
                this.direction = "RU";
                if(screenMatrix[currentX - 1][currentY] == 'B') screenMatrix[currentX - 1][currentY] = 'E';
            }
            else if(screenMatrix[currentX][currentY - 1] == 'B' || screenMatrix[currentX][currentY - 1] == 'W') {
                this.direction = "LD";
                if(screenMatrix[currentX][currentY - 1] == 'B') screenMatrix[currentX][currentY - 1] = 'E';
            }
            else if(screenMatrix[currentX - 1][currentY - 1] == 'B' || screenMatrix[currentX - 1][currentY - 1] == 'W') {
                this.direction = "RD";
                if(screenMatrix[currentX - 1][currentY - 1] == 'B') screenMatrix[currentX - 1][currentY - 1] = 'E';
            } else {
                this.x = currentX - 1;
                this.y = currentY - 1;
            }
        }
        //right down possibilities
        else if(currentDirection == "RD") {
            if(screenMatrix[currentX + 1][currentY] == 'B' || screenMatrix[currentX + 1][currentY] == 'W') {
                this.direction = "LD";
                if(screenMatrix[currentX + 1][currentY] == 'B') screenMatrix[currentX + 1][currentY] = 'E';
            }
            else if(screenMatrix[currentX][currentY + 1] == 'H' || screenMatrix[currentX][currentY + 1] == 'B') {
                this.direction = "RU";
                if(screenMatrix[currentX][currentY + 1] == 'B') screenMatrix[currentX][currentY + 1] = 'E';
            }
            else if(screenMatrix[currentX + 1][currentY + 1] == 'B' || screenMatrix[currentX + 1][currentY + 1] == 'W') {
                this.direction = "LU";
                if(screenMatrix[currentX + 1][currentY + 1] == 'B') screenMatrix[currentX + 1][currentY + 1] = 'E';
            }
            else {
                this.x = currentX + 1;
                this.y = currentY + 1;
            }
        }
        //left down possibilities
        else if(currentDirection == "LD") {
            if(screenMatrix[currentX - 1][currentY] == 'B' || screenMatrix[currentX - 1][currentY] == 'W') {
                this.direction = "RD";
                if(screenMatrix[currentX - 1][currentY] == 'B') screenMatrix[currentX - 1][currentY] = 'E';
            }
            else if(screenMatrix[currentX][currentY + 1] == 'H' || screenMatrix[currentX][currentY + 1] == 'W') {
                this.direction = "LU";
                if(screenMatrix[currentX][currentY + 1] == 'B') screenMatrix[currentX][currentY + 1] = 'E';
            }
            else if(screenMatrix[currentX - 1][currentY + 1] == 'B' || screenMatrix[currentX - 1][currentY + 1] == 'W') {
                this.direction = "RU";
                if(screenMatrix[currentX - 1][currentY + 1] == 'B') screenMatrix[currentX - 1][currentY + 1] = 'E';
            }
            else {
                this.x = currentX - 1;
                this.y = currentY + 1;
            }
        }
        screenMatrix[this.x][this.y] = 'P';
    }
}
