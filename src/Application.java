import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;

class Application extends JFrame {
    private JPanel pnlMain;
    private Field field;
    private int lastGameWidth = 0;
    private int lastGameHeight = 0;

    Application() {
        super("Catsweeper");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        resetMainPanel();
        setupMenuBar();
        setupGameMenu();

        add(pnlMain);
        pack();
        setVisible(true);
    }

    private void setupMenuBar() {
        var menMainMenu = new JMenuBar();

        var menGame = new JMenu("Game");
        {
            var menNew = new JMenuItem("New");
            var menLoad = new JMenuItem("Load");
            var menExit = new JMenuItem("Exit");

            menGame.add(menNew);
            menGame.add(menLoad);
            menGame.add(menExit);

            menNew.addActionListener(actionEvent -> newGame());
            menLoad.addActionListener(actionEvent -> loadGame());
            menExit.addActionListener(actionEvent -> System.exit(0));
        }

        var menHelp = new JMenu("Help");
        {
            var menAbout = new JMenuItem("About");

            menHelp.add(menAbout);

            menAbout.addActionListener(actionEvent -> about());
        }

        menMainMenu.add(menGame);
        menMainMenu.add(menHelp);

        setJMenuBar(menMainMenu);
    }

    private void setupGameMenu() {
        var pnlButtons = new JPanel(new GridBagLayout());

        var btnTenByTen = new JButton("10x10 field");
        var btnFifteenByFifteen = new JButton("15x15 field");
        var btnCustom = new JButton("Custom field");

        btnTenByTen.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(10, 10);
        });

        btnFifteenByFifteen.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(15, 15);
        });

        btnCustom.addActionListener(actionEvent -> {

        });

        var cstButtons = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.0, CENTER, BOTH, new Insets(60, 20, 60, 20), 10, 60);

        pnlButtons.add(btnTenByTen, cstButtons);
        cstButtons.gridx = 1;
        pnlButtons.add(btnFifteenByFifteen, cstButtons);
        cstButtons.gridx = 2;
        pnlButtons.add(btnCustom, cstButtons);

        pnlMain.add(pnlButtons);
        pack();
    }

    private void newGame() {
        resetMainPanel();

        setupGame(lastGameWidth, lastGameHeight);
    }

    private void loadGame() {
        System.out.println("load game");
    }

    private void about() {
        System.out.println("about");
    }

    private void resetMainPanel() {
        if (pnlMain != null) {
            pnlMain.removeAll();
        }

        pnlMain = new JPanel(new GridLayout());
    }

    private void setupGame(int width, int height) {
        if (field != null) {
            remove(field);
        }

        field = new Field(width, height);

        var lytGrid = new GridLayout(width, height, 2, 2);
        field.setLayout(lytGrid);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Tile tile = new Tile(TileType.Empty);
                field.add(tile);
                field.setTile(i, j, tile);
            }
        }

        pnlMain.add(field);
        pack();
    }
}
