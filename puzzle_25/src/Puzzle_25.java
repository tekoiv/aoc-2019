import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Puzzle_25 {

    String input;

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_25.txt"));
            sb.append(reader.readLine());
        } catch (IOException e) { e.printStackTrace(); }
        Puzzle_25 puzzle = new Puzzle_25(sb.toString());

    }

    private Puzzle_25(String input) {
        this.input = input;
    }

    private void run() {
        IntCodeComputer computer = new IntCodeComputer(input);
    }
}
