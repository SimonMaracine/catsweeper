import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;

class Application extends JFrame {
    private JPanel pnlMain;
    private Field field;
    private int lastGameWidth = 0;
    private int lastGameHeight = 0;
    private int lastGameCatsPercentage = 0;

    Application() {
        super("Catsweeper");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(350, 300));

        resetMainPanel();
        setupMenuBar();
        setupGameMenu();

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

        btnTenByTen.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(10, 10, 12);
        });

        btnFifteenByFifteen.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(15, 15, 12);
        });

        btnCustom.addActionListener(actionEvent -> {
            resetMainPanel();
            setupCustom();
        });

        var cstButtons = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.0, CENTER, BOTH, new Insets(60, 20, 60, 20), 10, 70);
        var pnlButtons = new JPanel(new GridBagLayout());

        cstButtons.gridx = 0;
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
        setupGame(lastGameWidth, lastGameHeight, lastGameCatsPercentage);
    }

    private void loadGame() {
        System.out.println("load game");
    }

    private void about() {
        System.out.println("about");
    }

    private void resetMainPanel() {
        if (pnlMain != null) {
            remove(pnlMain);
        }

        pnlMain = new JPanel(new GridLayout());
        add(pnlMain);
    }

    private void setupGame(int width, int height, int catsPercentage) {
        assert width >= Field.MIN_WIDTH && width <= Field.MAX_WIDTH;
        assert height >= Field.MIN_HEIGHT && height <= Field.MAX_HEIGHT;
        assert catsPercentage >= Field.MIN_PERCENT && catsPercentage <= Field.MAX_PERCENT;

        // TODO top

        if (field != null) {
            remove(field);
        }

        field = new Field(width, height, catsPercentage);

        var lytGrid = new GridLayout(width, height, 2, 2);
        field.setLayout(lytGrid);

        assert pnlMain != null : "The main panel must not be null";

        pnlMain.add(field);
        pack();

        lastGameWidth = width;
        lastGameHeight = height;
        lastGameCatsPercentage = catsPercentage;
    }

    private void setupCustom() {
        var lblWidth = new JLabel("Width");
        var lblHeight = new JLabel("Height");
        var lblMinesPercentage = new JLabel("Mines Percentage");
        var sldWidth = new JSlider(Field.MIN_WIDTH, Field.MAX_WIDTH, 15);
        var sldHeight = new JSlider(Field.MIN_HEIGHT, Field.MAX_HEIGHT, 15);
        var sldMinesPercentage = new JSlider(Field.MIN_PERCENT, Field.MAX_PERCENT, 20);
        var btnStart = new JButton("Start Game");
        var btnCancel = new JButton("Cancel");

        sldWidth.setMajorTickSpacing(5);
        sldWidth.setPaintTicks(true);
        sldWidth.setSnapToTicks(true);

        sldHeight.setMajorTickSpacing(5);
        sldHeight.setPaintTicks(true);
        sldHeight.setSnapToTicks(true);

        sldMinesPercentage.setMajorTickSpacing(5);
        sldMinesPercentage.setPaintTicks(true);
        sldMinesPercentage.setSnapToTicks(true);

        btnStart.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(sldWidth.getValue(), sldHeight.getValue(), sldMinesPercentage.getValue());
        });

        btnCancel.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGameMenu();
        });

        var cstCustom = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, CENTER, BOTH, new Insets(10, 10, 10, 10), 0, 0);
        var pnlCustom = new JPanel(new GridBagLayout());

        cstCustom.gridy = 0;
        pnlCustom.add(lblWidth, cstCustom);
        cstCustom.gridy = 1;
        pnlCustom.add(lblHeight, cstCustom);
        cstCustom.gridy = 2;
        pnlCustom.add(lblMinesPercentage, cstCustom);

        cstCustom.gridx = 1;

        cstCustom.gridy = 0;
        pnlCustom.add(sldWidth, cstCustom);
        cstCustom.gridy = 1;
        pnlCustom.add(sldHeight, cstCustom);
        cstCustom.gridy = 2;
        pnlCustom.add(sldMinesPercentage, cstCustom);

        cstCustom.gridx = 0;
        cstCustom.gridwidth = 2;

        cstCustom.gridy = 3;
        pnlCustom.add(btnStart, cstCustom);
        cstCustom.gridy = 4;
        pnlCustom.add(btnCancel, cstCustom);

        assert pnlMain != null : "The main panel must not be null";

        pnlMain.add(pnlCustom);
        pack();
    }
}
