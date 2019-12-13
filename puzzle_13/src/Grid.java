import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {
    char[][] screen;
    private final int boxWidth = 15;
    private final int boxHeight = 15;
    public Grid(char[][] screen) {
        this.screen = screen;
    }

    public void paintComponent(Graphics g) {

        for(int i = 0; i < 23; i++) {
            for(int j = 0; j < 42; j++) {
                if(screen[j][i] == 'B') g.setColor(Color.WHITE);
                else if(screen[j][i] == 'W') g.setColor(Color.BLUE);
                else if(screen[j][i] == 'P') g.setColor(Color.RED);
                else if(screen[j][i] == 'H') g.setColor(Color.YELLOW);
                else if(screen[j][i] == 'E') g.setColor(Color.BLACK);
                g.drawRect(j * boxWidth, i * boxHeight, boxWidth, boxHeight);
                g.fillRect(j*boxWidth, i*boxHeight, boxWidth, boxHeight);
            }
        }
    }
}
