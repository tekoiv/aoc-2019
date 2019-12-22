public class QueueNode {
    private Position pos;
    private int dist;
    public QueueNode(Position pos, int dist) {
        this.pos = pos;
        this.dist = dist;
    }

    public int getDist() {
        return dist;
    }

    public Position getPos() {
        return pos;
    }
}
