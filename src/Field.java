import javax.swing.*;

class Field extends JPanel {
    private final Tile[][] field;
    private final int width;
    private final int height;

    Field(int width, int height) {
        super();

        this.width = width;
        this.height = height;

        field = new Tile[width][height];
    }

    void setTile(int i, int j, Tile tile) {
        field[i][j] = tile;
    }
}
