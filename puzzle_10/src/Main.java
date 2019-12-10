import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static void getAngles(char[][] input, int width, int height) {
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
            straightAnglesList.clear();;
            count = 0;
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

    //greatest common divider
    static int gcd(int a, int b) {
        if(b  == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        /*String test = ".#..#\n" +
                ".....\n" +
                "#####\n" +
                "....#\n" +
                "...##";*/
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
        getAngles(spaceMatrix, width, height);
    }

}
