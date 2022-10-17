import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application extends JFrame {
    Tile[][] field;
    JPanel pnlField;

    Application() {
        super("Catsweeper");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        var pane = getContentPane();
        pane.setLayout(new GridBagLayout());

        setupMenuBar();
        setupGameMenu();

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
        var btnTenByTen = new JButton("10x10 field");
        var btnFifteenByFifteen = new JButton("15x15 field");
        var btnCustom = new JButton("Custom field");

        var lytButtons = new GridBagLayout();

    }

    private void newGame() {
        if (pnlField != null) {
            remove(pnlField);
        }

        pnlField = new JPanel();

        var lytGrid = new GridLayout(10, 10, 2, 2);
        pnlField.setLayout(lytGrid);

        field = new Tile[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Tile tile = new Tile(TileType.Empty);
                pnlField.add(tile);
                field[i][j] = tile;
            }
        }

        getContentPane().add(pnlField);
        pack();
    }

    private void loadGame() {
        System.out.println("load game");
    }

    private void about() {
        System.out.println("about");
    }
}
