import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static int oreCounter = 0;
    static Map<String, Integer> requiredIngredients = new HashMap<>();
    static Map<String, Integer> store = new HashMap<>();

    static <T, E> Set<T> getKeyByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().toString().substring(entry.getValue().toString().indexOf(" ") + 1).equals(value.toString().substring(value.toString().indexOf(" ") + 1)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    static int makeMineral(Map<List<String>, String> recipeMap, String mineral, int qty) {
        List<String> nextKey = new ArrayList<>(getKeyByValue(recipeMap, mineral)).get(0);
        if(nextKey.get(0).substring(nextKey.get(0).indexOf(" ") + 1).equals("ORE")) {
            int required = qty;
            if(store.containsKey(mineral)) {
                if(store.get(mineral) >= required) {
                    int originalValue = store.get(mineral);
                    required -= originalValue;
                    store.put(mineral, originalValue - required);
                } else {
                    int originalValue = store.get(mineral);
                    required = qty - originalValue;
                    store.put(mineral, 0);
                }
            }
            else {
                required = qty;
            }
            int provided = getAmount(recipeMap.get(nextKey));
            oreCounter += (int) Math.ceil(required / 1.0 / provided) * getAmount(nextKey.get(0));
            int manufacturedMinerals = (int) Math.ceil(required / 1.0 / provided) * getAmount(recipeMap.get(nextKey));
            int leftoverMinerals = manufacturedMinerals - required;
            if(store.containsKey(mineral)) {
                int originalValue = store.get(mineral);
                store.put(mineral, originalValue + leftoverMinerals);
            } else {
                store.put(mineral, leftoverMinerals);
            }
        }
        else {
            nextKey.forEach(ingredient -> {
                int required = (int) Math.ceil(qty / 1.0 / getAmount(recipeMap.get(nextKey))) * getAmount(ingredient);
                if(store.containsKey(ingredient)) {
                    if(store.get(ingredient) >= required) {
                        int originalValue = store.get(ingredient);
                        store.put(ingredient, originalValue - required);
                    }
                    else {
                        int originalValue = store.get(ingredient);
                        store.put(ingredient, 0);
                        makeMineral(recipeMap, ingredient, required - originalValue);
                    }
                } else {
                    makeMineral(recipeMap, ingredient.substring(ingredient.indexOf(" ") + 1), required);
                }
            });
        }
        return oreCounter;
    }

    static int getAmount(String s) {
        return Integer.parseInt(s.substring(0, s.indexOf(" ")));
    }

    public static void main(String[] args) {
        List<String> recipes = Arrays.asList(("9 ORE => 2 A\n" +
                "8 ORE => 3 B\n" +
                "7 ORE => 5 C\n" +
                "3 A, 4 B => 1 AB\n" +
                "5 B, 7 C => 1 BC\n" +
                "4 C, 1 A => 1 CA\n" +
                "2 AB, 3 BC, 4 CA => 1 FUEL").split("\n"));
        recipes.forEach(recipe -> System.out.println(recipe));
        Map<List<String>, String> recipeMap = new HashMap<>();
        recipes.forEach(recipe -> {
            List<String> key = Arrays.asList((recipe.substring(0, recipe.indexOf("="))).split(", "));
            key.set(key.size() - 1, key.get(key.size() - 1).substring(0, key.get(key.size() - 1).length() - 1));
            String value = recipe.substring(recipe.indexOf(">") + 2);
            recipeMap.put(key, value);
        });
        List<String> firstKey = new ArrayList<>(getKeyByValue(recipeMap, "1 FUEL")).get(0);
        firstKey.forEach(ingredient -> {
            makeMineral(recipeMap, ingredient, getAmount(ingredient));
            for(String s: store.keySet()) System.out.println("Mineral: " + s + " quantity: " + store.get(s));
        });
        System.out.println("Total ore: " + oreCounter);
    }
}
