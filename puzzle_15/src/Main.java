import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    static Droid droid = new Droid(0, 0, 4);
    static final int ROW = 41;
    static final int COL = 41;
    static List<Point> oxygenPoints = new ArrayList<>();
    //neighbours
    static int rowNum[] = {-1, 0, 0, 1};
    static int colNum[] = {0, -1, 1, 0};
    //refactored intCodeComputer from puzzle 5
    static void runProgram(ArrayList<StringBuilder> input, StringBuilder inputInstruction) {
        int index = 0;
        int relativeBase = 0;
        char modeOne, modeTwo, modeThree;
        StringBuilder firstParameter;
        StringBuilder secondParameter;
        while (!input.get(index).toString().equals("99")) {
            StringBuilder opCode = input.get(index);
            int fillerZeros = 0;
            if(opCode.length() < 5) fillerZeros = 5 - opCode.length();
            for(int i = 0; i < fillerZeros; i++) {
                opCode.reverse();
                opCode.append("0");
                opCode.reverse();
            }
            //modes
            modeOne = opCode.charAt(2);
            modeTwo = opCode.charAt(1);
            modeThree = opCode.charAt(0);
            if(opCode.substring(opCode.length() - 2).equals("03")) {
                int i;
                if(modeOne == '0') {
                    i = Integer.parseInt(input.get(index + 1).toString());
                }
                else {
                    i = relativeBase + Integer.parseInt(input.get(index + 1).toString());
                }
                input.set(i, inputInstruction);
                index += 2;
            }
            else if(opCode.substring(opCode.length() - 2).matches("04|09")) {
                int i;
                if(modeOne == '0') {
                    i = Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                }
                else if(modeOne == '2') {
                    i = relativeBase + Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                } else {
                    firstParameter = input.get(index + 1);
                }

                if(opCode.substring(opCode.length() - 2).equals("04")) {
                    int nonZeroIndex = 0;
                    if(firstParameter.length() > 0) {
                        for(int j = 0; j < firstParameter.length(); j++) {
                            if(firstParameter.charAt(j) != '0') {
                                nonZeroIndex = j;
                                break;
                            }
                        }
                    }
                    if(firstParameter.substring(nonZeroIndex).equals("0")) {
                        droid.addWall(Integer.parseInt(inputInstruction.toString()));
                        if(droid.getDir() == 4) {
                            if(inputInstruction.toString().equals("2")) {
                                inputInstruction = new StringBuilder("4");
                            } else if(inputInstruction.toString().equals("4")) {
                                inputInstruction = new StringBuilder("1");
                            } else if(inputInstruction.toString().equals("1")) {
                                inputInstruction = new StringBuilder("3");
                            }
                        } else if(droid.getDir() == 3) {
                            if(inputInstruction.toString().equals("1")) {
                                inputInstruction = new StringBuilder("3");
                            } else if(inputInstruction.toString().equals("3")) {
                                inputInstruction = new StringBuilder("2");
                            }
                            else if(inputInstruction.toString().equals("2")) {
                                inputInstruction = new StringBuilder("4");
                            }
                        } else if(droid.getDir() == 2) {
                            if(inputInstruction.toString().equals("3")) {
                                inputInstruction = new StringBuilder("2");
                            } else if(inputInstruction.toString().equals("2")) {
                                inputInstruction = new StringBuilder("4");
                            } else if(inputInstruction.toString().equals("4")) {
                                inputInstruction = new StringBuilder("1");
                            }
                        } else if(droid.getDir() == 1) {
                            if(inputInstruction.toString().equals("4")) {
                                inputInstruction = new StringBuilder("1");
                            } else if(inputInstruction.toString().equals("1")) {
                                inputInstruction = new StringBuilder("3");
                            } else if(inputInstruction.toString().equals("3")) {
                                inputInstruction = new StringBuilder("2");
                            }
                        }
                    }
                    else if(firstParameter.substring(nonZeroIndex).equals("1")) {
                        droid.moveDroid(Integer.parseInt(inputInstruction.toString()));
                        if(droid.getX() == 0 && droid.getY() == 0) {
                            droid.printMaze();
                            break;
                        }
                        droid.setDir(Integer.parseInt(inputInstruction.toString()));
                        if(droid.getDir() == 4) inputInstruction = new StringBuilder("2");
                        else if(droid.getDir() == 3) inputInstruction = new StringBuilder("1");
                        else if(droid.getDir() == 2) inputInstruction = new StringBuilder("3");
                        else if(droid.getDir() == 1) inputInstruction = new StringBuilder("4");
                    }
                    else if(firstParameter.substring(nonZeroIndex).equals("2")) {
                        droid.moveDroid(Integer.parseInt(inputInstruction.toString()));
                        if(droid.getX() == 0 && droid.getY() == 0) {
                            break;
                        }
                        droid.setDir(Integer.parseInt(inputInstruction.toString()));
                        if(droid.getDir() == 4) inputInstruction = new StringBuilder("2");
                        else if(droid.getDir() == 3) inputInstruction = new StringBuilder("1");
                        else if(droid.getDir() == 2) inputInstruction = new StringBuilder("3");
                        else if(droid.getDir() == 1) inputInstruction = new StringBuilder("4");
                        droid.setTankLocation();
                    }
                } else {
                    relativeBase += Integer.parseInt(firstParameter.toString());
                }
                index += 2;
            }
            else if(opCode.substring(opCode.length() - 2).matches("05|06|07|08")) {
                int i;
                if(modeOne == '0') {
                    i = Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                }
                else if(modeOne == '2') {
                    i = relativeBase + Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                } else firstParameter = input.get(index + 1);

                if(modeTwo == '0') {
                    i = Integer.parseInt(input.get(index + 2).toString());
                    secondParameter = input.get(i);
                }
                else if(modeTwo == '2') {
                    i = relativeBase + Integer.parseInt(input.get(index + 2).toString());
                    secondParameter = input.get(i);
                } else secondParameter = input.get(index + 2);
                if(modeThree == '0') {
                    i = Integer.parseInt(input.get(index + 3).toString());
                } else {
                    i = relativeBase + Integer.parseInt(input.get(index + 3).toString());
                }
                //different possibilities
                if (opCode.substring(opCode.length() - 2).equals("05")) {
                    if(!firstParameter.toString().equals("0")) {
                        index = Integer.parseInt(secondParameter.toString());
                    } else index += 3;
                }
                else if(opCode.substring(opCode.length() - 2).equals("06")) {
                    if(firstParameter.toString().equals("0")) {
                        index = Integer.parseInt(secondParameter.toString());
                    } else index += 3;
                }
                else if(opCode.substring(opCode.length() - 2).equals("07")) {
                    if(Long.parseLong(firstParameter.toString()) < Long.parseLong(secondParameter.toString())) input.set(i, new StringBuilder("1"));
                    else input.set(i, new StringBuilder("0"));
                    index += 4;
                }
                else if(opCode.substring(opCode.length() - 2).equals("08")) {
                    if(firstParameter.toString().equals(secondParameter.toString())) {
                        input.set(i, new StringBuilder("1"));
                    } else input.set(i, new StringBuilder("0"));
                    index += 4;
                }
            }
            else {
                int i;
                if(modeOne == '0') {
                    i = Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                }
                else if(modeOne == '2'){
                    i = relativeBase + Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                } else firstParameter = input.get(index + 1);
                if(modeTwo == '0') {
                    i = Integer.parseInt(input.get(index + 2).toString());
                    secondParameter = input.get(i);
                }
                else if(modeTwo == '2') {
                    i = relativeBase + Integer.parseInt(input.get(index + 2).toString());
                    secondParameter = input.get(i);
                } else secondParameter = input.get(index + 2);
                if(modeThree == '2') {
                    i = relativeBase + Integer.parseInt(input.get(index + 3).toString());
                } else {
                    i = Integer.parseInt(input.get(index + 3).toString());
                }
                //possibilities
                if(opCode.substring(opCode.length() - 2).equals("01")) {
                    long sum = Long.parseLong(firstParameter.toString()) + Long.parseLong(secondParameter.toString());
                    input.set(i, new StringBuilder(Long.toString(sum)));
                }
                else if(opCode.substring(opCode.length() - 2).equals("02")) {
                    long product = Long.parseLong(firstParameter.toString()) * Long.parseLong(secondParameter.toString());
                    input.set(i, new StringBuilder(Long.toString(product)));
                }
                index += 4;
            }
        }
    }

    static boolean isValid(int row, int col) {
        return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL);
    }

    static int findShortestPath(char mat[][], Point src, Point dest) {
        if(mat[src.x][src.y] != '.' || mat[dest.x][dest.y] != '.') return -1;
        //which cells are visited
        boolean[][] visited = new boolean[ROW][COL];
        visited[src.x][src.y] = true;
        Queue<QueueNode> q = new LinkedList<>();

        QueueNode s = new QueueNode(src, 0);
        q.add(s);

        while(!q.isEmpty()) {
            QueueNode current = q.peek();
            Point pt = current.pt;

            if(pt.x == dest.x && pt.y == dest.y) {
                return current.dist;
            }

            q.remove();

            for(int i = 0; i < 4; i++) {
                int row = pt.x + rowNum[i];
                int col = pt.y + colNum[i];

                if(isValid(row, col) && mat[row][col] == '.' && !visited[row][col]) {
                    visited[row][col] = true;
                    QueueNode adjCell = new QueueNode(new Point(row, col), current.dist + 1);

                    q.add(adjCell);
                }
            }
        }

        return -1;
    }

    static boolean isOxygenLevelZero(char[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[j][i] == '.') {
                    return true;
                }
            }
        }
        return false;
    }

    static void oxygenTick(char[][] map, List<Point> currentOxygenPoints) {
        for(Point p: currentOxygenPoints) {
            if(map[p.getX() + 1][p.getY()] == '.') {
                oxygenPoints.add(new Point(p.getX() + 1, p.getY()));
                map[p.getX() + 1][p.getY()] = 'O';
            }
            if(map[p.getX() - 1][p.getY()] == '.') {
                oxygenPoints.add(new Point(p.getX() - 1, p.getY()));
                map[p.getX() - 1][p.getY()] = 'O';
            }
            if(map[p.getX()][p.getY() + 1] == '.') {
                oxygenPoints.add(new Point(p.getX(), p.getY() + 1));
                map[p.getX()][p.getY() + 1] = 'O';
            }
            if(map[p.getX()][p.getY() - 1] == '.') {
                oxygenPoints.add(new Point(p.getX(), p.getY() - 1));
                map[p.getX()][p.getY() - 1] = 'O';
            }
        }
    }

    public static void main(String[] args) {
        String input = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_15.txt"));
            input = reader.readLine();
        } catch (IOException e) { System.out.println(e); }
        String[] array = input.split(",");
        StringBuilder[] sbArray = new StringBuilder[array.length];
        StringBuilder zero = new StringBuilder("0");
        ArrayList<StringBuilder> sbList = new ArrayList<>();
        for(int i = 0; i < 2000; i++) {
            sbList.add(zero);
        }
        for(int i = 0; i < sbArray.length; i++) {
            sbList.set(i, new StringBuilder(array[i]));
        }
        runProgram(sbList, new StringBuilder("2"));
        char maze[][] = droid.getMaze();
        Point source = new Point(21, 21);
        Point dest = new Point(9, 33);
        int dist = findShortestPath(maze, source, dest);

        if(dist != Integer.MAX_VALUE) System.out.println("Shortest path: " + dist);
        else System.out.println("Shortest path doesn't exist.");
        int index = 0;
        maze[9][33] = 'O';
        oxygenPoints.add(new Point(9, 33));
        while(isOxygenLevelZero(maze)) {
            List<Point> currentOxygenPoints = new ArrayList<>(oxygenPoints);
            oxygenTick(maze, currentOxygenPoints);
            index++;
        }
        System.out.println("Minutes until full of oxygen: " + index);
    }
}

