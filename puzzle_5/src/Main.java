import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        // read file and set into a string array
        String inputAsString = "";
        String inputInstruction = "1";
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
                    int index = -1;
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
                }
                else if(input[i].substring(input[i].length() - 2).equals("02")) {
                    int index = -1;
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
                }
                i += 4;
            }
        }
    }

    static int toInt(String s) {
        return Integer.parseInt(s);
    }
}
