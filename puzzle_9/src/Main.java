import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
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
                //fast stringBuilder approach
                opCode.reverse();
                opCode.append("0");
                opCode.reverse();
            }
            //modes
            modeOne = opCode.charAt(2);
            modeTwo = opCode.charAt(1);
            if(opCode.substring(opCode.length() - 2).equals("03")) {
                int i = Integer.parseInt(input.get(index + 1).toString());
                input.set(i, inputInstruction);
                index += 2;
            }
            else if(opCode.substring(opCode.length() - 2).matches("04|09")) {
                if(modeOne == '0') {
                    int i = Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                }
                else if(modeOne == '2') {
                    //int i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 1).toString())).toString());
                    //firstParameter = input.get(i);
                    firstParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 1).toString()));
                } else firstParameter = input.get(index + 1);

                if(opCode.substring(opCode.length() - 2).equals("04")) {
                    System.out.println(firstParameter);
                    if(firstParameter.toString().equals("99")) break;
                } else {
                    relativeBase += Integer.parseInt(firstParameter.toString());
                }
                index += 2;
            }
            else if(opCode.substring(opCode.length() - 2).matches("05|06|07|08")) {
                if(modeOne == '0') {
                    int i = Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                }
                else if(modeOne == '2') {
                    int i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 1).toString())).toString());
                    firstParameter = input.get(i);
                    firstParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 1).toString()));
                } else firstParameter = input.get(index + 1);
                if(modeTwo == '0') {
                    int i = Integer.parseInt(input.get(index + 2).toString());
                    secondParameter = input.get(i);
                }
                else if(modeTwo == '2') {
                    int i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 2).toString())).toString());
                    secondParameter = input.get(i);
                    secondParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 2).toString()));
                }
                else secondParameter = input.get(index + 2);
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
                    modeThree = opCode.charAt(0);
                    int i;
                    if(modeThree == '2') {
                        i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 3).toString())).toString());
                    } else i = Integer.parseInt(input.get(index + 3).toString());
                    if(Long.parseLong(firstParameter.toString()) < Long.parseLong(secondParameter.toString())) input.get(i).replace(0, input.get(i).length(), "1");
                    else input.get(i).replace(0, input.get(i).length(), "0");
                    index += 4;
                }
                else if(opCode.substring(opCode.length() - 2).equals("08")) {
                    int i = Integer.parseInt(input.get(index + 3).toString());
                    if(firstParameter.toString().equals(secondParameter.toString())) input.get(i).replace(0, input.get(i).length(), "1");
                    else input.get(i).replace(0, input.get(i).length(), "0");
                    index += 4;
                }
            }
            else {
                if(modeOne == '0') {
                    int i = Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                }
                else if(modeOne == '2'){
                    int i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 1).toString())).toString());
                    firstParameter = input.get(i);
                    firstParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 1).toString()));
                } else firstParameter = input.get(index + 1);
                if(modeTwo == '0') {
                    int i = Integer.parseInt(input.get(index + 2).toString());
                    secondParameter = input.get(i);
                }
                else if(modeTwo == '2') {
                    int i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 2).toString())).toString());
                    secondParameter = input.get(i);
                    secondParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 2).toString()));
                } else secondParameter = input.get(index + 2);
                modeThree = opCode.charAt(0);
                int i;
                if(modeThree == '2') {
                    i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 3).toString())).toString());
                } else i = Integer.parseInt(input.get(index + 3).toString());
                //possibilities
                if(opCode.substring(opCode.length() - 2).equals("01")) {
                    long sum = Long.parseLong(firstParameter.toString()) + Long.parseLong(secondParameter.toString());
                    input.get(i).replace(0, input.get(i).length(), Long.toString(sum));
                }
                else if(opCode.substring(opCode.length() - 2).equals("02")) {
                    long product = Long.parseLong(firstParameter.toString()) * Long.parseLong(secondParameter.toString());
                    input.get(i).replace(0, input.get(i).length(), Long.toString(product));
                }
                index += 4;
            }
        }
    }

    public static void main(String[] args) {
        String input = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_9.txt"));
            input = reader.readLine();
        } catch (IOException e) { System.out.println(e); }
        input = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
        String[] array = input.split(",");
        StringBuilder[] sbArray = new StringBuilder[array.length];
        ArrayList<StringBuilder> sbList = new ArrayList<>(Collections.nCopies(100000, new StringBuilder("0")));
        for(int i = 0; i < sbArray.length; i++) {
            sbList.set(i, new StringBuilder(array[i]));
        }
        runProgram(sbList, new StringBuilder("1"));
    }
}
