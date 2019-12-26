import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class IntCodeComputer implements Runnable {

    //important to keep the intcode computer
    //ready for incoming input.
    //concurrency.
    public enum Status {
        RUNNING,
        STOPPED,
        WAITING
    }

    public enum NetworkStatus {
        RUNNING,
        IDLE
    }

    private Map<Integer, Long> memory;
    private BlockingQueue<Long> in, out;
    private int pc = 0;
    private int[] modes = new int[3];

    private Long relativeBaseIndex = 0L;

    private Status status = Status.STOPPED;
    private NetworkStatus nst = NetworkStatus.RUNNING;
    private LocalTime idleSince = null;

    public IntCodeComputer(String code, BlockingQueue<Long> in, BlockingQueue<Long> out) {
        Long[] input = Arrays.stream(code.split(","))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .boxed()
                .toArray(Long[]::new);
        memory = new HashMap<>(input.length);
        for(int i = 0; i < input.length; i++) {
            memory.put(i, input[i]);
        }
        this.in = in;
        this.out = out;
    }

    public IntCodeComputer(String input) {
        //default constructor. Takes an input, parses
        //it into an array without trailing spaces
        Long[] inputArray = Arrays.stream(input.split(","))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .boxed()
                .toArray(Long[]::new);
        //create memory
        memory = new HashMap<>(inputArray.length);
        for(int i = 0; i < inputArray.length; i++) {
            memory.put(i, inputArray[i]);
        }
        this.in = new ArrayBlockingQueue<>(10_000);
        this.out = new ArrayBlockingQueue<>(10_000);
    }

    //concurrent implementation. the intcode computer
    //can wait for input instructions, be halted
    //or running normally.
    public void run() {
        status = Status.RUNNING;
        try {
            while(load(pc) != 99) {
                //format to length five, integer
                var command = String.format("%05d", load(pc));
                var opCode = Integer.parseInt(command.substring(3));
                modes[0] = getParam(command, 1);
                modes[1] = getParam(command, 2);
                modes[2] = getParam(command, 3);
                if(opCode == 3) {
                    status = Status.WAITING;
                    //read from blockQueue
                    Long value = read();
                    write(modes[0], value);
                    if(value != -1) {
                        status = Status.RUNNING;
                        nst = NetworkStatus.RUNNING;
                        idleSince = null;
                    } else {
                        if (nst != NetworkStatus.IDLE) {
                            idleSince = LocalTime.now();
                        }
                        nst = NetworkStatus.IDLE;
                    }
                    pc += 2;
                } else if(opCode == 4) {
                    out.put(load(modes[0]));
                    pc += 2;
                } else if(opCode == 9) {
                    relativeBaseIndex += load(modes[0]);
                    pc += 2;
                } else if(opCode == 5) {
                    pc = load(modes[0]) > 0 ? load(modes[1]).intValue() : pc + 3;
                } else if(opCode == 6) {
                    pc = load(modes[0]) == 0 ? load(modes[1]).intValue() : pc + 3;
                } else if(opCode == 1) {
                    write(modes[2], load(modes[0]) + load(modes[1]));
                    pc += 4;
                } else if(opCode == 2) {
                    write(modes[2], load(modes[0]) * load(modes[1]));
                    pc += 4;
                } else if(opCode == 7) {
                    write(modes[2], load(modes[0]) < load(modes[1]) ? 1L : 0L);
                    pc += 4;
                } else if(opCode == 8) {
                    write(modes[2], load(modes[0]).equals(load(modes[1])) ? 1L : 0L);
                    pc += 4;
                }
            }
        } catch (InterruptedException e) { e.printStackTrace(); } finally {
            status = Status.STOPPED;
        }
    }

    private Long read() {
        Long value = 0L;
        try {
            value = in.poll(50, TimeUnit.MILLISECONDS);
        } catch(InterruptedException e) { e.printStackTrace(); }
        return value == null ? -1L : value;
    }

    private Long load(int index) {
        return memory.computeIfAbsent(index, x -> 0L);
    }

    private void write(int index, Long value) {
        memory.put(index, value);
    }

    private int getParam(String command, int i) {
        switch(command.charAt(3 - i)) {
            case '0':
                return load(pc + i).intValue();
            case '1':
                return pc + i;
            case '2':
                return Long.valueOf(load(pc + i) + relativeBaseIndex).intValue();
            default:
                throw new RuntimeException("This isn't a proper opMode: " + command.charAt(3 - i));
        }
    }

    public BlockingQueue<Long> in() {
        return in;
    }

    public BlockingQueue<Long> out() {
        return out;
    }

    public void send(long c) throws InterruptedException {
        in.put(c);
    }

    public LocalTime idleTime() {
        return this.idleSince;
    }
}