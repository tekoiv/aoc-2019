import java.time.LocalTime;
import java.util.concurrent.locks.ReentrantLock;

import static java.time.temporal.ChronoUnit.MILLIS;

public class NAT implements Runnable {
    public static final long IDLE_LIMIT = 300L;
    private IntCodeComputer[] computers;

    //non-modifiable
    private final ReentrantLock valueLock = new ReentrantLock();
    private Long lastSentY, x, y;

    public NAT(IntCodeComputer[] computers) {
        this.computers = computers;
    }

    public void accept(Long x, Long y) {
        valueLock.lock();
        this.x = x;
        this.y = y;
        valueLock.unlock();
    }

    public void run() {
        while(true) {
            if(isIdle() && x != null && y != null) {
                try {
                    if (lastSentY != null && lastSentY.equals(y)) {
                        System.out.println("Sent twice: " + y);
                    }
                    //when sending in process, lock
                    valueLock.lock();
                    computers[0].in().put(x);
                    computers[0].in().put(y);
                    lastSentY = y;
                    x = null;
                    y = null;
                    valueLock.unlock();
                } catch (InterruptedException e) { e.printStackTrace(); return; }
            }
        }
    }

    private boolean isIdle() {
        LocalTime currentTime = LocalTime.now();
        for(IntCodeComputer computer: computers) {
            LocalTime idleTime = computer.idleTime();
            if(idleTime == null || MILLIS.between(idleTime, currentTime) < IDLE_LIMIT) {
                return false;
            }
        }
        return true;
    }

}
