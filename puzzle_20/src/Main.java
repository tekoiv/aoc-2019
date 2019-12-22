import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private Map<Point, String> portals = new HashMap<>();
    private Map<Point, Character> map = new HashMap<>();
    private Map<Position, List<Position>> nbrMemo = new HashMap<>();
    private int width;
    private int height;

    public static void main(String[] args) {
        String line;
        List<String> inputLines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_20.txt"));
            line = reader.readLine();
            while(line != null) {
                inputLines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main main = new Main(inputLines);
        System.out.println(main.runProgram());
    }

    private Main(List<String> lines) {
        lines.forEach(System.out::println);
        width = lines.get(0).length();
        height = lines.size();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                char c = lines.get(row).charAt(col);
                if (isChar(c)) {
                    Point portalLoc;
                    String portalName;
                    if (col < width - 1 && isChar(lines.get(row).charAt(col + 1))) {
                        portalName = "" + c + lines.get(row).charAt(col + 1);
                        if (col == 0 || (col >= width / 2 && col != width - 2)) {
                            portalLoc = new Point(col + 2, row);
                        } else {
                            portalLoc = new Point(col - 1, row);
                        }
                    } else if (row < height - 1 && isChar(lines.get(row + 1).charAt(col))) {
                        portalName = "" + c + lines.get(row + 1).charAt(col);
                        if (row == 0 || (row >= height / 2 && row != height - 2)) {
                            portalLoc = new Point(col, row + 2);
                        } else {
                            portalLoc = new Point(col, row - 1);
                        }
                    } else {
                        continue;
                    }
                    portals.put(portalLoc, portalName);
                } else if (c != ' ') {
                    map.put(new Point(col, row), c);
                }
            }
        }
    }

    //main
    private int runProgram() {
        return getDistance(findPortal("AA").get(0).getKey(), findPortal("ZZ").get(0).getKey());
    }

    //BFS again
    private int getDistance(Point source, Point dest) {
        Queue<QueueNode> visitQueue = new ArrayDeque<>();
        visitQueue.add(new QueueNode(new Position(source, 0), 0));
        Position target = new Position(dest, 0);
        Set<Position> visited = new HashSet<>();
        while (visitQueue.size() > 0) {
            QueueNode current = visitQueue.remove();
            if (current.getPos().equals(target)) {
                return current.getDist();
            }
            visited.add(current.getPos());
            List<Position> neighbours = getNeighbours(current.getPos());
            for (Position n : neighbours) {
                if (visited.contains(n)) {
                    continue;
                }
                visitQueue.add(new QueueNode(n, current.getDist() + 1));
            }
        }
        return -1;
    }

    //self explanatory
    private List<Position> getNeighbours(Position pos) {
        if (nbrMemo.containsKey(pos)) {
            return nbrMemo.get(pos);
        }
        List<Position> neighbours = new ArrayList<>();
        Point[] adjacent = pos.getPoint().getAdjacent();
        for (Point adj : adjacent) {
            if (adj != null && Character.valueOf('.').equals(map.get(adj))) {
                neighbours.add(new Position(adj, pos.getLevel()));
            }
        }
        String portal = portals.get(pos.getPoint());
        if (portal != null && !"AA".equals(portal)) {
            int lvl;
            if (isOuter(pos)) {
                if (pos.getLevel() != 0)
                    lvl = pos.getLevel() - 1;
                else {
                    nbrMemo.put(pos, neighbours);
                    return neighbours;
                }
            } else {
                lvl = pos.getLevel() + 1;
            }
            List<Map.Entry<Point, String>> portalPoints = findPortal(portal);
            for (Map.Entry<Point, String> p : portalPoints) {
                if (!p.getKey().equals(pos.getPoint())) {
                    neighbours.add(new Position(p.getKey(), lvl));
                }
            }
        }
        nbrMemo.put(pos, neighbours);
        return neighbours;
    }

    private boolean isOuter(Position pos) {
        return pos.getPoint().getX() <= 2 || pos.getPoint().getX() >= width - 3 || pos.getPoint().getY() <= 2 || pos.getPoint().getY() == height - 3;
    }

    private boolean isChar(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private List<Map.Entry<Point, String>> findPortal(String portalName) {
        List<Map.Entry<Point, String>> res = new ArrayList<>();
        for(Map.Entry<Point, String> entry: portals.entrySet()) {
            if(entry.getValue().equals(portalName)) {
                res.add(entry);
            }
        }
        return res;
    }
}
