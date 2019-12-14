import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static int oreRequired = 0;
    static Map<String, Integer> ingredientStorage = new HashMap<>();

    static int getOreRequired(Map<List<String>, String> recipes, List<String> key) {
        key.forEach(ingredient -> {
            List<String> nextKey = new ArrayList<>(getKeyByValue(recipes, ingredient)).get(0);
            int requiredAmount = getAmount(ingredient);
            int providedAmount = getAmount(recipes.get(nextKey));
            nextKey = multiplyIngredients(nextKey, requiredAmount, providedAmount);
            String ingredientID = ingredient.substring(ingredient.indexOf(" ") + 1);
            if(nextKey.get(0).substring(nextKey.get(0).indexOf(" ") + 1).equals("ORE")) {
                int oreAmount = getAmount(nextKey.get(0));
                //is in map
                if(ingredientStorage.containsKey(ingredientID)) {
                    if(ingredientStorage.get(ingredientID) < requiredAmount) {
                        if(requiredAmount / providedAmount == 1) {
                            oreRequired += oreAmount;
                        }
                        else {
                            oreRequired += oreAmount;
                            int originalValue = ingredientStorage.get(ingredientID);
                            int leftoverMaterial = providedAmount % requiredAmount;
                            ingredientStorage.put(ingredientID, originalValue + leftoverMaterial);
                        }
                    } else {
                        int originalValue = ingredientStorage.get(ingredientID);
                        ingredientStorage.put(ingredientID, originalValue - requiredAmount);
                    }
                }
                else {
                    if(requiredAmount / providedAmount == 1) {
                        oreRequired += oreAmount;
                    }
                    else oreRequired += oreAmount;
                    int leftoverMaterial = requiredAmount % providedAmount;
                    ingredientStorage.put(ingredientID, leftoverMaterial);
                }
            }
            else {
                getOreRequired(recipes, nextKey);
            }
        });
        return oreRequired;
    }

    static int getAmount(String recipe) {
        return Integer.parseInt(recipe.substring(0, recipe.indexOf(" ")));
    }

    static List<String> multiplyIngredients(List<String> ingredients, int requiredAmount, int providedAmount) {
        ingredients.forEach(ingredient -> {
            String currentAmount = ingredient.substring(0, ingredient.indexOf(" "));
            String multipliedAmount = Integer.toString(Integer.parseInt(currentAmount)*(requiredAmount / providedAmount));
            ingredients.set(ingredients.indexOf(ingredient), ingredient.replace(currentAmount, multipliedAmount));
        });
        return ingredients;
    }

    static <T, E> Set<T> getKeyByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().toString().substring(entry.getValue().toString().indexOf(" ") + 1).equals(value.toString().substring(value.toString().indexOf(" ") + 1)))
                //.filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
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
        System.out.println(firstKey);
        System.out.println(getOreRequired(recipeMap, firstKey));
        //System.out.println(getOreRequired(recipeMap, firstKey));
    }
}
