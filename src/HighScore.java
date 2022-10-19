import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.io.*;
import java.util.ArrayList;

class HighScore extends JFrame {
    HighScore() {
        super("HighScore");

        setMinimumSize(new Dimension(320, 240));
        setIconImage(Tile.catIcon.getImage());

        pack();
        setVisible(true);
    }
}

class HighScoreData {
    ArrayList<Score> scores = new ArrayList<>();
    int lastId;

    HighScoreData(String filePath) {
        load(filePath);
    }

    void insert(Score score) {
        scores.add(score);
        lastId++;
    }

    void remove(int id) {
        Score toRemove = null;

        for (Score score : scores) {
            if (score.id == id) {
                toRemove = score;
            }
        }

        if (toRemove == null) {
            throw new RuntimeException("ID " + "`" + id + "`" + " doesn't exist");
        }

        scores.remove(toRemove);
        lastId--;
    }

    void dump(String filePath) {
        FileOutputStream file;

        try {
            file = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (var encoder = new XMLEncoder(file)) {
            encoder.writeObject(lastId);
            encoder.writeObject(scores);
        }
    }

    @SuppressWarnings("unchecked")
    private void load(String filePath) {
        FileInputStream file;

        try {
            file = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (var decoder = new XMLDecoder(file)) {
            Object lastId = decoder.readObject();
            Object scores = decoder.readObject();

            try {
                this.lastId = (Integer) lastId;
                this.scores = (ArrayList<Score>) scores;
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }
}

class Score {
    int id = 0;
    String name = "";
    long time = 0;

    Score(int id, String name, long time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }
}
