import java.util.Currency;

public class Main {
    public static void main(String[] args) {
        System.out.println("Puzzle 3");
        /*
        Rules:
        1. Password is between 235741-706948 (puzzle input)
        2. Six digit number
        3. Must have two adjacent digits (122345)
        4. Increase of stay the same from left to right
         */
        //it's fast to just search with loops
        int validPasswords = 0;
        for(int i = 235741; i < 706948; i++) {
            if(meetsCriteria(toString(i), 'a')) {
                System.out.println("Valid password: " + toString(i));
                validPasswords++;
            }
        }
        System.out.println("Total valid passwords with first criteria: " + validPasswords);

        //new rule: two adjacent matching digits are not part
        //of a larger group of matching digits

        validPasswords = 0;
        for(int i = 235741; i < 706948; i++) {
            if(meetsCriteria(toString(i), 'b')) {
                System.out.println("Valid password: " + toString(i));
                validPasswords++;
            }
        }
        System.out.println("Total valid passwords with second criteria: " + validPasswords);
    }

    static boolean meetsCriteria(String password, char criteria) {
        for(int i = 1; i < password.length(); i++) {
            if(password.charAt(i) < password.charAt(i - 1)) return false;
        }
        for(int i = 0; i < password.length(); i++) {
            int currentChar = password.charAt(i);
            int counter = 0;
            for(int j = 0; j < password.length(); j++) {
                if(password.charAt(j) == currentChar) {
                    counter++;
                }
                if(criteria == 'a') {
                    if(counter > 1) return true;
                }
            }
            if(counter == 2) return true;
        }
        return false;
    }

    static String toString(int a) {
        return Integer.toString(a);
    }
}
