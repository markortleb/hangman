import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryScreen extends JPanel{

    private MainWindow mainWindow;
    private final Color backgroundColor = new Color(160, 179, 189);

    public CategoryScreen(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(backgroundColor);

        add(getTitleLabel());
        add(getCategoryButtons());
    }

    private JPanel getTitleLabel() {
        JPanel labelWrapper = new JPanel();
        JLabel titleLabel = new JLabel("Choose a category!");

        titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));

        labelWrapper.setBorder(new EmptyBorder(30, 10, 10, 10));
        labelWrapper.setBackground(backgroundColor);
        labelWrapper.add(titleLabel);

        return labelWrapper;
    }

    private JPanel getCategoryButtons() {
        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));

        buttonRow.add(getButton("Companies"));
        buttonRow.add(getButton("Countries"));
        buttonRow.add(getButton("Movies"));

        return buttonRow;
    }

    private JPanel getButton(String buttonName) {
        JPanel buttonWrapper = new JPanel();
        JButton playButton = new JButton(buttonName);

        playButton.setBackground(new Color(174, 185, 146));
        playButton.setForeground(new Color(12, 12, 12));
        playButton.setFocusPainted(false);
        playButton.setFont(new Font("Serif", Font.PLAIN, 20));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindow.showPlayScreen(buttonName);
            }
        });

        buttonWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonWrapper.setBackground(backgroundColor);

        buttonWrapper.add(playButton);

        return buttonWrapper;
    }


}
