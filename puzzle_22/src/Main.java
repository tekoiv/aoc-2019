import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private final int DECK_SIZE = 10007;
    private List<Integer> cards;

    public static void main(String[] args) {
        Main main = new Main();
        main.solve1();
    }

    private Main() {
        initCards();
    }

    private void solve1() {
        List<String> input = parseInput();
        for(String s: input) {
            if(s.contains("deal into new stack"))
                dealIntoNewStack();
            else if(s.contains("deal with increment"))
                dealWithIncrementN(Integer.parseInt(s.substring(s.indexOf("t ") + 2)));
            else if(s.contains("cut"))
                cutCards(Integer.parseInt(s.substring(s.indexOf("t ") + 2)));
            else System.out.println("Error");
        }
        System.out.println("22 a): " + cards.indexOf(2019));
    }

    private List<String> parseInput() {
        List<String> input = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../inputs/input_22.txt"));
            line = reader.readLine();
            while(line != null) {
                input.add(line);
                line = reader.readLine();
            }
        } catch(IOException e) { e.printStackTrace(); }
        return input;
    }

    private void cutCards(int N) {
        List<Integer> head;
        List<Integer> tail;
        if(N > 0) {
            head = cards.subList(0, N);
            tail = cards.subList(N, cards.size());
        } else {
            tail = cards.subList(cards.size() - Math.abs(N), cards.size());
            head = cards.subList(0, cards.size() - Math.abs(N));
        }
        cards = Stream.of(tail, head)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private void dealWithIncrementN(int N) {
        //use a map to prevent iteration over two arrayLists
        Map<Integer, Integer> newDeck = new HashMap<>();
        int cardIndex = 0;
        for(int i = 0;;i += N) {
            if(newDeck.size() == cards.size()) break;
            newDeck.put(i % cards.size(), cards.get(cardIndex));
            cardIndex++;
        }
        cards.clear();
        for(int i = 0; i < newDeck.size(); i++) {
            cards.add(newDeck.get(i));
        }
    }

    private void dealIntoNewStack() {
        Collections.reverse(cards);
    }

    private void initCards() {
        cards = new ArrayList<>();
        for(int i = 0; i < DECK_SIZE; i++) {
            cards.add(i);
        }
    }
}
