import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    static List<Integer> basePattern = new ArrayList<>(List.of(0, 1, 0, -1));
    static int recursionIndex = 0;
    static int phaseAmount = 100;

    static List<Integer> getPattern(int index, int size) {
        if(index == 0) return expandToProperSize(size, List.of(1, 0, -1, 0));
        List<Integer> newPattern = new ArrayList<>();
        basePattern.forEach(number -> {
            for(int i = 0; i < index + 1; i++) {
                newPattern.add(number);
            }
        });
        newPattern.remove(0);
        newPattern.add(0);
        if(newPattern.size() > size) return newPattern.subList(0, size);
        else return expandToProperSize(size, newPattern);
    }

    static List<Integer> expandToProperSize(int size, List<Integer> pattern) {
        int index = 0;
        List<Integer> properSizeList = new ArrayList<>();
        while(index < size) {
            properSizeList.add(pattern.get(index % pattern.size()));
            index++;
        }
        return properSizeList;
    }

    static int getOnesPart(int x) {
        return Integer.parseInt(Integer.toString(x).substring(Integer.toString(x).length() - 1));
    }

    static List<Integer> solvePartOne(List<Integer> input) {
        if(recursionIndex >= phaseAmount) return input;
        List<Integer> solution = new ArrayList<>();
        for(int i = 0; i < input.size(); i++) {
            int counter = 0;
            List<Integer> pattern = getPattern(i, input.size());
            for(int j = 0; j < input.size(); j++) {
                counter += input.get(j)*pattern.get(j);
            }
            solution.add(getOnesPart(counter));
        }
        recursionIndex++;
        return solvePartOne(solution);
    }

    static List<Integer> solvePartTwo(String inputString, List<Integer> solution) {
        int offset = Integer.parseInt(inputString.substring(0, 7));
        System.out.println(offset);
        return solution.subList(offset, offset + 8);
    }

    public static void main(String[] args) {
        String line = "";
        String inputString = "03036732577212944063491565474664";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_16.txt"));
            line = reader.readLine();
        } catch(IOException e) { System.out.println(e); }
        List<Integer> input = Arrays.asList("03036732577212944063491565474664".split(""))
                .stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> repeatedList = new ArrayList<>();
        for(int i = 0; i < 10000; i++) repeatedList.addAll(input);
        System.out.println(solvePartOne(input));
        recursionIndex = 0;
        //System.out.println(solvePartTwo(inputString, solvePartOne(repeatedList)));
    }
}
