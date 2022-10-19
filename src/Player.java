import javax.swing.*;

import static java.awt.Image.SCALE_SMOOTH;

enum PlayerIcon {
    Normal,
    Happy,
    Embarrassed
}

class Player extends JLabel {
    private static final ImageIcon playerNormalIcon = new ImageIcon("res/face-smile-solid.png");
    private static final ImageIcon playerHappyIcon = new ImageIcon("res/face-smile-beam-solid.png");
    private static final ImageIcon playerEmbarrassedIcon = new ImageIcon("res/face-grin-beam-sweat-solid.png");

    private PlayerIcon playerIcon = PlayerIcon.Normal;

    Player() {
        super();

        resizeAndSetIcon(playerNormalIcon);
    }

    void setPlayerIcon(PlayerIcon playerIcon) {
        this.playerIcon = playerIcon;

        switch (playerIcon) {
            case Normal -> resizeAndSetIcon(playerNormalIcon);
            case Happy -> resizeAndSetIcon(playerHappyIcon);
            case Embarrassed -> resizeAndSetIcon(playerEmbarrassedIcon);
        }
    }

    private void resizeAndSetIcon(ImageIcon icon) {
        var image= icon.getImage().getScaledInstance(64, 64, SCALE_SMOOTH);
        setIcon(new ImageIcon(image));
    }
}
