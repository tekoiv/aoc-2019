import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Droid {
    int x, y;
    //would be better to use maps, might implement
    ArrayList<int[]> droidPoints = new ArrayList<>();
    ArrayList<int[]> walls = new ArrayList<>();
    public Droid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveDroid(int direction) {
        if(direction == 1) this.y--;
        else if(direction == 2) this.y++;
        else if(direction == 3) this.x++;
        else this.x--;
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
        else if(direction == 3) wallCoordinate = (new int[]{this.x + 1, this.y});
        else wallCoordinate = (new int[]{this.x - 1, this.y});
        AtomicBoolean isFound = new AtomicBoolean(false);
        walls.forEach(wall -> {
            if(Arrays.equals(wall, wallCoordinate)) {
                isFound.set(true);
            }
        });
        if(!isFound.get()) walls.add(wallCoordinate);
    }

    public void printWallsAndPoints() {
        System.out.print("Original walls: ");
        walls.forEach(wall -> System.out.print(wall[0] + "," + wall[1] + " "));
        System.out.println();
        System.out.print("Original points: ");
        droidPoints.forEach(point -> System.out.print(point[0] + "," + point[1] + " "));
        int[] smallestCoordinates = findSmallestSeparateCoordinates();
        System.out.println();
        System.out.print("Walls: ");
        walls.forEach(wall -> {
            System.out.print((wall[0] + smallestCoordinates[0]) + "," + (wall[1] + smallestCoordinates[1]) + " ");
        });
        System.out.println();
        System.out.print("Points: ");
        droidPoints.forEach(point -> {
            System.out.print((point[0] + smallestCoordinates[0]) + "," + (point[1] + smallestCoordinates[1]) + " ");
        });
        System.out.println();
    }
    // offset
    public int[] findSmallestSeparateCoordinates() {
        int[] smallestCoordinates;
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
        smallestCoordinates = new int[]{Math.abs(smallestX), Math.abs(smallestY)};
        return smallestCoordinates;

    }
}
