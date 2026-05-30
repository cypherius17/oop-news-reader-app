import ui.MainFrame;

import javax.swing.SwingUtilities;
import java.util.logging.Logger;

public class Main {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tH:%1$tM:%1$tS  %4$-7s %3$s - %5$s%n");
    }

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOG.info("Starting OOP News Reader");
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
