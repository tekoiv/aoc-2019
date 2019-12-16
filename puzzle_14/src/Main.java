import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    /*
    This took way longer than I'd like to admit. I like the solution though.
    Part b could be way faster with some numeric search algorithms
    but this solution is quite speedy as well.
     */

    static BigInteger oreCounter = new BigInteger("0");
    static Map<String, Long> store = new HashMap<>();
    static final BigInteger MAX_ORE = new BigInteger("1000000000000");
    static  long maxFuel = 0;

    static <T, E> Set<T> getKeyByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().toString().substring(entry.getValue().toString().indexOf(" ") + 1).equals(value.toString().substring(value.toString().indexOf(" ") + 1)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    static BigInteger makeMineral(Map<List<String>, String> recipeMap, String mineral, long qty) {
        List<String> nextKey = new ArrayList<>(getKeyByValue(recipeMap, mineral)).get(0);
        long required = qty;
        if (store.containsKey(mineral)) {
            long originalValue = store.get(mineral);
            if (originalValue >= required) {
                store.put(mineral, originalValue - required);
                return oreCounter;
            } else {
                required -= originalValue;
                store.put(mineral, 0L);
            }
        }

        long provided = getAmount(recipeMap.get(nextKey));
        long manufacturedMinerals = (long) Math.ceil(required / 1.0 / provided) * getAmount(recipeMap.get(nextKey));
        long leftoverMinerals = manufacturedMinerals - required;

        if (store.containsKey(mineral)) {
            long originalValue = store.get(mineral);
            store.put(mineral, originalValue + leftoverMinerals);
        } else {
            store.put(mineral, leftoverMinerals);
        }

        if (nextKey.get(0).substring(nextKey.get(0).indexOf(" ") + 1).equals("ORE")) {
            oreCounter = oreCounter.add(new BigInteger(Long.toString((long) Math.ceil(required / 1.0 / provided) * getAmount(nextKey.get(0)))));
        } else {
            long finalRequired = required;
            nextKey.forEach(ingredient -> {
                makeMineral(recipeMap, ingredient.substring(ingredient.indexOf(" ") + 1), getAmount(ingredient) * (long) Math.ceil(finalRequired / 1.0 / provided));
            });
        }
        return oreCounter;
    }

    static long getAmount(String s) {
        return Long.parseLong(s.substring(0, s.indexOf(" ")));
    }

    static long maxFuel(Map<List<String>, String> recipeMap) {
        List<String> firstKey = new ArrayList<>(getKeyByValue(recipeMap, "1 FUEL")).get(0);
        long fuelAmount = 2;
        //first find fuel with accuracy of 1000
        while(true) {
            oreCounter = new BigInteger("0");
            final long finalFuelAmount = fuelAmount;
            firstKey.forEach(ingredient -> makeMineral(recipeMap, ingredient.substring(ingredient.indexOf(" ") + 1), getAmount(ingredient) * finalFuelAmount));
            if(oreCounter.compareTo(MAX_ORE) == -1) {
                maxFuel = fuelAmount;
                fuelAmount += 1000;
            }
            else break;
        }
        //then find exact
        fuelAmount -= 1000;
        while(true) {
            oreCounter = new BigInteger("0");
            final long finalFuelAmount = fuelAmount;
            firstKey.forEach(ingredient -> makeMineral(recipeMap, ingredient.substring(ingredient.indexOf(" ") + 1), getAmount(ingredient) * finalFuelAmount));
            if(oreCounter.compareTo(MAX_ORE) == -1) {
                maxFuel = fuelAmount;
                fuelAmount++;
            }
            else return maxFuel;
        }
    }

    public static void main(String[] args) {
        List<String> recipes = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_14.txt"));
            line = reader.readLine();
            while(line != null) {
                recipes.add(line);
                line = reader.readLine();
            }
        } catch(IOException e) {
            System.out.println(e);
        }

        Map<List<String>, String> recipeMap = new HashMap<>();
        recipes.forEach(recipe -> {
            List<String> key = Arrays.asList((recipe.substring(0, recipe.indexOf("="))).split(", "));
            key.set(key.size() - 1, key.get(key.size() - 1).substring(0, key.get(key.size() - 1).length() - 1));
            String value = recipe.substring(recipe.indexOf(">") + 2);
            recipeMap.put(key, value);
        });
        List<String> firstKey = new ArrayList<>(getKeyByValue(recipeMap, "1 FUEL")).get(0);
        firstKey.forEach(ingredient -> makeMineral(recipeMap, ingredient.substring(ingredient.indexOf(" ") + 1), getAmount(ingredient)));
        System.out.println("Total ore: " + oreCounter);
        System.out.println("Max fuel: " + maxFuel(recipeMap));
    }
}