import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
                int i;
                if(modeOne == '0') {
                    //input[index + 1] = inputInstruction
                    i = Integer.parseInt(input.get(index + 1).toString());
                    input.set(i, inputInstruction);
                }
                else if(modeOne == '2') {
                    //input[relative_base + input[index + 1]] = inputInstruction
                    //System.out.println(input.get(index + 1));
                    //System.out.println(relativeBase);
                    i = relativeBase + Integer.parseInt(input.get(index + 1).toString());
                    input.set(i, inputInstruction);
                    //System.out.println(input.get(i));
                }
                index += 2;
            }
            else if(opCode.substring(opCode.length() - 2).matches("04|09")) {
                if(modeOne == '0') {
                    int i = Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
                }
                else if(modeOne == '2') {
                    int i = relativeBase + Integer.parseInt(input.get(index + 1).toString());
                    firstParameter = input.get(i);
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
                    firstParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 1).toString()));
                } else firstParameter = input.get(index + 1);
                if(modeTwo == '0') {
                    int i = Integer.parseInt(input.get(index + 2).toString());
                    secondParameter = input.get(i);
                }
                else if(modeTwo == '2') {
                    secondParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 2).toString()));
                } else secondParameter = input.get(index + 2);
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
                        //i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 3).toString())).toString());
                        i = relativeBase + Integer.parseInt(input.get(index + 3).toString());
                    } else {
                        i = Integer.parseInt(input.get(index + 3).toString());
                    }
                    if(Long.parseLong(firstParameter.toString()) < Long.parseLong(secondParameter.toString())) input.get(i).replace(0, input.get(i).length(), "1");
                    else input.get(i).replace(0, input.get(i).length(), "0");
                    index += 4;
                }
                else if(opCode.substring(opCode.length() - 2).equals("08")) {
                    modeThree = opCode.charAt(0);
                    int i;
                    if(modeThree == '2') {
                        //i = Integer.parseInt(input.get(relativeBase + Integer.parseInt(input.get(index + 3).toString())).toString());
                        i = relativeBase + Integer.parseInt(input.get(index + 3).toString());
                    }
                    else {
                        //i = Integer.parseInt(input.get(Integer.parseInt(input.get(index + 3).toString())).toString());
                        i = Integer.parseInt(input.get(index + 3).toString());
                    }
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
                    firstParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 1).toString()));
                } else firstParameter = input.get(index + 1);
                if(modeTwo == '0') {
                    int i = Integer.parseInt(input.get(index + 2).toString());
                    secondParameter = input.get(i);
                }
                else if(modeTwo == '2') {
                    secondParameter = input.get(relativeBase + Integer.parseInt(input.get(index + 2).toString()));
                } else secondParameter = input.get(index + 2);
                modeThree = opCode.charAt(0);
                int i;
                if(modeThree == '2') {
                    i = relativeBase + Integer.parseInt(input.get(index + 3).toString());
                } else {
                    i = Integer.parseInt(input.get(index + 3).toString());
                }
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
        //input = "104,1125899906842624,99";
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
        runProgram(sbList, new StringBuilder("5"));
    }
}

