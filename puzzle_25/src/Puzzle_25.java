import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Puzzle_25 {

    private String input;
    private BlockingQueue<Long> in = new ArrayBlockingQueue<>(10_000);
    private BlockingQueue<Long> out = new ArrayBlockingQueue<>(10_000);

    public static void main(String[] args) throws InterruptedException {
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

    private void run() throws InterruptedException {
        long[] commands = {101097115116013010L};
        for(long l: commands) in.put(l);
        IntCodeComputer computer = new IntCodeComputer(input, in, out);
        computer.run();
    }
}
