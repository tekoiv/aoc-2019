import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Key> keys = new ArrayList<>();
    static List<Door> doors = new ArrayList<>();
    static Point startingPoint;

    static void getKeysAndDoors(List<StringBuilder> input) {
        int lengthX = input.get(0).length();
        int lengthY = input.size();
        for(int i = 0; i < lengthY; i++) {
            for(int j = 0; j < lengthX; j++) {
                if(input.get(i).charAt(j) != '#' || input.get(i).charAt(j) != '.') {
                    if(input.get(i).charAt(j) == '@') startingPoint = new Point(j, i);
                    else if(Character.isLowerCase(input.get(i).charAt(j))) keys.add(new Key(j, i, input.get(i).charAt(j)));
                    else if(Character.isUpperCase(input.get(i).charAt(j))) doors.add(new Door(j, i, input.get(i).charAt(j)));
                }
            }
        }
    }

    public static void main(String[] args) {
        List<StringBuilder> input = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_18.txt"));
            line = reader.readLine();
            while(line != null) {
                input.add(new StringBuilder(line));
                line = reader.readLine();
            }
        } catch(IOException e) { System.out.println(e); }
        getKeysAndDoors(input);
        System.out.println("Keys: ");
        keys.forEach(key -> System.out.println(key.getX() + "," + key.getY() + "," + key.getValue()));
        System.out.println("Doors: ");
        doors.forEach(door -> System.out.println(door.getX() + "," + door.getY() + "," + door.getValue()));
        System.out.println("Starting point: " + startingPoint.getX() + "," + startingPoint.getY());
    }
}
