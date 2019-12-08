import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    //public static final int TEST_HEIGHT = 2;
    //public static final int TEST_WIDTH = 3;
    //public static StringBuilder testData = new StringBuilder("123456789012");

    public static final int HEIGHT = 6;
    public static final int WIDTH = 25;
    public static StringBuilder data;

    static void solve1() {
        StringBuilder[] layers = new StringBuilder[data.length() / (HEIGHT * WIDTH)];
        int arrayIndex = 0;
        while(data.length() > 0) {
            layers[arrayIndex] = new StringBuilder(data.substring(0, HEIGHT * WIDTH));
            data.delete(0, HEIGHT * WIDTH);
            arrayIndex++;
        }
        //layer with fewest 0's
        long fewestZeroes = Integer.MAX_VALUE;
        int count;
        int layerWithFewestZeroesIndex = 0;
        for(int i = 0; i < layers.length; i++) {
            count = 0;
            for(int j = 0; j < HEIGHT * WIDTH; j++) {
                if(layers[i].charAt(j) == '0') {
                    count++;
                }
            }
            if(count < fewestZeroes) {
                layerWithFewestZeroesIndex = i;
                fewestZeroes = count;
            }
        }

        //1's and 2's
        int ones = 0;
        int twos = 0;
        for(int i = 0; i < HEIGHT * WIDTH; i++) {
            if(layers[layerWithFewestZeroesIndex].charAt(i) == '1') ones++;
            else if(layers[layerWithFewestZeroesIndex].charAt(i) == '2') twos++;
        }
        System.out.println(ones * twos);
    }

    static void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_8.txt"));
            String line = reader.readLine();
            String input = "";
            while(line != null) {
                input += line;
                line = reader.readLine();
            }
            data = new StringBuilder(input);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        readFile();
        solve1();
    }
}
