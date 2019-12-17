import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Droid {
    private int x, y, dir;
    //would be better to use maps, might implement
    ArrayList<int[]> droidPoints = new ArrayList<>();
    ArrayList<int[]> walls = new ArrayList<>();
    int[] tankLocation;
    public Droid(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public void moveDroid(int direction) {
        if(direction == 1) this.y--;
        else if(direction == 2) this.y++;
        else if(direction == 3) this.x--;
        else this.x++;
        AtomicBoolean isFound = new AtomicBoolean(false);
        droidPoints.forEach(point -> {
            if(Arrays.equals(point, new int[]{this.x, this.y})) {
                isFound.set(true);
            }
        });
        if(!isFound.get()) droidPoints.add(new int[]{this.x, this.y});
    }

    public void addWall(int direction) {
        int[] wallCoordinate;
        if(direction == 1) wallCoordinate = (new int[]{this.x, this.y - 1});
        else if(direction == 2) wallCoordinate = (new int[]{this.x, this.y + 1});
        else if(direction == 3) wallCoordinate = (new int[]{this.x - 1, this.y});
        else wallCoordinate = (new int[]{this.x + 1, this.y});
        AtomicBoolean isFound = new AtomicBoolean(false);
        walls.forEach(wall -> {
            if(Arrays.equals(wall, wallCoordinate)) {
                isFound.set(true);
            }
        });
        if(!isFound.get()) walls.add(wallCoordinate);
    }

    public void printWallsAndPoints() {
        /*System.out.print("Walls: ");
        walls.forEach(wall -> {
            System.out.print(wall[0] + "," + wall[1] + " ");
        });
        System.out.println();
        System.out.print("Points: ");
        droidPoints.forEach(point -> {
            System.out.print(point[0] + "," + point[1] + " ");
        });
        System.out.println();*/
        int[] offset = getOffset();
        List<int[]> pointsWithOffset = new ArrayList<>();
        List<int[]> wallsWithOffset = new ArrayList<>();
        for(int[] p: droidPoints) {
            pointsWithOffset.add(new int[]{p[0] + offset[0], p[1] + offset[1]});
        }
        for(int[] w: walls) {
            wallsWithOffset.add(new int[]{w[0] + offset[0], w[1] + offset[1]});
        }
        int[] robotWithOffset = {this.x + offset[0], this.y + offset[1]};
        //pointsWithOffset.forEach(point -> System.out.print(point[0] + "," + point[1] + " "));
        System.out.println();
        printMaze(pointsWithOffset, wallsWithOffset, robotWithOffset);
    }

    private void printMaze(List<int[]> points, List<int[]> walls, int[] robot) {
        int largestX = 0;
        int largestY = 0;
        for(int[] p: points) {
            if (p[0] > largestX) largestX = p[0];
            if (p[1] > largestY) largestY = p[1];
        }
        for(int[] w: walls) {
            if(w[0] > largestX) largestX = w[0];
            if(w[1] > largestY) largestY = w[1];
        }
        char[][] visualMap = new char[largestX + 1][largestY + 1];
        for(int i = 0; i < largestY + 1; i++){
            for(int j = 0; j < largestX + 1; j++) {
                visualMap[j][i] = ' ';
                for(int[] p: points) {
                    if(p[0] == j && p[1] == i) visualMap[j][i] = '.';
                }
                for(int[] w: walls) {
                    if(w[0] == j && w[1] == i) visualMap[j][i] = '#';
                }
            }
        }
        visualMap[robot[0]][robot[1]] = 'R';
        for(int i = 0; i < largestY + 1; i++) {
            for(int j = 0; j < largestX + 1; j++) {
                System.out.print(visualMap[j][i]);
            }
            System.out.println();
        }
    }
    // offset
    private int[] getOffset() {
        int smallestX = 0;
        int smallestY = 0;
        for(int[] p: droidPoints) {
            if(p[0] < smallestX) smallestX = p[0];
            if(p[1] < smallestY) smallestY = p[1];
        }
        for(int[] p: walls) {
            if(p[0] < smallestX) smallestX = p[0];
            if(p[1] < smallestY) smallestY = p[1];
        }
        return new int[]{Math.abs(smallestX), Math.abs(smallestY)};
    }

    public void setTankLocation() {
        tankLocation = new int[]{this.x, this.y};
    }
}
