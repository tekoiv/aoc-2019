import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
This could be about 100 lines shorter. Got confused
by the instructions and just tried to get this over with.
 */

public class Main {

    public static ArrayList<Integer> permutations;
    public static int phase;
    public static int lastOutput;

    public static void main(String[] args) {
        // read file and set into a string array
        String inputAsString = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_7.txt"));
            inputAsString = reader.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }
        String[] input = inputAsString.split(",");
        //part a)
        /*
        String[] clone = input.clone();
        int largestOutput = 0;
        int index = 0;
        int[] a = {0,1,2,3,4};
        permutations = new ArrayList<>();
        permute(0, a);

        for(int i = 0; i < permutations.size(); i++) {
            String inputSignal = "0";
            String phaseSequence = Integer.toString(permutations.get(i));
            if(phaseSequence.length() < 5) {
                int fillerZeros = 5 - phaseSequence.length();
                for(int k = 0; k < fillerZeros; k++) {
                    phaseSequence = "0" + phaseSequence;
                }
            }
            for(int j = 0; j < 5; j++) {
                String[] nonMutated = clone.clone();
                inputSignal = runProgram(nonMutated, phaseSequence.substring(j, j + 1), inputSignal, "phase");
            }
            if(Integer.parseInt(inputSignal) > largestOutput) {
                largestOutput = Integer.parseInt(inputSignal);
                index = i;
            }
        }
        //answer for 7a)
        System.out.println(largestOutput + " at index " + index);
        */
        //part 2
        int[] b = {5,6,7,8,9};
        permutations = new ArrayList<>();
        permute(0, b);
        String[] testInput = "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10".split(",");
        int largestOutput = 0;

        /*for(int i = 0; i < permutations.size(); i++) {
            String[] clone = testInput.clone();
            String inputSignal = "0";
            String phaseSequence = Integer.toString(permutations.get(i));
            phase = 0;
            if(phaseSequence.length() < 5) {
                int fillerZeros = 5 - phaseSequence.length();
                for(int j = 0; j < fillerZeros; j++) {
                    phaseSequence = "0" + phaseSequence;
                }
            }
            String lastSignal = runProgram(clone, phaseSequence.substring(0, 1), inputSignal, phaseSequence);
            if(Integer.parseInt(lastSignal) > largestOutput) {
                largestOutput = Integer.parseInt(lastSignal);
            }
        }
        //answer for 2b)
        System.out.println(largestOutput);*/
        //test
        String[] clone = testInput.clone();
        String phaseSequence = "98765";
        phase = 0;
        String lastSignal = runProgram(clone, phaseSequence.substring(0, 1), "0", phaseSequence);
        System.out.println(lastSignal);
    }

    //permutations
    public static void permute(int start, int[] input) {
        if (start == input.length) {
            String numberAsString = "";
            for(int x: input){
                numberAsString += Integer.toString(x);
            }
            permutations.add(Integer.parseInt(numberAsString));
            return;
        }
        for (int i = start; i < input.length; i++) {
            int temp = input[i];
            input[i] = input[start];
            input[start] = temp;

            permute(start + 1, input);

            int temp2 = input[i];
            input[i] = input[start];
            input[start] = temp2;
        }
    }

    static String runProgram(String[] input, String inputInstruction1, String inputInstruction2, String phaseSequence) {
        int i = 0;
        int threesCount = 0;
        String output = "0";
        String instOne = inputInstruction1;
        String instTwo;
        String finalThrusterOutput = "0";
        while(!input[i].equals("99")) {
            //position modes
            if(input[i].equals("3")) {
                int index = toInt(input[i + 1]);
                if(threesCount == 0) {
                    input[index] = instOne;
                    instOne = phaseSequence.substring(phase, phase + 1);
                    threesCount++;
                } else if(threesCount == 1) {
                    instTwo = output;
                    input[index] = instTwo;
                    threesCount = 0;
                    if(phase == 4) {
                        phase = 0;
                        finalThrusterOutput = output;
                    } else phase++;
                }
                i += 2;
            }
            else if(input[i].equals("4")) {
                int index = toInt(input[i + 1]);
                output = input[index];
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
                    output = Integer.toString(p1);
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
        return finalThrusterOutput;
    }

    static int toInt(String s) {
        return Integer.parseInt(s);
    }
}
