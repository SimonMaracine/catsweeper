import javax.swing.*;

public class Tile extends JButton {
    TileType type;

    Tile(TileType type) {
        super("S");

        this.type = type;
    }
}
