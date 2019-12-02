import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int[] opCodes = readFile();
        //puzzle instruction
        opCodes[1] = 12; opCodes[2] = 2;
        calculateFinalState(opCodes);
        System.out.println("The answer for 2a): " + opCodes[0]);
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
