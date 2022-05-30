import javax.swing.*;
import java.awt.event.WindowEvent;


public class MainWindow extends JFrame {


    private JPanel contentPane;

    public MainWindow() {
        setTitle("Hangman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        showStartScreen();
        setResizable(false);
        setVisible(true);
    }

    public void showStartScreen() {
        contentPane = new StartScreen(this);
        setContentPane(contentPane);
        validate();
    }

    public void showCategoryScreen() {
        contentPane = new CategoryScreen(this);
        setContentPane(contentPane);
        validate();
    }

    public void showPlayScreen(String category) {
        contentPane = new PlayScreen(this, category);
        setContentPane(contentPane);
        validate();
    }

    public void quitProgram() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
