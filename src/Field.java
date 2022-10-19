import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

class Field extends JPanel implements MouseListener {
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
    private final Player player;

    private boolean firstClick = false;
    private boolean gameOver = false;

    Field(int width, int height, int catsPercentage, Player player) {
        super();

        this.width = width;
        this.height = height;
        this.player = player;

        catsCount = (width * height * catsPercentage) / 100;
        field = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final Tile tile = new Tile();
                add(tile);
                field[i][j] = tile;

                final int iID = i;
                final int jID = j;
                tile.addActionListener(actionEvent -> onTileClicked(iID, jID));
                tile.addMouseListener(this);
            }
        }
    }

    private void onTileClicked(int clickedI, int clickedJ) {
        if (gameOver) {
            return;
        }

        final Tile tile = field[clickedI][clickedJ];

        if (tile.getTileIcon() == TileIcon.Flag) {
            return;
        }

        if (!firstClick) {
            firstClick = true;

            generateCatField(clickedI, clickedJ);
            updateNumbers();
        }

        tile.reveal();

        switch (tile.getTileType()) {
            case None -> {
                cascade(clickedI, clickedJ);

                player.setPlayerIcon(PlayerIcon.Happy);
                setIconNormalAfter(1300);
            }
            case Cat -> {
                gameOver = true;

                player.setPlayerIcon(PlayerIcon.Embarrassed);
                revealAllCats();
                JOptionPane.showMessageDialog(this, "Game Over!");
            }
            default -> {}
        }

        if (hasWon()) {
            gameOver = true;
            player.setPlayerIcon(PlayerIcon.Happy);

            JOptionPane.showMessageDialog(this, "You Have Won!");
            String name = JOptionPane.showInputDialog(this, "Type your name", "HighScore", JOptionPane.PLAIN_MESSAGE);
            Application.highScore.insert(new Score(Application.highScore.lastId + 1, name, 1272));
        }

        Application.highScore.insert(new Score(Application.highScore.lastId + 1, "Simon", 1272));
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

                if (x >= 0 && x < width && y >= 0 && y < height) {
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

    private void revealAllCats() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final Tile tile = field[i][j];

                if (tile.getTileType() == TileType.Cat && !tile.isRevealed()) {
                    tile.reveal();
                }
            }
        }
    }

    private boolean hasWon() {  // FIXME this seems dodgy
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final Tile tile = field[i][j];

                if (tile.getTileType() != TileType.Cat) {
                    if (!tile.isRevealed()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void setIconNormalAfter(int milliseconds) {
        Timer timer = new Timer(milliseconds, actionEvent -> {
            if (player.getPlayerIcon() == PlayerIcon.Happy) {
                player.setPlayerIcon(PlayerIcon.Normal);
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (!SwingUtilities.isRightMouseButton(mouseEvent)) {
            return;
        }

        if (gameOver) {
            return;
        }

        if (!firstClick) {
            return;
        }

        final Tile tile = (Tile) mouseEvent.getComponent();

        if (tile.isRevealed()) {
            return;
        }

        switch (tile.getTileIcon()) {
            case None, Cat -> tile.setTileIcon(TileIcon.Flag);
            case Flag -> tile.setTileIcon(TileIcon.QuestionMark);
            case QuestionMark -> tile.setTileIcon(TileIcon.None);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
