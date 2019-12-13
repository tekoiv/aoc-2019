import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {

    static ArrayList<Integer> screen;
    static char[][] screenMatrix;
    static int part = 0;
    static Grid grid;

    static void runProgram(ArrayList<StringBuilder> input, StringBuilder inputInstruction) {
        int index = 0;
        int relativeBase = 0;
        char modeOne, modeTwo, modeThree;
        screen = new ArrayList<>();
        screen.clear();
        StringBuilder firstParameter;
        StringBuilder secondParameter;
        Ball ball = new Ball("RD", 19, 18);
        int paddle_x = 21;
        final int paddle_y = 21;
        char paddleDir;
        // && blocksLeft()
        while (!input.get(index).toString().equals("99")) {
            if(part == 1) {
                //grid.repaint();
                /*try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }*/
                ball.move(screenMatrix);
                screenMatrix[paddle_x][paddle_y] = 'E';
                if (paddle_x > ball.getX()) {
                    paddleDir = 'L';
                } else if (paddle_x < ball.getX()) {
                    paddleDir = 'R';
                } else paddleDir = 'N';
                if (paddleDir == 'L') {
                    paddle_x--;
                } else if (paddleDir == 'R') {
                    paddle_x++;
                }
                if(ball.getDirection().charAt(0) == 'R') inputInstruction = new StringBuilder("1");
                else inputInstruction = new StringBuilder("-1");
                screenMatrix[paddle_x][paddle_y] = 'H';
            }
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
                System.out.println("Input! " + inputInstruction);
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
                    //System.out.println(firstParameter.substring(nonZeroIndex));
                    screen.add(Integer.parseInt(firstParameter.toString()));
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

    static boolean blocksLeft() {
        if(part == 0) return true;
        for(int i = 0; i < 23; i++) {
            for(int j = 0; j < 42; j++) {
                if(screenMatrix[j][i] == 'B') return true;
            }
        }
        return false;
    }

    static void drawAndPrintScreen(ArrayList<Integer> screen) {
        screenMatrix = new char[42][23];
        for(int i = 0; i < 23; i++) {
            for(int j = 0; j < 42; j++) {
                screenMatrix[j][i] = ' ';
            }
        }
        int index = 0;
        while(index != screen.size() - 3) {
            int x = screen.get(index);
            int y = screen.get(index + 1);
            int tileId = screen.get(index + 2);
            if(tileId == 0) screenMatrix[x][y] = 'E';
            else if(tileId == 1) screenMatrix[x][y] = 'W';
            else if(tileId == 2) screenMatrix[x][y] = 'B';
            else if(tileId == 3) {
                screenMatrix[x][y] = 'H';
                System.out.println("Paddle starting position: (" + x + "," + y + ")");
            }
            else {
                screenMatrix[x][y] = 'P';
                System.out.println("Ball starting position: (" + x + "," + y + ")");
            }
            index += 3;
        }
        //part 1
        int blockCounter = 0;
        for(int i = 0; i < 23; i++) {
            for(int j = 0; j < 42; j++) {
                //System.out.print(screenMatrix[j][i]);
                if(screenMatrix[j][i] == 'B') blockCounter++;
            }
            //System.out.println();
        }
        System.out.println("Blocks: " + blockCounter);
    }

    static void debugPrint() {
        for(int i = 0; i < 23; i++) {
            for(int j = 0; j < 42; j++) {
                System.out.print(screenMatrix[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printPoints() {
        int index = 0;
        int finalScore = 0;
        while(index < screen.size() - 3) {
            if(screen.get(index) == -1 && screen.get(index + 1) == 0) finalScore = screen.get(index + 2);
            index += 3;
        }
        System.out.println(finalScore);
    }

    public static void main(String[] args) {
        //Visualization
        //final JFrame f = new JFrame("Visualization");

        String[] line = null;
        ArrayList<StringBuilder> input = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_13.txt"));
            line = reader.readLine().split(",");
        } catch (IOException e) {
            System.out.println(e);
        }
        for(int i = 0; i < 5000; i++) {
            input.add(new StringBuilder("0"));
        }
        for(int i = 0; i < line.length; i++) input.set(i, new StringBuilder(line[i]));
        ArrayList<StringBuilder> copyInput = (ArrayList<StringBuilder>) input.clone();
        runProgram(input, new StringBuilder("0"));
        drawAndPrintScreen(screen);
        grid = new Grid(screenMatrix);
        /*f.setBounds(0, 0, 43*15, 25*15);
        f.setBackground(Color.BLACK);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(grid);*/
        part = 1;
        copyInput.set(0, new StringBuilder("2"));
        runProgram(copyInput, new StringBuilder("0"));
        printPoints();
    }
}
