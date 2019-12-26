import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Puzzle_25 {

    private String input;
    private BlockingQueue<Long> in = new ArrayBlockingQueue<>(10_000);
    private BlockingQueue<Long> out = new ArrayBlockingQueue<>(10_000);

    /*
    You can play this by giving instructions :) The instructions are limited to:
    east, north, west, south, take <item>, inv.
     */

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_25.txt"));
            sb.append(reader.readLine());
        } catch (IOException e) { e.printStackTrace(); }
        Puzzle_25 puzzle = new Puzzle_25(sb.toString());
        puzzle.run();
    }

    private Puzzle_25(String input) {
        this.input = input;
    }

    private void run() {
        IntCodeComputer computer = new IntCodeComputer(input, in, out);
        computer.run();
    }
}