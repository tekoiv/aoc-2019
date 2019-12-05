import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
This could be about 100 lines shorter. Got confused
by the instructions and just tried to get this over with.
 */

public class Main {
    public static void main(String[] args){
        // read file and set into a string array
        String inputAsString = "";
        String inputInstruction = "5";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_5.txt"));
            inputAsString = reader.readLine();
        } catch(IOException e) {
            System.out.println(e);
        }
        String[] input = inputAsString.split(",");
        runProgram(input, inputInstruction);

    }

    static void runProgram(String[] input, String inputInstruction) {
        int i = 0;
        while(!input[i].equals("99")) {
            //position modes
            if(input[i].equals("3")) {
                int index = toInt(input[i + 1]);
                input[index] = inputInstruction;
                i += 2;
            }
            else if(input[i].equals("4")) {
                int index = toInt(input[i + 1]);
                System.out.println(input[index]);
                i += 2;
            }
            else if(input[i].equals("1")) {
                int value1st, value2nd;
                int index = toInt(input[i + 1]);
                value1st = toInt(input[index]);
                index = toInt(input[i + 2]);
                value2nd = toInt(input[index]);
                index = toInt(input[i + 3]);
                input[index] = Integer.toString(value1st + value2nd);
                i += 4;
            }
            else if(input[i].equals("2")) {
                int value1st, value2nd;
                int index = toInt(input[i + 1]);
                value1st = toInt(input[index]);
                index = toInt(input[i + 2]);
                value2nd = toInt(input[index]);
                index = toInt(input[i + 3]);
                input[index] = Integer.toString(value1st * value2nd);
                i += 4;
            }
            else if(input[i].equals("5")) {
                int index = toInt(input[i + 1]);
                int p1 = toInt(input[index]);
                if(p1 != 0) {
                    index = toInt(input[i + 2]);
                    int p2 = toInt(input[index]);
                    i = p2;
                } else i += 3;
            }
            else if(input[i].equals("6")) {
                int index = toInt(input[i + 1]);
                int p1 = toInt(input[index]);
                if(p1 == 0) {
                    index = toInt(input[i + 2]);
                    int p2 = toInt(input[index]);
                    i = p2;
                } else i += 3;
            }
            else if(input[i].equals("7")) {
                int index = toInt(input[i + 1]);
                int p1 = toInt(input[index]);
                index = toInt(input[i + 2]);
                int p2 = toInt(input[index]);
                index = toInt(input[i + 3]);
                if(p1 < p2) {
                    input[index] = "1";
                } else input[index] = "0";
                i += 4;
            }
            else if(input[i].equals("8")) {
                int index = toInt(input[i + 1]);
                int p1 = toInt(input[index]);
                index = toInt(input[i + 2]);
                int p2 = toInt(input[index]);
                index = toInt(input[i + 3]);
                if(p1 == p2) {
                    input[index] = "1";
                } else input[index] = "0";
                i += 4;
            }
            //immediate modes
            else {
                if(input[i].length() < 5) {
                    int fillerZeros = 5 - input[i].length();
                    for(int j = 0; j < fillerZeros; j++) {
                        input[i] = "0" + input[i];
                    }
                }
                char mode1st = input[i].charAt(2);
                char mode2nd = input[i].charAt(1);
                if(input[i].substring(input[i].length() - 2).equals("01")) {
                    int index;
                    int value1st;
                    int value2nd;
                    if(mode1st == '0') {
                        index = toInt(input[i + 1]);
                        value1st = toInt(input[index]);
                    } else {
                        value1st = toInt(input[i + 1]);
                    }
                    if(mode2nd == '0') {
                        index = toInt(input[i + 2]);
                        value2nd = toInt(input[index]);
                    } else {
                        value2nd = toInt(input[i + 2]);
                    }
                    index = toInt(input[i + 3]);
                    input[index] = Integer.toString(value1st + value2nd);
                    i += 4;
                }
                else if(input[i].substring(input[i].length() - 2).equals("02")) {
                    int index;
                    int value1st;
                    int value2nd;
                    if(mode1st == '0') {
                        index = toInt(input[i + 1]);
                        value1st = toInt(input[index]);
                    } else {
                        value1st = toInt(input[i + 1]);
                    }
                    if(mode2nd == '0') {
                        index = toInt(input[i + 2]);
                        value2nd = toInt(input[index]);
                    } else {
                        value2nd = toInt(input[i + 2]);
                    }
                    index = toInt(input[i + 3]);
                    input[index] = Integer.toString(value1st * value2nd);
                    i += 4;
                }
                else if(input[i].substring(input[i].length() - 2).equals("04")) {
                    int index, p1;
                    if(mode1st == '0') {
                        index = toInt(input[i + 1]);
                        p1 = toInt(input[index]);
                    } else p1 = toInt(input[i + 1]);
                    System.out.println(p1);
                    i += 2;
                }
                else if(input[i].substring(input[i].length() - 2).equals("05")) {
                    int index, p1, p2;
                    if(mode1st == '0') {
                        index = toInt(input[i + 1]);
                        p1 = toInt(input[index]);
                    } else p1 = toInt(input[i + 1]);
                    if(p1 != 0) {
                        if(mode2nd == '0') {
                            index = toInt(input[i + 2]);
                            p2 = toInt(input[index]);
                        } else p2 = toInt(input[i + 2]);
                        i = p2;
                    } else i += 3;
                }
                else if(input[i].substring(input[i].length() - 2).equals("06")) {
                    int index, p1, p2;
                    if(mode1st == '0') {
                        index = toInt(input[i + 1]);
                        p1 = toInt(input[index]);
                    } else p1 = toInt(input[i + 1]);
                    if(p1 == 0) {
                        if(mode2nd == '0') {
                            index = toInt(input[i + 2]);
                            p2 = toInt(input[index]);
                        } else p2 = toInt(input[i + 2]);
                        i = p2;
                    } else i += 3;
                }
                else if(input[i].substring(input[i].length() - 2).equals("07")) {
                    int index, p1, p2;
                    if(mode1st == '0') {
                        index = toInt(input[i + 1]);
                        p1 = toInt(input[index]);
                    } else p1 = toInt(input[i + 1]);
                    if(mode2nd == '0') {
                        index = toInt(input[i + 2]);
                        p2 = toInt(input[index]);
                    } else p2 = toInt(input[i + 2]);
                    index = toInt(input[i + 3]);
                    if(p1 < p2) {
                        input[index] = "1";
                    } else input[index] = "0";
                    i += 4;
                }
                else if(input[i].substring(input[i].length() - 2).equals("08")) {
                    int index, p1, p2;
                    if(mode1st == '0') {
                        index = toInt(input[i + 1]);
                        p1 = toInt(input[index]);
                    } else p1 = toInt(input[i + 1]);
                    if(mode2nd == '0') {
                        index = toInt(input[i + 2]);
                        p2 = toInt(input[index]);
                    } else p2 = toInt(input[i + 2]);
                    index = toInt(input[i + 3]);
                    if(p1 == p2) {
                        input[index] = "1";
                    } else input[index] = "0";
                    i += 4;
                }
                else {
                    System.out.println("This shouldn't happen. " + i);
                }
            }
        }
    }

    static int toInt(String s) {
        return Integer.parseInt(s);
    }
}
