import javax.swing.*;
import java.util.Random;

class Field extends JPanel {
    static final int MAX_WIDTH = 45;
    static final int MAX_HEIGHT = 45;
    static final int MIN_WIDTH = 4;
    static final int MIN_HEIGHT = 4;
    static final int MAX_PERCENT = 99;
    static final int MIN_PERCENT = 1;

    private final Tile[][] field;
    private final int width;
    private final int height;
    private final int catsCount;

    private boolean firstClick = false;
    private boolean gameOver = false;

    Field(int width, int height, int catsPercentage) {
        super();

        this.width = width;
        this.height = height;

        catsCount = (width * height * catsPercentage) / 100;
        field = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Tile tile = new Tile();
                add(tile);
                field[i][j] = tile;

                final int iID = i;
                final int jID = j;
                field[i][j].addActionListener(actionEvent -> onTileClicked(iID, jID));
                // TODO right click listener for flag and question mark
            }
        }
    }

    private void onTileClicked(int clickedI, int clickedJ) {
        if (gameOver) {
            return;
        }

        final Tile tile = field[clickedI][clickedJ];

        if (!firstClick) {
            firstClick = true;

            generateCatField(clickedI, clickedJ);
            updateNumbers();
        }

        tile.reveal();

        if (tile.getTileType() == TileType.Cat) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, "Game Over");
            return;
        }

        switch (tile.getTileType()) {
            case None -> cascade(clickedI, clickedJ);
            case Cat -> {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Game Over");
            }
            default -> {
            }
        }
    }

    private void generateCatField(int clickedI, int clickedJ) {
        final Random rand = new Random();

        int cats = catsCount;

        while (cats != 0) {
            final int x = rand.nextInt(width);
            final int y = rand.nextInt(height);

            final Tile tile = field[x][y];

            if (tile.getTileType() == TileType.None) {
                if (!isAroundFirstClick(clickedI, clickedJ, x, y)) {
                    tile.setTileType(TileType.Cat);
                    cats--;
                }
            }
        }
    }

    private void updateNumbers() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final Tile tile = field[i][j];

                assert tile.getTileType() == TileType.Cat || tile.getTileType() == TileType.None;

                if (tile.getTileType() == TileType.None) {
                    final int cats = getTileNumber(i, j);

                    if (cats > 0) {
                        tile.setTileType(TileType.Number);
                        tile.setNumber(cats);
                    }
                }
            }
        }
    }

    private int getTileNumber(int clickedI, int clickedJ) {
        int cats = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final int x = clickedI + i;
                final int y = clickedJ + j;

                if (x >= 0 && x < width && y >= 0 && y < height) {
                    if (field[x][y].getTileType() == TileType.Cat) {
                        cats++;
                    }
                }
            }
        }

        return cats;
    }

    private void cascade(int clickedI, int clickedJ) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final int x = clickedI + i;
                final int y = clickedJ + j;

                if (x >= 0 && x < width && y > 0 && y < height) {
                    final Tile tile = field[x][y];

                    if (!tile.isRevealed()) {
                        switch (tile.getTileType()) {
                            case None -> {
                                tile.reveal();
                                cascade(x, y);
                            }
                            case Number -> tile.reveal();
                            default -> {}
                        }
                    }
                }
            }
        }
    }

    private boolean isAroundFirstClick(int clickedI, int clickedJ, int tileX, int tileY) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final int x = clickedI + i;
                final int y = clickedJ + j;

                if (x == tileX && y == tileY) {
                    return true;
                }
            }
        }

        return false;
    }
}
