public class Moon {
    int x, y, z;
    private int velx, vely, velz;
    public Moon(int x, int y, int z) {
        this.velx = 0;
        this.vely = 0;
        this.velz = 0;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getVelx() {
        return velx;
    }

    public int getVely() {
        return vely;
    }

    public int getVelz() {
        return velz;
    }

    public void setVelx(int velx) {
        this.velx = velx;
    }

    public void setVely(int vely) {
        this.vely = vely;
    }

    public void setVelz(int velz) {
        this.velz = velz;
    }
}
