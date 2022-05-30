import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

public class PlayScreen extends JPanel {

    private MainWindow mainWindow;
    private String category;
    private final Color backgroundColor = Color.WHITE;
    private FramePanel leftPanel;
    private GamePanel rightPanel;

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


    private void buttonClicked(char charSelected) {
        boolean hasMoreFrames = true;
        int gameOver = 0;
        boolean matchFound = rightPanel.updatePanel(charSelected);

        if (!matchFound) {
             hasMoreFrames = leftPanel.nextFrame();
        }

        if (!hasMoreFrames) {
            gameOver = -1;
        }

        if (rightPanel.isSolutionReached()){
            gameOver = 1;
        }

        if (gameOver != 0) {
            rightPanel.showResults(gameOver);
        }

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
            boolean hasMoreFrames = frameNum < maxFrameNum - 1;

            frameNum++;
            removeAll();
            revalidate();
            repaint();
            add(getFrame());


            return hasMoreFrames;
        }
    }

    private class GamePanel extends JPanel{

        ArrayList<ArrayList<Character>> guessList;
        ArrayList<ArrayList<Character>> solutionList;
        ArrayList<Character> charactersClicked;
        String type;
        String guessPhrase;

        public GamePanel(String type) {
            this.type = type;
            this.guessPhrase = getGuessPhrase();
            this.charactersClicked = new ArrayList<Character>();
            initLetterLists();

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);

            setSize(new Dimension(544, 600));

            add(getDisplayPanel());
            add(getButtonsPanel());
        }

        public boolean updatePanel(char characterClicked) {
            boolean foundMatch = false;

            // Update letter lists
            for (int i = 0; i < guessList.size(); i++) {
                for (int j = 0; j < guessList.get(i).size(); j++){
                    if (Character.toUpperCase(characterClicked)
                            == Character.toUpperCase(solutionList.get(i).get(j))){
                        guessList.get(i).set(j, solutionList.get(i).get(j));
                        foundMatch = true;
                    }
                }
            }

            // Add char to list of clicked chars
            charactersClicked.add(characterClicked);

            // Update Panels
            removeAll();
            add(getDisplayPanel());
            add(getButtonsPanel());
            revalidate();
            repaint();

            return foundMatch;
        }

        public boolean isSolutionReached(){
            boolean solutionReached = false;

            solutionReached = solutionList.equals(guessList);

            return solutionReached;
        }

        public void showResults(int gameOver){
            JPanel resultsPanel = new JPanel();
            JLabel resultMessage = new JLabel();
            JLabel answerMessage = new JLabel();
            JButton playAgainButton = new JButton();
            JButton chooseCategoryButton = new JButton();
            JButton quitButton = new JButton();

            resultsPanel.setBackground(backgroundColor);
            resultsPanel.setLayout(new GridLayout(0, 1));
            resultsPanel.setBorder(new EmptyBorder(30, 10, 25, 10));
            resultsPanel.setSize(new Dimension(544, 600));

            if (gameOver == 1){
                resultMessage.setText("You Win!");
            } else {
                resultMessage.setText("You Lose!");
            }

            resultMessage.setHorizontalAlignment(JLabel.CENTER);
            resultMessage.setFont(new Font("Serif", Font.PLAIN, 35));

            answerMessage.setText("Answer: " + guessPhrase);
            answerMessage.setHorizontalAlignment(JLabel.CENTER);
            answerMessage.setFont(new Font("Serif", Font.PLAIN, 14));

            playAgainButton.setText("Play Again");
            playAgainButton.setBackground(new Color(200, 255, 174));
            playAgainButton.setFocusPainted(false);
            playAgainButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    mainWindow.showPlayScreen(category);
                }
            });

            chooseCategoryButton.setText("Choose Category");
            chooseCategoryButton.setBackground(new Color(255, 253, 150));
            chooseCategoryButton.setFocusPainted(false);
            chooseCategoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    mainWindow.showCategoryScreen();
                }
            });

            quitButton.setText("Quit");
            quitButton.setBackground(new Color(255, 142, 142));
            quitButton.setFocusPainted(false);
            quitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    mainWindow.quitProgram();
                }
            });

            resultsPanel.add(resultMessage);
            resultsPanel.add(answerMessage);
            resultsPanel.add(playAgainButton);
            resultsPanel.add(chooseCategoryButton);
            resultsPanel.add(quitButton);

            removeAll();
            add(resultsPanel);
            revalidate();
            repaint();

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

        private void initLetterLists() {
            guessList = new ArrayList<ArrayList<Character>>();
            solutionList = new ArrayList<ArrayList<Character>>();

            StringTokenizer st = new StringTokenizer(this.guessPhrase);

            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                ArrayList<Character> tempList1 = new ArrayList<Character>();
                ArrayList<Character> tempList2 = new ArrayList<Character>();
                for (int i = 0; i < token.length(); i++){
                    tempList1.add('_');
                    tempList1.add(' ');
                    tempList2.add(token.charAt(i));
                    tempList2.add(' ');
                }
                guessList.add(tempList1);
                solutionList.add(tempList2);
            }
        }

        private JPanel getButtonsPanel(){
            JPanel buttonsPanel = new JPanel();
            int[] buttonsPerRow = {7, 7, 7, 5};
            int ctr = 0;
            int curRow = 0;

            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
            buttonsPanel.setBorder(new EmptyBorder(10, 30, 25, 10));
            buttonsPanel.setBackground(backgroundColor);
            buttonsPanel.setSize(new Dimension(456, 300));

            JPanel buttonRow = new JPanel();
            for (char c = 65; c < 91; c++){
                JButton charButton = new JButton(Character.toString(c));
                char finalC = c;

                if (charactersClicked.contains(finalC)){
                    charButton.setBackground(new Color(161, 161, 161));
                    charButton.setForeground(new Color(128, 128, 128));
                    charButton.setEnabled(false);
                } else {
                    charButton.setBackground(new Color(229, 250, 58));
                }

                charButton.setFocusPainted(false);

                charButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        buttonClicked(finalC);
                    }
                });

                buttonRow.add(charButton);
                ctr++;
                if (ctr >= buttonsPerRow[curRow]) {
                    buttonRow.setBackground(backgroundColor);
                    buttonRow.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                    buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));
                    buttonRow.setSize(new Dimension(360, 60));
                    buttonRow.setBorder(new EmptyBorder(5, 5, 5, 5));
                    buttonsPanel.add(buttonRow);
                    buttonRow = new JPanel();
                    ctr = 0;
                    curRow++;
                }
            }

            return buttonsPanel;
        }

        private JPanel getDisplayPanel() {
            JPanel displayPanel = new JPanel();
            displayPanel.setBackground(backgroundColor);
            displayPanel.setLayout(new GridLayout(0, 1));

            displayPanel.setBorder(new EmptyBorder(40, 10, 10, 10));

            for (int i = 0; i < guessList.size(); i++){
                String guessString = "";
                for (int j = 0; j < guessList.get(i).size(); j++){
                    guessString += guessList.get(i).get(j);
                }
                JLabel guessLabel = new JLabel(guessString);
                guessLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                guessLabel.setHorizontalAlignment(JLabel.CENTER);
                displayPanel.add(guessLabel);
            }

            return displayPanel;
        }
    }
}
