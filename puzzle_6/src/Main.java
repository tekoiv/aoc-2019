import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {

    public static Map<String, HashSet<String>> map;
    public static Map<String, Integer> santaMap;
    public static Map<String, Integer> youMap;

    public static long calculateCount(String s) {
        if(!map.containsKey(s)) return 0;

        int count = 0;
        for(String str : map.get(s)) {
            count += 1 + calculateCount(str);
        }
        return count;
    }

    public static void getToSanta(String key, int path, boolean santaIsHere) {
        if(santaIsHere) {
            if(!santaMap.containsKey(key)) {
                santaMap.put(key, path);
                if(map.containsKey(key)) {
                    for(String str : map.get(key)) {
                        getToSanta(str, path + 1, true);
                    }
                }
            }
        } else {
            if(!youMap.containsKey(key)) {
                youMap.put(key, path);
                if(map.containsKey(key)) {
                    for(String str : map.get(key)) {
                        getToSanta(str, path + 1, false);
                    }
                }
            }
        }
    }

    public static String[] readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_6.txt"));
            ArrayList<String> stringList = new ArrayList<>();
            String line = reader.readLine();
            while(line != null) {
                stringList.add(line);
                line = reader.readLine();
            }
            String[] stringArray = new String[stringList.size()];
            for(int i = 0; i < stringArray.length; i++) {
                stringArray[i] = stringList.get(i);
            }
            return stringArray;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        String[] puzzleInput = readFile();
        map = new HashMap<String, HashSet<String>>();
        for(int i = 0; i < puzzleInput.length; i++) {
            String[] input = puzzleInput[i].split("\\)");
            if(!map.containsKey(input[1])) {
                map.put(input[1], new HashSet<>());
            }

            map.get(input[1]).add(input[0]);
        }
        long total = 0;

        for (String s : map.keySet()) {
            total += calculateCount(s);
        }
        //a) answer
        System.out.println(total);

        santaMap = new HashMap<String, Integer>();
        youMap = new HashMap<String, Integer>();

        getToSanta("YOU", 0, false);
        getToSanta("SAN", 0, true);

        long answer = Integer.MAX_VALUE;

        for(String visitedPlanet : santaMap.keySet()) {
            if(youMap.containsKey(visitedPlanet)) {
                answer = Math.min(santaMap.get(visitedPlanet) + youMap.get(visitedPlanet), answer);
            }
        }
        //b) answer

        System.out.println(answer - 2);
    }
}