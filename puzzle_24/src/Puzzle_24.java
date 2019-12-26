import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Puzzle_24 {

    private Point[][] points = new Point[5][5];
    private Map<Integer, Point[][]> recursionLevels;
    private List<Point[][]> previousPoints = new ArrayList<>();
    private final int TIME = 200;

    public static void main(String[] args) {
        StringBuilder input = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_24.txt"));
            String nextLine = reader.readLine();
            while(nextLine != null) {
                input.append(nextLine).append("\n");
                nextLine = reader.readLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
        String test = "....#\n" +
                "#..#.\n" +
                "#..##\n" +
                "..#..\n" +
                "#....";
        Puzzle_24 puzzle = new Puzzle_24(test);
        //puzzle.run();
        puzzle.run2();

    }

    private Puzzle_24(String input) {
        String[] inputArray = input.split("\n");
        for(int i = 0; i < inputArray.length; i++) {
            for(int j = 0; j < inputArray[0].length(); j++) {
                points[j][i] = (new Point(j, i, inputArray[i].charAt(j) == '#'));
            }
        }
        recursionLevels = new HashMap<>();
        recursionLevels.put(0, points);
    }

    private void debugPrint(Point[][] points) {
        System.out.println();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(points[j][i].isInfested()) System.out.print("#");
                else System.out.print(".");
            } System.out.println();
        }
    }

    private void printMap() {
        for(int i: recursionLevels.keySet()) {
            System.out.println("Layer: " + i);
            for(int j = 0; j < 5; j++) {
                for(int k = 0; k < 5; k++) {
                    if(recursionLevels.get(i)[k][j].isInfested()) System.out.print("#");
                    else System.out.print(".");
                } System.out.println();
            }
        }
    }

    private boolean checkReference(Point[][] test1, Point[][] test2) {
        for(int i = 0; i < test1.length; i++) {
            if(!Arrays.deepEquals(test1[i], test2[i])) return false;
        }
        return true;
    }

    private void run() {
        int index = 0;
        while(true) {
            Point[][] newPoints = new Point[5][5];
            for(int j = 0; j < 5; j++) {
                for(int k = 0; k < 5; k++) {
                    newPoints[k][j] = new Point(k, j, nextGenAlive(points[k][j]));
                }
            }
            for (Point[][] previousPoint : previousPoints) {
                if (checkReference(newPoints, previousPoint)) {
                    System.out.println("Found on iteration: " + index);
                    debugPrint(newPoints);
                    printBiodiversityPoints(newPoints);
                    return;
                }
            }
            points = newPoints;
            previousPoints.add(newPoints);
            index++;
        }
    }

    private void printBiodiversityPoints(Point[][] points) {
        long totalPoints = 0;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(points[j][i].isInfested())
                    totalPoints += Math.pow(2, i * 5 + j);
            }
        }
        System.out.println("Biodiversity points: " + totalPoints);
    }

    private boolean nextGenAlive(Point p) {
        List<Point> adjacentPoints = p.getAdjacentPoints();
        int adjacentBugs = 0;
        for(Point point: adjacentPoints) {
            if(points[point.getX()][point.getY()].isInfested()) adjacentBugs++;
        }
        if(p.isInfested() && adjacentBugs == 1) return true;
        else return !p.isInfested() && (adjacentBugs == 1 || adjacentBugs == 2);
    }

    //part 2
    /*
    Initially, only level 0 contains bugs. Then, after each
    iteration, two more levels are populated by bugs.
     */

    private Point[][] initEmptyRecursionLevel() {
        Point[][] emptyLevel = new Point[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                emptyLevel[j][i] = new Point(j, i, false);
            }
        }
        return emptyLevel;
    }

    private void run2() {
        System.out.println(normalBugs(recursionLevels.get(0), 0, 1));
        for(int i = 0; i < 2; i++) {
            tick(i);
            System.out.println("Iteration " + i);
            printMap();
        }
    }

    private int outerCornerAdjacent(Point[][] levelAbove, int x, int y) {
        int adjacentBugs = 0;
        boolean north = levelAbove[2][1].isInfested();
        boolean south = levelAbove[2][3].isInfested();
        boolean west = levelAbove[1][2].isInfested();
        boolean east = levelAbove[3][2].isInfested();
        if(x == 0 && y == 0) {
            if(north) adjacentBugs++;
            if(west) adjacentBugs++;
        } else if(x == 4 && y == 0) {
            if(north) adjacentBugs++;
            if(east) adjacentBugs++;
        } else if(x == 0 && y == 4) {
            if(south) adjacentBugs++;
            if(west) adjacentBugs++;
        } else if(x == 4 && y == 4) {
            if(south) adjacentBugs++;
            if(east) adjacentBugs++;
        } else if(x == 0) {
            if(west) adjacentBugs++;
        } else if(x == 4) {
            if(east) adjacentBugs++;
        } else if(y == 0) {
            if(north) adjacentBugs++;
        } else if(y == 4) {
            if(south) adjacentBugs++;
        } else System.out.println("This shouldn't happen");
        return adjacentBugs;
    }

    private int innerCornerAdjacent(Point[][] levelBelow, int x, int y) {
        int adjacentBugs = 0;
        if(x == 2 && y == 1) {
            for(int i = 0; i < 5; i++) {
                if(levelBelow[i][0].isInfested()) adjacentBugs++;
            }
        } else if(x == 2 && y == 3) {
            for(int i = 0; i < 5; i++) {
                if(levelBelow[i][4].isInfested()) adjacentBugs++;
            }
        } else if(x == 1 && y == 2) {
            for(int i = 0; i < 5; i++) {
                if(levelBelow[0][i].isInfested()) adjacentBugs++;
            }
        } else if(x == 3 && y == 2) {
            for(int i = 0; i < 5; i++) {
                if(levelBelow[4][i].isInfested()) adjacentBugs++;
            }
        } else System.out.println("This shouldn't happen.");
        return adjacentBugs;
    }

    private int normalBugs(Point[][] currentLevel, int x, int y) {
        int adjacentBugs = 0;
        if(x - 1 >= 0) if(currentLevel[x - 1][y].isInfested() && !isCenter(x - 1, y)) adjacentBugs++;
        if(x + 1 < 5) if(currentLevel[x + 1][y].isInfested() && !isCenter(x + 1, y)) adjacentBugs++;
        if(y - 1 >= 0) if(currentLevel[x][y - 1].isInfested() && !isCenter(x, y - 1)) adjacentBugs++;
        if(y + 1 < 5) if(currentLevel[x][y + 1].isInfested() && !isCenter(x, y + 1)) adjacentBugs++;
        return adjacentBugs;
    }

    private boolean isCenter(int x, int y) {
        return (x==2 && y == 2);
    }

    private void tick(int i) {
        Map<Integer, Point[][]> tempLevels = new HashMap<>();
        if(i == 0) {
            recursionLevels.put(0, points);
            recursionLevels.put(-1, initEmptyRecursionLevel());
            recursionLevels.put(1, initEmptyRecursionLevel());
        } else {
            recursionLevels.put(0 - i - 1, initEmptyRecursionLevel());
            recursionLevels.put(i + 1, initEmptyRecursionLevel());
            for(int iterator = 0 - i; iterator < i + 1; iterator++) {
                Point[][] levelBelow = recursionLevels.get(iterator - 1);
                Point[][] levelAbove = recursionLevels.get(iterator + 1);
                Point[][] currentLevel = recursionLevels.get(iterator);
                Point[][] tempLevel = new Point[5][5];
                //level above affects the outer bugs' future,
                //level below affects the 4 inner bugs' future
                for(int j = 0; j < 5; j++) {
                    for(int k = 0; k < 5; k++) {
                        int adjacentBugs = 0;
                        if(k == 0 || j == 0 || k == 4 || j == 4) {
                            adjacentBugs += outerCornerAdjacent(levelAbove, k, j);
                        }
                        if((k == 2 && j == 1) || (k == 2 && j == 3)
                                || (k == 1 && j == 2) || (k == 3 && j == 2)) {
                            adjacentBugs += innerCornerAdjacent(levelBelow, k, j);
                        }
                        adjacentBugs += normalBugs(currentLevel, k, j);
                        if(isCenter(k, j)) tempLevel[k][j] = new Point(2, 2, false);
                        else if(currentLevel[k][j].isInfested() && adjacentBugs == 1) tempLevel[k][j] = new Point(k, j, true);
                        else if(!currentLevel[k][j].isInfested() && (adjacentBugs == 2 || adjacentBugs == 1)) tempLevel[k][j] = new Point(k, j, true);
                        else tempLevel[k][j] = new Point(k, j, false);
                    }
                }
                tempLevels.put(iterator, tempLevel);
            }
            tempLevels.put(0 - i - 1, initEmptyRecursionLevel());
            tempLevels.put(i + 1, initEmptyRecursionLevel());
            recursionLevels = tempLevels;
        }
    }
}
