import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;

class Application extends JFrame {
    private JPanel pnlMain;
    private final GridBagConstraints cstMain = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0);
    private Field field;
    private Player player;
    private int lastGameWidth = 0;
    private int lastGameHeight = 0;
    private int lastGameCatsPercentage = 0;

    Application() {
        super("Catsweeper");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));

        resetMainPanel();
        setupGameMenu();

        setVisible(true);
    }

    private void setupGameMenu() {
        var btnTenByTen = new JButton("10x10 field");
        var btnFifteenByFifteen = new JButton("15x15 field");
        var btnThirtyByFifteen = new JButton("30x15 field");
        var btnCustom = new JButton("Custom field");

        btnTenByTen.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(10, 10, 12);
        });

        btnFifteenByFifteen.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(15, 15, 12);
        });

        btnThirtyByFifteen.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(30, 15, 12);
        });

        btnCustom.addActionListener(actionEvent -> {
            resetMainPanel();
            setupCustom();
        });

        var cstButtons = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.0, CENTER, BOTH, new Insets(20, 20, 20, 20), 10, 70);
        var pnlButtons = new JPanel(new GridBagLayout());

        cstButtons.gridy = 0;

        cstButtons.gridx = 0;
        pnlButtons.add(btnTenByTen, cstButtons);
        cstButtons.gridx = 1;
        pnlButtons.add(btnFifteenByFifteen, cstButtons);

        cstButtons.gridy = 1;

        cstButtons.gridx = 0;
        pnlButtons.add(btnThirtyByFifteen, cstButtons);
        cstButtons.gridx = 1;
        pnlButtons.add(btnCustom, cstButtons);

        pnlMain.add(pnlButtons, cstMain);
        pack();
    }

    private void newGame() {
        resetMainPanel();
        setupGame(lastGameWidth, lastGameHeight, lastGameCatsPercentage);
    }

    private void about() {
        System.out.println("about");
    }

    private void resetMainPanel() {
        if (pnlMain != null) {
            remove(pnlMain);
        }

        pnlMain = new JPanel(new GridBagLayout());
        add(pnlMain);
    }

    private void setupGame(int width, int height, int catsPercentage) {
        assert width >= Field.MIN_WIDTH && width <= Field.MAX_WIDTH;
        assert height >= Field.MIN_HEIGHT && height <= Field.MAX_HEIGHT;
        assert catsPercentage >= Field.MIN_PERCENT && catsPercentage <= Field.MAX_PERCENT;

        if (field != null) {
            remove(field);
        }

        var pnlPlayer = setupPlayer();

        field = new Field(width, height, catsPercentage, player);
        var lytGrid = new GridLayout(width, height, 2, 2);
        field.setLayout(lytGrid);

        assert pnlMain != null : "The main panel must not be null";

        cstMain.gridx = 0;
        cstMain.weightx = 1.0;
        pnlMain.add(field, cstMain);
        cstMain.gridx = 1;
        cstMain.weightx = 1.0;  // FIXME this
        pnlMain.add(pnlPlayer, cstMain);
        pack();

        lastGameWidth = width;
        lastGameHeight = height;
        lastGameCatsPercentage = catsPercentage;
    }

    private JPanel setupPlayer() {
        var btnReset = new JButton("Reset");
        var btnHighScore = new JButton("HighScore");
        var btnMainMenu = new JButton("Main Menu");

        btnReset.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGame(lastGameWidth, lastGameHeight, lastGameCatsPercentage);
        });

        btnHighScore.addActionListener(actionEvent -> {
            // TODO this
        });

        btnMainMenu.addActionListener(actionEvent -> {
            resetMainPanel();
            setupGameMenu();
        });

        player = new Player();

        var cstPlayer = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, CENTER, BOTH, new Insets(20, 20, 20, 20), 0, 0);
        var pnlPlayer = new JPanel(new GridBagLayout());

        cstPlayer.gridy = 0;
        pnlPlayer.add(btnReset, cstPlayer);
        cstPlayer.gridy = 1;
        pnlPlayer.add(player, cstPlayer);
        cstPlayer.gridy = 2;
        pnlPlayer.add(btnHighScore, cstPlayer);
        cstPlayer.gridy = 3;
        pnlPlayer.add(btnMainMenu, cstPlayer);

        return pnlPlayer;
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

        var cstCustom = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, CENTER, BOTH, new Insets(20, 20, 20, 20), 0, 0);
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

        pnlMain.add(pnlCustom, cstMain);
        pack();
    }
}
