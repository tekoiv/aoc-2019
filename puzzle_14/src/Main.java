import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static int oreRequired = 0;

    static int getOreRequired(Map<List<String>, String> recipes, List<String> key) {

        return oreRequired;
    }

    static <T, E> Set<T> getKeyByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        List<String> recipes = Arrays.asList(("10 ORE => 10 A\n" +
                "1 ORE => 1 B\n" +
                "7 A, 1 B => 1 C\n" +
                "7 A, 1 C => 1 D\n" +
                "7 A, 1 D => 1 E\n" +
                "7 A, 1 E => 1 FUEL").split("\n"));
        recipes.forEach(recipe -> System.out.println(recipe));
        Map<List<String>, String> recipeMap = new HashMap<>();
        recipes.forEach(recipe -> {
            List<String> key = Arrays.asList((recipe.substring(0, recipe.indexOf("="))).split(", "));
            key.set(key.size() - 1, key.get(key.size() - 1).substring(0, key.get(key.size() - 1).length() - 1));
            String value = recipe.substring(recipe.indexOf(">") + 2);
            recipeMap.put(key, value);
        });
        recipeMap.entrySet().forEach(entry -> {
            System.out.print("Key:");
            entry.getKey().forEach(str -> System.out.print(str + " "));
            System.out.println("Value:" + entry.getValue());
        });
        List<String> firstKey = new ArrayList<>(getKeyByValue(recipeMap, "1 FUEL")).get(0);
        System.out.println(firstKey);
    }
}
