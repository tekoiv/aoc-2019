import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    //refactored intCodeComputer from puzzle 5
    static void runProgram(StringBuilder[] input, StringBuilder inputInstruction) {
        int index = 0;
        char modeOne, modeTwo;
        StringBuilder firstParameter;
        StringBuilder secondParameter;
        while (!input[index].toString().equals("99")) {
            StringBuilder opCode = input[index];
            int fillerZeros = 0;
            if(opCode.length() < 5) fillerZeros = 5 - opCode.length();
            for(int i = 0; i < fillerZeros; i++) {
                //fast stringBuilder approach
                opCode.reverse();
                opCode.append("0");
                opCode.reverse();
            }
            //modes
            modeOne = opCode.charAt(2);
            modeTwo = opCode.charAt(1);
            if(opCode.substring(opCode.length() - 2).equals("03")) {
                int i = Integer.parseInt(input[index + 1].toString());
                input[i] = inputInstruction;
                index += 2;
            }
            else if(opCode.substring(opCode.length() - 2).equals("04")) {
                //special case
                if(modeOne == '0') {
                    int i = Integer.parseInt(input[index + 1].toString());
                    System.out.println(input[i]);
                } else System.out.println(input[index + 1]);
                index += 2;
            }
            else if(opCode.substring(opCode.length() - 2).matches("05|06|07|08")) {
                if(modeOne == '0') {
                    int i = Integer.parseInt(input[index + 1].toString());
                    firstParameter = input[i];
                } else firstParameter = input[index + 1];
                if(modeTwo == '0') {
                    int i = Integer.parseInt(input[index + 2].toString());
                    secondParameter = input[i];
                } else secondParameter = input[index + 2];
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
                    int i = Integer.parseInt(input[index + 3].toString());
                    if(Integer.parseInt(firstParameter.toString()) < Integer.parseInt(secondParameter.toString())) input[i].replace(0, input[i].length(), "1");
                    else input[i].replace(0, input[i].length(), "0");
                    index += 4;
                }
                else if(opCode.substring(opCode.length() - 2).equals("08")) {
                    int i = Integer.parseInt(input[index + 3].toString());
                    if(firstParameter.toString().equals(secondParameter.toString())) input[i].replace(0, input[i].length(), "1");
                    else input[i].replace(0, input[i].length(), "0");
                    index += 4;
                }
            }
            else {
                if(modeOne == '0') {
                    int i = Integer.parseInt(input[index + 1].toString());
                    firstParameter = input[i];
                } else firstParameter = input[index + 1];
                if(modeTwo == '0') {
                    int i = Integer.parseInt(input[index + 2].toString());
                    secondParameter = input[i];
                } else secondParameter = input[index + 2];
                int i = Integer.parseInt(input[index + 3].toString());
                //possibilities
                if(opCode.substring(opCode.length() - 2).equals("01")) {
                    int sum = Integer.parseInt(firstParameter.toString()) + Integer.parseInt(secondParameter.toString());
                    input[i].replace(0, input[i].length(), Integer.toString(sum));
                }
                else if(opCode.substring(opCode.length() - 2).equals("02")) {
                    int product = Integer.parseInt(firstParameter.toString()) * Integer.parseInt(secondParameter.toString());
                    input[i].replace(0, input[i].length(), Integer.toString(product));
                }
                index += 4;
            }
        }
    }

    public static void main(String[] args) {
        String input = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_5.txt"));
            input = reader.readLine();
        } catch (IOException e) { System.out.println(e); }
        String[] array = input.split(",");
        StringBuilder[] sbArray = new StringBuilder[array.length];
        for(int i = 0; i < sbArray.length; i++) {
            sbArray[i] = new StringBuilder(array[i]);
        }
        runProgram(sbArray, new StringBuilder("5"));
    }
}
