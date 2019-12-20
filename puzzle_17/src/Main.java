import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    static List<Integer> ASCIIList;
    static char[][] maze = new char[49][41];
    static List<String> mainRoutine = convertToASCII(List.of("A", "A", "B", "C", "B", "C", "B", "C", "C", "A"));
    static List<String> A = convertToASCII(List.of("L","10","R","8","R","8"));
    static List<String> B = convertToASCII(List.of("L","10","L","12","R","8","R","10"));
    static List<String> C = convertToASCII(List.of("R","10","L","12","R","10"));

    //refactored intCodeComputer from puzzle 5
    static void runProgram(ArrayList<StringBuilder> input, StringBuilder inputInstruction) {
        int index = 0;
        int relativeBase = 0;
        int inputInstructionIndex = 0;
        int mainRoutineIndex = 0;
        int AIndex = 0;
        int BIndex = 0;
        int CIndex = 0;
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
                if(inputInstructionIndex == 0) {
                    input.set(i, new StringBuilder(mainRoutine.get(mainRoutineIndex)));
                    if(mainRoutine.get(mainRoutineIndex).equals("10")) inputInstructionIndex++;
                    System.out.println("First");
                    mainRoutineIndex++;
                } else if(inputInstructionIndex == 1) {
                    input.set(i, new StringBuilder(A.get(AIndex)));
                    if(A.get(AIndex).equals("10")) {
                        inputInstructionIndex++;
                    }
                    System.out.println("Second");
                    AIndex++;
                } else if(inputInstructionIndex == 2) {
                    input.set(i, new StringBuilder(B.get(BIndex)));
                    if(B.get(BIndex).equals("10")) {
                        inputInstructionIndex++;
                    }
                    System.out.println("Third");
                    BIndex++;
                } else if(inputInstructionIndex == 3) {
                    input.set(i, new StringBuilder(C.get(CIndex)));
                    if(C.get(CIndex).equals("10")) {
                        inputInstructionIndex++;
                    }
                    System.out.println("Fourth");
                    CIndex++;
                } else if(inputInstructionIndex == 4){
                    input.set(i, new StringBuilder("110"));
                    System.out.println("Fifth");
                    inputInstructionIndex++;
                } else inputInstructionIndex = 1;
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
                    //System.out.println(firstParameter.substring(nonZeroIndex));
                    ASCIIList.add(Integer.parseInt(firstParameter.substring(nonZeroIndex)));
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

    static List<Point> getCrossroads(List<Point> points) {
        List<Point> crossRoads = new ArrayList<>();
        for(int i = 0; i < points.size(); i++) {
            int finalI = i;
            List<Point> adjacent = points.stream()
                    .filter(item -> {
                        int currentX = points.get(finalI).getX();
                        int currentY = points.get(finalI).getY();
                        return ((item.getX() == currentX - 1 && item.getY() == currentY)
                        || (item.getX() == currentX + 1 && item.getY() == currentY)
                        || (item.getX() == currentX && item.getY() == currentY - 1)
                        || (item.getX() == currentX && item.getY() == currentY + 1));
                    }).collect(Collectors.toList());
            if(adjacent.size() > 3) crossRoads.add(points.get(i));
        }
        return crossRoads;
    }

    static void solveOne() {
        List<Point> scaffoldingPoints = new ArrayList<>();
        int row = 0;
        int col = 0;
        for (Integer code : ASCIIList) {
            if (code == 35) {
                System.out.print('#');
                scaffoldingPoints.add(new Point(row, col));
                row++;
            }
            else if (code == 46) {
                System.out.print(".");
                row++;
            }
            else if (code == 10) {
                System.out.println();
                col++;
                row = 0;
            }
            else {
                System.out.print("^");
                row++;
            }
        }
        System.out.println(row + ", " + col);
        List<Point> crossRoads = getCrossroads(scaffoldingPoints);
        int counter = 0;
        for(Point p: crossRoads) {
            counter += p.getX()*p.getY();
        }
        System.out.println("Answer for 17 a): " + counter);
        for(Point p: scaffoldingPoints) maze[p.getX()][p.getY()] = '#';
        for(int i = 0; i < 41; i++){
            for(int j = 0; j < 49; j++) {
                if(maze[j][i] != '#') maze[j][i] = '.';
            }
        }
        maze[24][22] = '^';
        for(int i = 0; i < 41; i++) {
            for(int j = 0; j < 49; j++) {
                System.out.print(maze[j][i]);
            } System.out.println();
        }
        //robot in 24, 22
    }

    static List<String> solveMaze() {
        Robot robot = new Robot(24, 22, 'L');
        List<String> strList = new ArrayList<>();
        strList.add("L");
        while(robot.getX() != 34 && robot.getY() != 38) {
            int counter = 0;
            while (robot.getNextPosition(maze) != '.') {
                robot.move();
                counter++;
            }
            char turnDir = robot.turn(maze);
            strList.add(Integer.toString(counter));
            strList.add(Character.toString(turnDir));
        }
        strList.add("8");
        return strList;
    }

    static List<String> convertToASCII(List<String> input) {
        List<String> ASCIIList = new ArrayList<>();
        for(String s: input) {
            switch (s) {
                case "A": ASCIIList.add("65"); break;
                case "B": ASCIIList.add("66"); break;
                case "C": ASCIIList.add("67"); break;
                case ",": ASCIIList.add("44"); break;
                case "R": ASCIIList.add("82"); break;
                case "L": ASCIIList.add("76"); break;
                case "10": ASCIIList.add("57"); ASCIIList.add("44"); ASCIIList.add("49"); break;
                case "8": ASCIIList.add("56"); break;
                case "12": ASCIIList.add("57"); ASCIIList.add("44"); ASCIIList.add("51"); break;
                case "NEW": ASCIIList.add("10"); break;
            }
            ASCIIList.add("44");
        }
        ASCIIList.set(ASCIIList.size() - 1, "10");
        return ASCIIList;
    }

    static void convertFromASCII() {
        for(Integer i: ASCIIList) {
            System.out.print((char) Integer.parseInt(Integer.toString(i)));
        }
    }

    public static void main(String[] args) {
        String input = "";
        ASCIIList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_17.txt"));
            input = reader.readLine();
        } catch (IOException e) { System.out.println(e); }
        String[] array = input.split(",");
        StringBuilder[] sbArray = new StringBuilder[array.length];
        StringBuilder zero = new StringBuilder("0");
        ArrayList<StringBuilder> sbList = new ArrayList<>();
        for(int i = 0; i < 4000; i++) {
            sbList.add(zero);
        }
        //34
        for(int i = 0; i < sbArray.length; i++) {
            sbList.set(i, new StringBuilder(array[i]));
        }
        //runProgram(sbList, new StringBuilder("0"));
        //solveOne();
        sbList.set(0, new StringBuilder("2"));
        //34, 38
        runProgram(sbList, new StringBuilder("0"));
        //List<String> path = solveMaze();
        //System.out.println(path);
        //System.out.println(mainRoutine);
        convertFromASCII();
    }
}