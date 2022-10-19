import javax.swing.*;
import java.awt.*;

public class HighScore extends JFrame {
    HighScore() {
        super("HighScore");

        setMinimumSize(new Dimension(320, 240));
        setIconImage(Tile.catIcon.getImage());

        pack();
        setVisible(true);
    }
}
