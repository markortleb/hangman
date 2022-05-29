import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

public class PlayScreen extends JPanel {

    private MainWindow mainWindow;
    private String category;
    private final Color backgroundColor = Color.WHITE;
    private JPanel leftPanel, rightPanel;

    public PlayScreen(MainWindow mainWindow, String category) {
        this.mainWindow = mainWindow;
        this.category = category;
        this.leftPanel = new FramePanel();
        this.rightPanel = new GamePanel(category);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(backgroundColor);

        add(this.leftPanel);
        add(this.rightPanel);

    }


    private class FramePanel extends JPanel{

        private final int maxFrameNum = 9;
        private int frameNum;

        public FramePanel(){
            frameNum = 0;
            setBackground(Color.WHITE);
            add(getFrame());
            setMaximumSize(getPreferredSize());
        }

        private JLabel getFrame(){
            String imagePath = "img/hang_frame_" + Integer.toString(frameNum) + ".png";
            return new JLabel(new ImageIcon(getClass().getResource(imagePath)));
        }

        public boolean nextFrame(){
            boolean hasMoreFrames = true;

            if (frameNum < maxFrameNum) {
                frameNum++;
                removeAll();
                revalidate();
                repaint();
                add(getFrame());
            } else {
                hasMoreFrames = false;
            }

            return hasMoreFrames;
        }
    }

    private class GamePanel extends JPanel{

        String[] letters;
        String type;
        String guessPhrase;

        public GamePanel(String type) {
            this.type = type;
            this.guessPhrase = getGuessPhrase();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);

            add(getButtonsPanel());
            setMaximumSize(getPreferredSize());
        }

        private String getGuessPhrase() {
            List<String> phraseList = new ArrayList<String>();
            URL phrasesURL = getClass().getResource("categories/" + type.toLowerCase() + ".txt");

            // Add phrases from text file to phraseList
            try {
                Scanner scanner = new Scanner(new File(phrasesURL.getPath()));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    phraseList.add(line);
                }
            } catch (IOException e){
                System.out.println("Failed to load");
            } catch (NullPointerException e) {
                System.out.println("Null pointer exception detected.");
            }

            return phraseList.get((new Random()).nextInt(phraseList.size()));
        }

        private JPanel getButtonsPanel(){
            JPanel buttonsPanel = new JPanel();
            int[] buttonsPerRow = {7, 7, 7, 5};
            int ctr = 0;
            int curRow = 0;

            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
            buttonsPanel.setBorder(new EmptyBorder(10, 100, 10, 10));
            buttonsPanel.setBackground(backgroundColor);

            JPanel buttonRow = new JPanel();
            for (char c = 65; c < 91; c++){
                JButton charButton = new JButton(Character.toString(c));
                charButton.setBackground(new Color(229, 250, 58));
                charButton.setFocusPainted(false);
                buttonRow.add(charButton);
                ctr++;
                if (ctr >= buttonsPerRow[curRow]) {
                    buttonRow.setBackground(backgroundColor);
                    buttonsPanel.add(buttonRow);
                    buttonRow = new JPanel();
                    ctr = 0;
                    curRow++;
                }
            }

            return buttonsPanel;
        }

    }



}
