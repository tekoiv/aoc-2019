import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<StringBuilder> input = new ArrayList<>();
        StringBuilder line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_18.txt"));
            line = new StringBuilder(reader.readLine());
            while(line != null) {
                input.add(line);
                line = new StringBuilder(reader.readLine());
            }
        } catch(IOException e) { System.out.println(e); }
    }
}
