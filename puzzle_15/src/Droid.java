import java.util.ArrayList;

public class Droid {
    int x, y;
    ArrayList<int[]> droidPoints = new ArrayList<>();
    public Droid(int x, int t) {
        this.x = x;
        this.y = y;
    }

    public void moveDroid(int direction) {
        if(direction == 1) this.y--;
        else if(direction == 2) this.y++;
        else if(direction == 3) this.x++;
        else this.x--;
        droidPoints.add(new int[]{this.x, this.y});
    }
}
