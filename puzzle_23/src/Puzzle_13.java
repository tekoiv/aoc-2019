import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Puzzle_13 {

    private String input;

    public static void main(String[] args) throws InterruptedException {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_23.txt"));
            line = reader.readLine();
        } catch(IOException e) { e.printStackTrace(); }
        Puzzle_13 puzzle = new Puzzle_13(line);
        puzzle.run();
    }

    private Puzzle_13(String input) {
        this.input = input;
    }

    private void run() throws InterruptedException {
        //initial computer setup
        IntCodeComputer[] computers = new IntCodeComputer[50];
        for(int i = 0; i < 50; i++) {
            computers[i] = new IntCodeComputer(input);
            computers[i].in().put((long) i);
        }

        //network devices
        NAT nat = new NAT(computers);
        NWSwitch networkSwitch = new NWSwitch(computers, nat);

        //start network concurrently
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(computers.length + 2);
        pool.execute(networkSwitch);
        pool.execute(nat);
        for(int i = 0; i < 50; i++) {
            pool.execute(computers[i]);
        }
        pool.shutdown();

        //DEBUG
        pool.awaitTermination(10L, TimeUnit.SECONDS);
    }
}