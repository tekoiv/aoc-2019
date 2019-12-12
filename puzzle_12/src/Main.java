import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    /*
    The idea behind this is to simply simulate the moons in part 1 and use a little number theory in part 2.
    In part 2, we check the axis' separately and calculate when the pattern starts to repeat. When we find the
    number of steps between repeats, we calculate the least common divider. This is the amount of ticks that
    are required for the moons to be in the same position as sometime before.
     */

    static void timeStep(ArrayList<Moon> moons) {
        for(int i = 0; i < moons.size(); i++) {
            int counterX = 0; int counterY = 0; int counterZ = 0;
            for(int j = 0; j < moons.size(); j++) {
                if(j != i) {
                    if(moons.get(i).getX() < moons.get(j).getX()) counterX++;
                    else if(moons.get(i).getX() > moons.get(j).getX()) counterX--;

                    if(moons.get(i).getY() < moons.get(j).getY()) counterY++;
                    else if(moons.get(i).getY() > moons.get(j).getY()) counterY--;

                    if(moons.get(i).getZ() < moons.get(j).getZ()) counterZ++;
                    else if(moons.get(i).getZ() > moons.get(j).getZ()) counterZ--;
                }
            }
            moons.get(i).setVelx(moons.get(i).getVelx() + counterX);
            moons.get(i).setVely(moons.get(i).getVely() + counterY);
            moons.get(i).setVelz(moons.get(i).getVelz() + counterZ);
        }
        for(Moon m: moons) {
            m.setX(m.getX() + m.getVelx());
            m.setY(m.getY() + m.getVely());
            m.setZ(m.getZ() + m.getVelz());
        }
    }

    //find repetitions for each axis separately
    static BigInteger solveBWithAxisPatterns(ArrayList<Moon> moons){
        BigInteger index = new BigInteger("0");

        //x variables
        boolean xFound = false;
        Map<String, BigInteger> xPositions = new HashMap<>();
        BigInteger repeatedXIndex;
        BigInteger stepsBetweenRepeatsX = null;

        //y variables
        boolean yFound = false;
        Map<String, BigInteger> yPositions = new HashMap<>();
        BigInteger repeatedYIndex;
        BigInteger stepsBetweenRepeatsY = null;

        //z variables
        boolean zFound = false;
        Map<String, BigInteger> zPositions = new HashMap<>();
        BigInteger repeatedZIndex;
        BigInteger stepsBetweenRepeatsZ = null;

        while(!xFound || !yFound || !zFound) {

            String xPosition = getXPos(moons);
            if(!xFound && !xPositions.containsKey(xPosition)) {
                xPositions.put(xPosition, index);
            } else if(!xFound) {
                repeatedXIndex = xPositions.get(xPosition);
                stepsBetweenRepeatsX = index.subtract(repeatedXIndex);
                xFound = true;
            }

            String yPosition = getYPos(moons);
            if(!yFound && !yPositions.containsKey(yPosition)) {
                yPositions.put(yPosition, index);
            } else if(!yFound) {
                repeatedYIndex = yPositions.get(yPosition);
                stepsBetweenRepeatsY = index.subtract(repeatedYIndex);
                yFound = true;
            }

            String zPosition = getZPos(moons);
            if(!zFound && !zPositions.containsKey(zPosition)) {
                zPositions.put(zPosition, index);
            } else if(!zFound) {
                repeatedZIndex = zPositions.get(zPosition);
                stepsBetweenRepeatsZ = index.subtract(repeatedZIndex);
                zFound = true;
            }
            timeStep(moons);
            index = index.add(new BigInteger("1"));
        }
        return getLCM(stepsBetweenRepeatsX, stepsBetweenRepeatsY, stepsBetweenRepeatsZ);
    }

    //the next two functions are to determine the least common multiplier of three numbers
    static BigInteger getLCM(BigInteger x, BigInteger y, BigInteger z) {
        BigInteger lcmOne = lcm(x, y);
        BigInteger lcmTwo = lcm(y, z);
        return lcm(lcmOne, lcmTwo);
    }

    static BigInteger lcm(BigInteger a, BigInteger b) {
        BigInteger gcd = a.gcd(b);
        BigInteger absProduct = a.multiply(b).abs();
        return absProduct.divide(gcd);
    }

    static String getXPos(ArrayList<Moon> moons) {
        StringBuilder sb = new StringBuilder();
        moons.stream().map(moon -> Integer.toString(moon.getX())).forEach(s -> sb.append(s + ","));
        moons.stream().map(moon -> Integer.toString(moon.getVelx())).forEach(s -> sb.append(s + ","));
        return sb.toString();
    }

    static String getYPos(ArrayList<Moon> moons) {
        StringBuilder sb = new StringBuilder();
        moons.stream().map(moon -> Integer.toString(moon.getY())).forEach(s -> sb.append(s + ","));
        moons.stream().map(moon -> Integer.toString(moon.getVely())).forEach(s -> sb.append(s + ","));
        return sb.toString();
    }

    static String getZPos(ArrayList<Moon> moons) {
        StringBuilder sb = new StringBuilder();
        moons.stream().map(moon -> Integer.toString(moon.getZ())).forEach(s -> sb.append(s + ","));
        moons.stream().map(moon -> Integer.toString(moon.getVely())).forEach(s -> sb.append(s + ","));
        return sb.toString();
    }

    static int getSystemEnergy(ArrayList<Moon> moons) {
        int sum = 0;
        for(Moon m: moons) {
            int pot = Math.abs(m.getX()) + Math.abs(m.getY()) + Math.abs(m.getZ());
            int kin = Math.abs(m.getVelx()) + Math.abs(m.getVely()) + Math.abs(m.getVelz());
            sum += pot*kin;
        }
        return sum;
    }

    public static void main(String[] args) {
        ArrayList<Moon> moons = new ArrayList<>();
        moons.add(new Moon(15, -2,-6));
        moons.add(new Moon(-5, -4, -11));
        moons.add(new Moon(0, -6, 0));
        moons.add(new Moon(5, 9, 6));
        for(int i = 0; i < 1000; i++) {
            timeStep(moons);
        }
        System.out.println("System energy after 1000 steps: " + getSystemEnergy(moons));
        //part 2
        System.out.println(solveBWithAxisPatterns(moons));
    }
}
