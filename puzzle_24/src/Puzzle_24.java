import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Puzzle_24 {

    private Point[][] points = new Point[5][5];
    //slow but will work for part one
    private List<Point[][]> previousPoints = new ArrayList<>();

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
        Puzzle_24 puzzle = new Puzzle_24(input.toString());
        puzzle.run();

    }

    private Puzzle_24(String input) {
        String[] inputArray = input.split("\n");
        for(int i = 0; i < inputArray.length; i++) {
            for(int j = 0; j < inputArray[0].length(); j++) {
                points[j][i] = (new Point(j, i, inputArray[i].charAt(j) == '#'));
            }
        }
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
}
