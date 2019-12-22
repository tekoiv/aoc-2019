import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
    A bit messy but hey, it's another intcode puzzle.
     */
    static List<Point> affectedPoints = new ArrayList<>();

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
                    input.set(i, new StringBuilder(Long.toString(point.x)));
                    instructionIndex++;
                } else {
                    input.set(i, new StringBuilder(Long.toString(point.y)));
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

    static void createSmartCoordinates(ArrayList<StringBuilder> input, int areaX, int areaY) {
        int x = 0;
        int y = 0;
        boolean foundFirst = false;
        while(x < areaX && y < areaY) {
            Point point = new Point(x, y);
            if(runProgram((ArrayList<StringBuilder>) input.clone(), point)) {
                foundFirst = true;
                affectedPoints.add(point);
                x++;
            } else {
                if(foundFirst) {
                    foundFirst = false;
                    y++;
                    if(x > 40) x -= 40;
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

    static boolean checkBeam(long x, long y, ArrayList<StringBuilder> input) {
        //returns true if affected, false otherwise
        return runProgram((ArrayList<StringBuilder>) input.clone(), new Point(x, y));
    }

    static double getProportion(ArrayList<StringBuilder> input) {
        //trying out these ugly for/while-loops just so I get used to it
        //checking the proportion of x and y for points that return
        //true (affected by beam) when run
        for(long x = 0;;x++) {
            if(checkBeam(x, 100, input)) return x/100D;
        }
    }

    static long[] find(long yBegin, long yOffset, ArrayList<StringBuilder> input) {
        long x,y;
        boolean trackBeam = false;
        double proportion = getProportion(input);
        //using offset to reduce run time. when visualising the beam, it's clear that we only need to
        //check certain coordinates for every row
        for(y = yBegin;;y+=yOffset) {
            /*
            the ship automatically fits into a point if it's length is larger than 100 and the one 100 rows below it
            returns true from checkbeam
             */
            for(x = (int)(y*proportion);;++x) {
                boolean beam = checkBeam(x, y, (ArrayList<StringBuilder>) input.clone());
                if(!trackBeam) {
                    trackBeam = beam;
                } else if(!beam || !checkBeam(x+99, y, (ArrayList<StringBuilder>) input.clone())) {
                    break;
                }
                if(checkBeam(x, y+99, (ArrayList<StringBuilder>) input.clone())) {
                    return new long[]{x, y};
                }
            }
        }
    }

    static void solve2(ArrayList<StringBuilder> input) {
        long[] longs = find(100, 30, (ArrayList<StringBuilder>) input.clone());
        longs = find(longs[1]-30,1, (ArrayList<StringBuilder>) input.clone());
        System.out.println(longs[0]*10000 + longs[1]);
    }

    public static void main(String[] args) {
        String input = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_19.txt"));
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
        createSmartCoordinates(sbList, 100, 100);
        System.out.println("Part a: " + affectedPoints.size());
        System.out.println("Part b: "); solve2(sbList);
    }
}