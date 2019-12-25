import java.util.concurrent.TimeUnit;

public class NWSwitch implements Runnable {
    private IntCodeComputer[] computers;
    private NAT nat;

    public NWSwitch(IntCodeComputer[] computers, NAT nat) {
        this.computers = computers;
        this.nat = nat;
    }

    public void run() {
        while (true) {
            for (IntCodeComputer computer : computers) {
                Long address = computer.out().poll();
                if (address != null) {
                    try {
                        Long x = computer.out().poll(500, TimeUnit.MILLISECONDS);
                        Long y = computer.out().poll(500, TimeUnit.MILLISECONDS);
                        if (x == null || y == null) {
                            System.err.println("Time out!");
                            return;
                        }
                        //part one
                        if (address == 255) {
                            //System.out.println(y);
                            nat.accept(x, y);
                        } else {
                            computers[Math.toIntExact(address)].in().put(x);
                            computers[Math.toIntExact(address)].in().put(y);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }
}
