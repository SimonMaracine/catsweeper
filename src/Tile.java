import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static java.awt.Image.SCALE_SMOOTH;

enum TileType {
    None,
    Number,
    Cat
}

enum TileIcon {
    None,
    Cat,
    Flag,
    QuestionMark
}

class Tile extends JButton implements ComponentListener {
    private final int x, y;
    private TileType tileType = TileType.None;
    private TileIcon tileIcon = TileIcon.None;
    private boolean revealed = false;
    private int number = 0;

    private static final ImageIcon catIcon = new ImageIcon("res/cat-solid.png");
    private static final ImageIcon flagIcon = new ImageIcon("res/flag-solid.png");
    private static final ImageIcon questionMarkIcon = new ImageIcon("res/question-solid.png");

    private long lastResize = 0;

    Tile(int x, int y) {
        super();

        this.x = x;
        this.y = y;

        addComponentListener(this);
    }

    void reveal() {
        revealed = true;

        switch (tileType) {
            case None, Number -> tileIcon = TileIcon.None;
            case Cat -> tileIcon = TileIcon.Cat;
            default -> {}
        }

        setEnabled(false);
        updateContents(getWidth(), getHeight());
    }

    boolean isRevealed() {
        return revealed;
    }

    TileType getTileType() {
        return tileType;
    }

    void setTileType(TileType tileType) {
        this.tileType = tileType;

        updateContents(getWidth(), getHeight());
    }

    TileIcon getTileIcon() {
        return tileIcon;
    }

    void setTileIcon(TileIcon tileIcon) {
        this.tileIcon = tileIcon;

        updateContents(getWidth(), getHeight());
    }

    void setNumber(int number) {
        this.number = number;
    }

    private void updateContents(int width, int height) {
        updateIcon(width, height);

        switch (tileType) {
            case None:
                break;
            case Number:
                if (revealed) {
                    if (number != 0) {
                        setText(String.valueOf(number));
                    }
                }
                break;
            case Cat:
                break;
        }
    }

    private void updateIcon(int width, int height) {
        switch (tileIcon) {
            case None -> setIcon(null);
            case Cat -> resizeAndSetIcon(catIcon, width, height);
            case Flag -> resizeAndSetIcon(flagIcon, width, height);
            case QuestionMark -> resizeAndSetIcon(questionMarkIcon, width, height);
        }
    }

    private void resizeAndSetIcon(ImageIcon icon, int componentWidth, int componentHeight) {
        final long current = System.currentTimeMillis();

        if (current - lastResize > 90) {  // TODO this is a bit dodgy
            lastResize = System.currentTimeMillis();
        } else {
            return;
        }

        int width;
        int height;

        if (componentWidth >= componentHeight) {
            width = componentHeight - 10;
            height = componentHeight - 10;
        } else {
            width = componentWidth - 10;
            height = componentWidth - 10;
        }

        var image= icon.getImage().getScaledInstance(width, height, SCALE_SMOOTH);  // FIXME resize only once per icon
        setIcon(new ImageIcon(image));
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        if (revealed) {
            var component = componentEvent.getComponent();
            updateContents(component.getWidth(), component.getHeight());
        }
    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {}

    @Override
    public void componentShown(ComponentEvent componentEvent) {}

    @Override
    public void componentHidden(ComponentEvent componentEvent) {}
}
