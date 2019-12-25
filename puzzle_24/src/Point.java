import java.util.ArrayList;
import java.util.List;

class Point {

    private int x, y;
    private boolean isInfested;

    Point(int x, int y, boolean isInfested) {
        this.x = x;
        this.y = y;
        this.isInfested = isInfested;
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    boolean isInfested() {
        return isInfested;
    }

    List<Point> getAdjacentPoints() {
        List<Point> adjacentPoints = new ArrayList<>();
        //neighbours
        int[] x = {1, -1, 0, 0};
        int[] y = {0, 0, 1, -1};
        for(int i = 0; i < 4; i++) {
            if(this.x + x[i] >= 0 && this.x + x[i] < 5 && this.y + y[i] >= 0 && this.y + y[i] < 5) {
                adjacentPoints.add(new Point(this.x + x[i], this.y + y[i]));
            }
        }
        return adjacentPoints;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Point p = (Point) obj;
        return x == p.x &&
                y == p.y &&
                isInfested == p.isInfested;
    }
}
