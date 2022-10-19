import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;

public class About extends JFrame {
    About() {
        super("About");

        var lblTitle = new JLabel("Catsweeper");
        var lblVersion = new JLabel("v0.1.0");
        var lblAuthor = new JLabel("By Simon Mărăcine");

        setMinimumSize(new Dimension(320, 240));
        setIconImage(Tile.catIcon.getImage());

        var cstAbout = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, CENTER, BOTH, new Insets(10, 10, 10, 10), 10, 10);
        setLayout(new GridBagLayout());

        cstAbout.gridy = 0;

        cstAbout.gridx = 0;
        add(lblTitle, cstAbout);
        cstAbout.gridx = 1;
        add(lblVersion, cstAbout);

        cstAbout.gridy = 1;

        cstAbout.gridx = 0;
        add(lblAuthor, cstAbout);

        pack();
        setVisible(true);
    }
}
