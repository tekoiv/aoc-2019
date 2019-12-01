import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //read input file
        int inputLength = 100;
        int[] masses = readFile(inputLength);
        calculateTotalFuelRequirement(masses);
    }

    static void calculateTotalFuelRequirement(int[] masses) {
        /*
        Fuel required to launch a given module:
        Mass/3, round down, subtract 2.
        */
        int totalFuel = 0;
        for(int i = 0; i < masses.length; i++) {
            totalFuel += (masses[i] / 3 - 2);
        }
        System.out.println("Puzzle 1a): " + totalFuel);
        totalFuel = 0;
        for(int i = 0; i < masses.length; i++) {
            totalFuel += totalFuelCount(masses[i], 0);
        }
        System.out.println("Puzzle 1b): " + totalFuel);
    }

    //recursive function to help with 1b)
    static int totalFuelCount(int mass, int fuel) {
        int totalFuel = fuel;
        if (mass / 3 - 2 > 0) {
            totalFuel += mass / 3 - 2;
            return totalFuelCount(mass / 3 - 2, totalFuel);
        } else {
            return totalFuel;
        }
    }

    //reading the input file
    static int[] readFile(int inputLength) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_1.txt"));
            int[] masses = new int[inputLength];
            String line;
            for(int i = 0; i < inputLength; i++) {
                line = reader.readLine();
                masses[i] = Integer.parseInt(line);
            }
            return masses;
        } catch(IOException e) {
            System.out.println("IOException: " + e);
            return null;
        }
    }
}
