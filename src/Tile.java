import javax.swing.*;

class Tile extends JButton {
    private TileType type;

    Tile(TileType type) {
        super("S");

        this.type = type;
    }
}
