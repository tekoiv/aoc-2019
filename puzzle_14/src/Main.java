import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static int oreRequired = 0;
    static Map<String, Integer> requiredIngredients = new HashMap<>();

    static int getOreRequired(Map<List<String>, String> recipes, List<String> key) {
        //first is 2AB
        key.forEach(ingredient -> {
            List<String> nextKey = new ArrayList<>(getKeyByValue(recipes, ingredient)).get(0);
            int requiredAmount = getAmount(ingredient);
            //int providedAmount = getAmount(recipes.get(nextKey));
            String currentIngredient = ingredient.substring(ingredient.indexOf(" ") + 1);

            if(recipes.get(key).contains("FUEL")) {
                requiredIngredients.put(currentIngredient, requiredAmount);
                getOreRequired(recipes, nextKey);
            } else {
                String product = recipes.get(key).substring(recipes.get(key).indexOf(" ") + 1);
                String ing = recipes.get(key);
                if(requiredIngredients.containsKey(currentIngredient)){
                    int originalValue = requiredIngredients.get(currentIngredient);
                    requiredIngredients.put(currentIngredient, originalValue + (requiredIngredients.get(product)/getAmount(product))*getAmount(currentIngredient));
                } else {
                    requiredIngredients.put(currentIngredient, requiredIngredients.get(product)/getAmount(ing)*getAmount(ingredient));
                }
            }
            if(nextKey.get(0).substring(nextKey.get(0).indexOf(" ") + 1).equals("ORE")) {
                 String product = recipes.get(key).substring(recipes.get(0).indexOf(" ") + 1);
                 int provided = getAmount(recipes.get(key));
                 oreRequired += requiredIngredients.get(currentIngredient)/provided
            } else getOreRequired(recipes, nextKey);
        });
        /*key.forEach(ingredient -> {
            List<String> nextKey = new ArrayList<>(getKeyByValue(recipes, ingredient)).get(0);
            int requiredAmount = getAmount(ingredient);
            int providedAmount = getAmount(recipes.get(nextKey));
            List<String> nextMultiplied = new ArrayList<>(multiplyIngredients(nextKey, requiredAmount, providedAmount));
            String ingredientID = ingredient.substring(ingredient.indexOf(" ") + 1);
            if(nextKey.get(0).substring(nextKey.get(0).indexOf(" ") + 1).equals("ORE")) {
                int oreAmount = getAmount(nextMultiplied.get(0));
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
        });*/

        return oreRequired;
    }

    static int getAmount(String recipe) {
        return Integer.parseInt(recipe.substring(0, recipe.indexOf(" ")));
    }

    static List<String> multiplyIngredients(List<String> ingredients, int requiredAmount, int providedAmount) {
        List<String> list = new ArrayList<>();
        ingredients.forEach(ingredient -> {
            String currentAmount = ingredient.substring(0, ingredient.indexOf(" "));
            String multipliedAmount = Integer.toString(Integer.parseInt(currentAmount)*(requiredAmount / providedAmount));
            list.add(multipliedAmount);
        });
        return list;
    }

    static <T, E> Set<T> getKeyByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().toString().substring(entry.getValue().toString().indexOf(" ") + 1).equals(value.toString().substring(value.toString().indexOf(" ") + 1)))
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
        getOreRequired(recipeMap, firstKey);
        //System.out.println(getOreRequired(recipeMap, firstKey));
    }
}
