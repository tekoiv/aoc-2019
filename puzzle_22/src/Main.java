import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private final int DECK_SIZE = 10;
    private List<Integer> cards;

    public static void main(String[] args) {
        Main main = new Main();
        main.cutCards(-4);
        main.printStack();
    }

    private Main() {
        initCards();
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

    private void dealIntoNewStack() {
        Collections.reverse(cards);
    }

    private void initCards() {
        cards = new ArrayList<>();
        for(int i = 0; i < DECK_SIZE; i++) {
            cards.add(i);
        }
    }

    private void printStack() { System.out.println(cards); }

}
