import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
This solution is slow. It would've been better to
create objects with an x_low, x_high, y_low, y_high.
This way there wouldn't be objects for separate points,
but for separate lines.
 */

public class Main {
    public static void main(String[] args) {

        String[] inputWires = readFile();
        String[] inputWire1 = inputWires[0].split(",");
        String[] inputWire2 = inputWires[1].split(",");

        ArrayList<Point> pointsFirstWire = getWirePoints(inputWire1);
        ArrayList<Point> pointsSecondWire = getWirePoints(inputWire2);

        ArrayList<Point> intersectingPoints = getIntersectingPoints((ArrayList<Point>) pointsFirstWire.clone(), (ArrayList<Point>) pointsSecondWire.clone());

        System.out.println("Shortest manhattan distance: " + getSmallestManhattanDistance(intersectingPoints));
        System.out.println("Shortest path: " + smallestPath(intersectingPoints, pointsFirstWire, pointsSecondWire));

        /*String[] test1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51".split(",");
        String[] test2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7".split(",");

        ArrayList<Point> p1 = getWirePoints(test1);
        ArrayList<Point> p2 = getWirePoints(test2);
        ArrayList<Point> intersection = getIntersectingPoints((ArrayList<Point>) p1.clone(), (ArrayList<Point>) p2.clone());

        System.out.println("Shortest path: " + smallestPath(intersection, p1, p2));*/

    }

    static int getPaths(Point p, ArrayList<Point> firstWire, ArrayList<Point> secondWire) {
        int firstPath = firstWire.indexOf(p) + 1;
        int secondPath = secondWire.indexOf(p) + 1;
        int combined = firstPath + secondPath;
        return combined;
    }

    static int smallestPath(ArrayList<Point> intersection, ArrayList<Point> firstWire, ArrayList<Point> secondWire) {
        int smallestPath = 0;
        for(int i = 0; i < intersection.size(); i++) {
            if(i == 0) smallestPath = getPaths(intersection.get(i), firstWire, secondWire);
            else {
                int temp = getPaths(intersection.get(i), firstWire, secondWire);
                if(temp < smallestPath) {
                    smallestPath = temp;
                }
            }
        }
        return smallestPath;
    }

    static int getSmallestManhattanDistance(ArrayList<Point> intersectingPoints) {
        int smallestDistance = 0;
        for(int i = 0; i < intersectingPoints.size(); i++) {
            if(i == 0) smallestDistance = Math.abs(intersectingPoints.get(i).getX()) + Math.abs(intersectingPoints.get(i).getY());
            else {
                int temp = Math.abs(intersectingPoints.get(i).getX()) + Math.abs(intersectingPoints.get(i).getY());
                if (temp < smallestDistance) {
                    smallestDistance = temp;
                }
            }
        }
        return smallestDistance;
    }

    static ArrayList<Point> getIntersectingPoints(ArrayList<Point> p1, ArrayList<Point> p2) {
        p1.retainAll(p2);
        return p1;
    }

    static ArrayList<Point> getWirePoints(String[] inputWire) {
        Point currentPoint = new Point(0, 0);
        ArrayList<Point> points = new ArrayList<>();
        for(int i = 0; i < inputWire.length; i++) {
            if(inputWire[i].charAt(0) == 'R') {
                for(int j = 0; j < Integer.parseInt(inputWire[i].substring(1)); j++) {
                    currentPoint.setX(currentPoint.getX() + 1);
                    Point point = new Point(currentPoint.getX(), currentPoint.getY());
                    points.add(point);
                }
            }
            else if(inputWire[i].charAt(0) == 'L') {
                for(int j = 0; j < Integer.parseInt(inputWire[i].substring(1)); j++) {
                    currentPoint.setX(currentPoint.getX() - 1);
                    Point point = new Point(currentPoint.getX(), currentPoint.getY());
                    points.add(point);
                }
            }
            else if(inputWire[i].charAt(0) == 'U') {
                for(int j = 0; j < Integer.parseInt(inputWire[i].substring(1)); j++) {
                    currentPoint.setY(currentPoint.getY() - 1);
                    Point point = new Point(currentPoint.getX(), currentPoint.getY());
                    points.add(point);
                }
            }
            else {
                for(int j = 0; j < Integer.parseInt(inputWire[i].substring(1)); j++) {
                    currentPoint.setY(currentPoint.getY() + 1);
                    Point point = new Point(currentPoint.getX(), currentPoint.getY());
                    points.add(point);
                }
            }
        }
        return points;
    }

    static String[] readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_3.txt"));
            String line = reader.readLine();
            String[] wireInstructions = new String[2];
            wireInstructions[0] = line;
            line = reader.readLine();
            wireInstructions[1] = line;
            return wireInstructions;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
