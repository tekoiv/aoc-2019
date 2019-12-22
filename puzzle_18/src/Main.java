import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Main {

    static List<Key> keys = new ArrayList<>();
    static List<Door> doors = new ArrayList<>();
    static List<Key> reachableKeys = new ArrayList<>();
    static Point startingPoint;
    static int row;
    static int col;
    //neighbours
    static int rowNum[] = {-1, 0, 0, 1};
    static int colNum[] = {0, -1, 1, 0};
    static int totalPath = 0;

    static void getKeysAndDoors(List<StringBuilder> input) {
        int lengthX = input.get(0).length();
        int lengthY = input.size();
        for(int i = 0; i < lengthY; i++) {
            for(int j = 0; j < lengthX; j++) {
                if(input.get(i).charAt(j) != '#' || input.get(i).charAt(j) != '.') {
                    if(input.get(i).charAt(j) == '@') startingPoint = new Point(j, i);
                    else if(Character.isLowerCase(input.get(i).charAt(j))) keys.add(new Key(j, i, input.get(i).charAt(j)));
                    else if(Character.isUpperCase(input.get(i).charAt(j))) doors.add(new Door(j, i, input.get(i).charAt(j)));
                }
            }
        }
    }

    //BFS

    static boolean isValid(int r, int c) {
        return (r >= 0) && (r < row) && (c >= 0) && (c < col);
    }

    static int findShortestPath(char[][] maze, Point src, Key dest) {
        if(maze[src.getX()][src.getY()] == '#' || maze[dest.getX()][dest.getY()] == '#') return -1;
        //visited cells
        boolean[][] visited = new boolean[row][col];
        visited[src.getX()][src.getY()] = true;
        Queue<QueueNode> q = new LinkedList<>();

        QueueNode s = new QueueNode(src, 0);
        q.add(s);

        while(!q.isEmpty()) {
            QueueNode current = q.peek();
            Point pt = current.pt;

            if(pt.getX() == dest.getX() && pt.getY() == dest.getY()) {
                return current.dist;
            }
            q.remove();
            for(int i = 0; i < 4; i++) {
                int row = pt.getX() + rowNum[i];
                int col = pt.getY() + colNum[i];

                if(isValid(row, col) && (maze[row][col] == '.' || Character.isLowerCase(maze[row][col])) && !visited[row][col]) {
                    visited[row][col] = true;
                    QueueNode adjCell = new QueueNode(new Point(row, col), current.dist + 1);

                    q.add(adjCell);
                }
            }
        }
        return -1;
    }

    static int getRows(List<StringBuilder> input) { return input.size(); }
    static int getColumns(List<StringBuilder> input) { return input.get(0).length(); }

    static void debugPrint() {
        System.out.println("Keys: ");
        keys.forEach(key -> System.out.println(key.getX() + "," + key.getY() + "," + key.getValue()));
        System.out.println("Doors: ");
        doors.forEach(door -> System.out.println(door.getX() + "," + door.getY() + "," + door.getValue()));
        System.out.println("Starting point: " + startingPoint.getX() + "," + startingPoint.getY());
        System.out.println("Reachable keys: ");
        reachableKeys.forEach(key -> System.out.println(key.getX() + "," + key.getY() + "," + key.getValue()));
    }

    static void findPath(char[][] maze) {
        while(keys.size() > 0) {
            Key keyWithShortestPath = new Key(0, 0, 'ä');
            int shortestPath = Integer.MAX_VALUE;
            for(Key k: keys) {
                int pathLength = findShortestPath(maze, startingPoint, k);
                if(pathLength != -1 && pathLength < shortestPath) {
                    keyWithShortestPath = k;
                    shortestPath = pathLength;
                }
            }
            totalPath += shortestPath;
            startingPoint = new Point(keyWithShortestPath.getX(), keyWithShortestPath.getY());
            keys.remove(keyWithShortestPath);
            final Key finalKey = keyWithShortestPath;
            List<Door> doorToBeOpened = doors.stream().filter(door -> door.getValue() == Character.toUpperCase(finalKey.getValue())).collect(Collectors.toList());
            maze[doorToBeOpened.get(0).getX()][doorToBeOpened.get(0).getY()] = '.';
            debugPrint();
        }
    }

    public static void main(String[] args) {
        List<StringBuilder> input = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_18.txt"));
            line = reader.readLine();
            while(line != null) {
                input.add(new StringBuilder(line));
                line = reader.readLine();
            }
        } catch(IOException e) { System.out.println(e); }
        row = getRows(input);
        col = getColumns(input);
        getKeysAndDoors(input);
        char[][] maze = new char[col][row];
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                maze[j][i] = input.get(i).charAt(j);
            }
        }
        //starting point
        maze[40][40] = '.';
        findPath(maze);
        System.out.println("Total path: " + totalPath);
    }
}
