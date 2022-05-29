import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StartScreen extends JPanel {

    private final Color backgroundColor = new Color(160, 179, 189);
    private MainWindow mainWindow;

    public StartScreen(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(backgroundColor);

        add(getTitleLabel());
        add(getPlayButton());

    }


    private JPanel getTitleLabel() {
        JPanel labelWrapper = new JPanel();
        JLabel titleLabel = new JLabel("Hangman");

        titleLabel.setFont(new Font("Serif", Font.PLAIN, 40));

        labelWrapper.setBorder(new EmptyBorder(30, 10, 10, 10));
        labelWrapper.setBackground(backgroundColor);
        labelWrapper.add(titleLabel);

        return labelWrapper;
    }


    private JPanel getPlayButton() {
        JPanel buttonWrapper = new JPanel();
        JButton playButton = new JButton("Play");


        playButton.setBackground(new Color(174, 185, 146));
        playButton.setForeground(new Color(12, 12, 12));
        playButton.setFocusPainted(false);
        playButton.setFont(new Font("Serif", Font.PLAIN, 20));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindow.showCategoryScreen();
            }
        });

        buttonWrapper.setBorder(new EmptyBorder(100, 10, 10, 10));
        buttonWrapper.setBackground(backgroundColor);

        buttonWrapper.add(playButton);


        return buttonWrapper;
    }

}
