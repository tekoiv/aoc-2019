import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    public static int vaporizedCounter = 0;
    public static int[] lastVaporized = new int[2];

    static void solve1(char[][] input, int width, int height) {
        ArrayList<int[]> anglesList = new ArrayList<>();
        ArrayList<int[]> asteroidCoordinates = new ArrayList<>();
        ArrayList<Character> straightAnglesList = new ArrayList<>();
        int count;
        int maxCount = Integer.MIN_VALUE;
        int bestAsteroid = 0;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(input[j][i] == '#') {
                    asteroidCoordinates.add(new int[]{j, i});
                }
            }
        }
        ArrayList<Integer> visibleAsteroids = new ArrayList<>();
        for(int i = 0; i < asteroidCoordinates.size(); i++) {
            anglesList.clear();
            straightAnglesList.clear();            count = 0;
            for(int j = 0; j < asteroidCoordinates.size(); j++) {
                if (i != j) {
                    int xOne = asteroidCoordinates.get(i)[0];
                    int yOne = asteroidCoordinates.get(i)[1];
                    int xTwo = asteroidCoordinates.get(j)[0];
                    int yTwo = asteroidCoordinates.get(j)[1];
                    if(xOne == xTwo) {
                        if(yOne < yTwo && straightAnglesList.indexOf('D') == -1) {
                            straightAnglesList.add('D');
                            count++;
                        }
                        else if(yTwo < yOne && straightAnglesList.indexOf('U') == -1) {
                            straightAnglesList.add('U');
                            count++;
                        }
                    }
                    else if(yOne == yTwo) {
                        if(xOne < xTwo && straightAnglesList.indexOf('R') == -1) {
                            straightAnglesList.add('R');
                            count++;
                        }
                        else if(xTwo < xOne && straightAnglesList.indexOf('L') == -1) {
                            straightAnglesList.add('L');
                            count++;
                        }
                    }
                    else {
                        int x = xTwo - xOne;
                        int y = yTwo - yOne;
                        int gcd = gcd(Math.abs(x), Math.abs(y));
                        int[] angle = new int[2];
                        angle[0] = x / gcd;
                        angle[1] = y / gcd;
                        boolean isFound = false;
                        for(int k = 0; k < anglesList.size(); k++) {
                            if(Arrays.equals(anglesList.get(k), angle)) {
                                isFound = true;
                                break;
                            }
                        }
                        if(!isFound) {
                            count++;
                            anglesList.add(angle);
                        }
                    }
                }
            }
            visibleAsteroids.add(count);
            if(count > maxCount) {
                maxCount = count;
                bestAsteroid = i;
            }
        }
        for(int i: visibleAsteroids) System.out.print(i + ", ");
        System.out.println();
        System.out.println(asteroidCoordinates.get(bestAsteroid)[0] + ", " + asteroidCoordinates.get(bestAsteroid)[1]);
        System.out.println("Most asteroids: " + maxCount);
    }

    static void solve2(int x, int y) {
        //best asteroid coordinate is (x, y)
        String[] testString = (".#....#####...#..\n" +
                "##...##.#####..##\n" +
                "##...#...#.#####.\n" +
                "..#.....X...###..\n" +
                "..#.#.....#....##").split("\n");
        char[][] map = new char[testString[0].length()][testString.length];
        for(int i = 0; i < testString.length; i++) {
            for(int j = 0; j < testString[0].length(); j++) {
                map[j][i] = testString[i].charAt(j);
            }
        }
        map[11][13] = 'X';
        int height = testString.length;
        int width = testString[0].length();
        ArrayList<int[]> asteroidCoordinates = getAsteroidCoordinates(map, testString[0].length(), testString.length);
        ArrayList<int[]> anglesList = getNonStraightAngles(map, asteroidCoordinates, x, y);
        //for(int[] i: asteroidCoordinates) System.out.println(i[0] + ", " + i[1]);
        //for(int[] i: anglesList) System.out.println(i[0] + ", " + i[1]);
        /*while(vaporizedCounter < 200) {
            rotateLaser(map, anglesList, x, y, height, width);
        }*/
        /*printMap(map, width, height);
        rotateLaser(map, anglesList, x, y);
        System.out.println();
        System.out.println(lastVaporized[0] + ", " + lastVaporized[1]);
        printMap(map, width, height);*/
    }

    static void printMap(char[][] map, int width, int height) {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                System.out.print(map[j][i]);
            }
            System.out.println();
        }
    }

    static void rotateLaser(char[][] map, ArrayList<int[]> angles, int x, int y, int height, int width) {
        int[] laserPointingCoordinate = {x, 0};
        ArrayList<int[]> anglesInOrder = new ArrayList<>();
        int numberOfPoints = 2*width + 2*height;
        for(int i = 0; i < angles.size(); i++) {
            if(vaporizedCounter == 200) {
                printMap(map, height, width);
                System.out.println("Rotation index after 99: " + i);
                break;
            }
            int index = 1;
            while(map[x + angles.get(i)[0]*index][y + angles.get(i)[1]*index] != '#') {
                index++;
            }
            vaporizedCounter++;
            lastVaporized[0] = x + angles.get(i)[0]*index;
            lastVaporized[1] = y + angles.get(i)[1]*index;
            map[x + angles.get(i)[0]*index][y + angles.get(i)[1]*index] = '.';
        }
    }

    static ArrayList<int[]> getNonStraightAngles(char[][] map, ArrayList<int[]> asteroidCoordinates, int x, int y) {
        ArrayList<int[]> angles = new ArrayList<>();
        int xOne = x;
        int yOne = y;
        for(int i = 0; i < asteroidCoordinates.size(); i++) {
            int xTwo = asteroidCoordinates.get(i)[0];
            int yTwo = asteroidCoordinates.get(i)[1];
            int angleX = xTwo - xOne;
            int angleY = yTwo - yOne;
            int gcd = gcd(Math.abs(angleX), Math.abs(angleY));
            int[] angle = new int[2];
            if(gcd != 0) {
                angle[0] = angleX / gcd;
                angle[1] = angleY / gcd;
            } else angle = new int[]{0, 0};
            boolean isFound = false;
            for(int j = 0; j < angles.size(); j++) {
                if(Arrays.equals(angles.get(j), angle)) {
                    isFound = true;
                    break;
                }
            }
            if(!isFound) angles.add(angle);
        }
        return angles;
    }

    static ArrayList<int[]> getAsteroidCoordinates(char[][] map, int width, int height) {
        ArrayList<int[]> asteroidCoordinates = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(map[j][i] == '#') asteroidCoordinates.add(new int[]{j, i});
            }
        }
        return asteroidCoordinates;
    }

    //greatest common divider
    static int gcd(int a, int b) {
        if(b  == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        ArrayList<String> inputList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_10.txt"));
            String line = reader.readLine();
            while(line != null) {
                inputList.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        //String[] testArr = test.split("\n");
        char[][] spaceMatrix = new char[inputList.size()][inputList.get(0).length()];
        int height = inputList.size(); int width = inputList.get(0).length();
        for(int i = 0; i < inputList.size(); i++) {
            for(int j = 0; j < inputList.get(0).length(); j++) {
                spaceMatrix[j][i] = inputList.get(i).charAt(j);
            }
        }
        for(String s: inputList) System.out.println(s);
        solve1(spaceMatrix, width, height);
        solve2(11, 13);
    }
}
