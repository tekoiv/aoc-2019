import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    //refactored intCodeComputer from puzzle 5
    static void runProgram(ArrayList<StringBuilder> input, StringBuilder inputInstruction) {
        Droid droid = new Droid(0, 0, 4);
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
                        droid.setDir(Integer.parseInt(inputInstruction.toString()));
                        if(droid.getDir() == 4) inputInstruction = new StringBuilder("2");
                        else if(droid.getDir() == 3) inputInstruction = new StringBuilder("1");
                        else if(droid.getDir() == 2) inputInstruction = new StringBuilder("3");
                        else if(droid.getDir() == 1) inputInstruction = new StringBuilder("4");
                        droid.printWallsAndPoints();
                    }
                    else if(firstParameter.substring(nonZeroIndex).equals("2")) {
                        droid.setDir(Integer.parseInt(inputInstruction.toString()));
                        if(droid.getDir() == 4) inputInstruction = new StringBuilder("2");
                        else if(droid.getDir() == 3) inputInstruction = new StringBuilder("1");
                        else if(droid.getDir() == 2) inputInstruction = new StringBuilder("3");
                        else if(droid.getDir() == 1) inputInstruction = new StringBuilder("4");
                        droid.moveDroid(Integer.parseInt(inputInstruction.toString()));
                        droid.setTankLocation();
                        System.out.println("FOUND");
                    }
                    //System.out.println(firstParameter.substring(nonZeroIndex));
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
    }
}

