import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //refactored intCodeComputer from puzzle 5

    /*
    The code is very slow. Could be faster if we were to
    eliminate the points that are definitely not inside the beam.
    There's a clear pattern if we map out the points.
     */

    static final int MAX_X = 100;
    static final int MAX_Y = 100;
    static List<Point> allPoints = new ArrayList<>();
    static List<Point> affectedPoints = new ArrayList<>();
    static List<Point> smartPoints = new ArrayList<>();

    static boolean runProgram(ArrayList<StringBuilder> input, Point point) {
        int index = 0;
        int relativeBase = 0;
        char modeOne, modeTwo, modeThree;
        int instructionIndex = 0;
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
                if(instructionIndex == 0) {
                    input.set(i, new StringBuilder(Integer.toString(point.x)));
                    instructionIndex++;
                } else {
                    input.set(i, new StringBuilder(Integer.toString(point.y)));
                    instructionIndex = 0;
                }
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
                    if(firstParameter.substring(nonZeroIndex).equals("1")) return true;
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
        return false;
    }

    static void createCoordinates() {
        for(int i = 0; i < MAX_Y; i++) {
            for(int j = 0; j < MAX_X; j++) {
                allPoints.add(new Point(j, i));
            }
        }
    }

    static void createSmartCoordinates(ArrayList<StringBuilder> input, int areaX, int areaY) {
        int x = 0;
        int y = 0;
        boolean foundFirst = false;
        while(x < areaX && y < areaY) {
            Point point = new Point(x, y);
            if(runProgram((ArrayList<StringBuilder>) input.clone(), point)) {
                foundFirst = true;
                affectedPoints.add(point);
                System.out.println(x + "," + y);
                x++;
            } else {
                if(foundFirst) {
                    foundFirst = false;
                    y++;
                    if(x > 20) x -= 20;
                    else x = 0;
                } else {
                    if(x > y*2 + 5) {
                        x = 0;
                        y++;
                    }
                    else x++;
                }
            }
        }
    }

    public static void main(String[] args) {
        String input = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_19.txt"));
            input = reader.readLine();
        } catch (IOException e) { System.out.println(e); }
        //input = "3,3,1105,-1,9,1101,0,0,12,4,12,99,1";
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
        System.out.println(sbList);
        //createSmartCoordinates(sbList);
        //affectedPoints.forEach(point -> System.out.println(point.x + "," + point.y));
        /*int counter = 0;
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                if(runProgram((ArrayList<StringBuilder>) sbList.clone(), new Point(j, i))) {
                    counter++;
                    System.out.println(j + "," + i);
                }
            }
        }
        System.out.println(counter);*/
        //System.out.println(runProgram((ArrayList<StringBuilder>) sbList.clone(), new Point(2, 3)));
        createSmartCoordinates(sbList, 100, 100);
        System.out.println(affectedPoints.size());
    }
}