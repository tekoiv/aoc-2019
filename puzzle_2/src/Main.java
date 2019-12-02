import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int[] opCodes = readFile();
        //no side effects
        int[] opCodes2 = opCodes.clone();
        //puzzle instruction
        //20, 3
        opCodes[1] = 12; opCodes[2] = 2;
        calculateFinalState(opCodes);
        System.out.println("The answer for 2a): " + opCodes[0]);
        int[] nounAndVerb = calculateGivenOutput(19690720, opCodes2);
        System.out.println("The answer for 2b): " + nounAndVerb[0] * 100 + nounAndVerb[1]);
    }

    static int[] calculateGivenOutput(int output, int[] opCodes) {
        //find inputs which produce a given output
        int noun = 0;
        int verb = 0;
        int[] nonModifiedArray = opCodes.clone();
        //by increasing the noun we get close to the desired output
        //when we're close enough (< 99), we deduct the verb
        //we already know that with 12, 1 the output is smaller than
        //the desired output
        //beware of array mutation
        for(int i = 12; i < 99; i++) {
            noun = i;
            opCodes[1] = i;
            if(Math.abs(output - calculateFinalState(opCodes)[0]) < 99) {
                break;
            }
            opCodes = nonModifiedArray.clone();
        }
        opCodes = nonModifiedArray.clone();
        opCodes[1] = noun;
        verb = Math.abs(output - calculateFinalState(opCodes)[0]);
        int[] nounAndVerb = new int[2];
        nounAndVerb[0] = noun;
        nounAndVerb[1] = verb;
        return nounAndVerb;
    }

    static int[] calculateFinalState(int[] opCodes) {
        //index from which the op-code sequence is read
        //arrays are _fast_
        int index = 0;
        while(opCodes[index] != 99) {
            //adding
            if(opCodes[index] == 1) {
                opCodes[opCodes[index + 3]] = opCodes[opCodes[index + 1]] + opCodes[opCodes[index + 2]];
                index += 4;
            }
            //multiplication
            else {
                opCodes[opCodes[index + 3]] = opCodes[opCodes[index + 1]] * opCodes[opCodes[index + 2]];
                index += 4;
            }
        }
        return opCodes;
    }

    //reading the input file
    static int[] readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_2.txt"));
            String line = reader.readLine();
            String[] opCodesAsString = line.split(",");
            int[] opCodes = new int[opCodesAsString.length];
            for(int i = 0; i < opCodes.length; i++) {
                opCodes[i] = Integer.parseInt(opCodesAsString[i]);
            }
            return opCodes;
        } catch(IOException e) {
            System.out.println("IOException: " + e);
            return null;
        }
    }
}
