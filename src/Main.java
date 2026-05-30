import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Font;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::showPlaceholderWindow);
    }

    private static void showPlaceholderWindow() {
        JFrame frame = new JFrame("OOP News Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 400);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel("OOP News Reader - setup OK", JLabel.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 18f));
        frame.add(label);

        frame.setVisible(true);
    }
}
