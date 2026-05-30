package ui;

import model.Article;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;

public class ArticleDetailPanel extends JPanel {

    private final JLabel titleLabel = new JLabel();
    private final JLabel metaLabel = new JLabel();
    private final JTextArea summaryArea = new JTextArea();
    private final JButton openButton = new JButton("Open in browser");
    private Article current;

    public ArticleDetailPanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 15f));
        metaLabel.setForeground(Color.GRAY);

        summaryArea.setEditable(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        summaryArea.setOpaque(false);

        JPanel header = new JPanel(new BorderLayout(4, 4));
        header.add(titleLabel, BorderLayout.NORTH);
        header.add(metaLabel, BorderLayout.SOUTH);

        openButton.setEnabled(false);
        openButton.addActionListener(event -> openCurrent());

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        add(openButton, BorderLayout.SOUTH);
    }

    public void show(Article article) {
        this.current = article;
        titleLabel.setText(article.getTitle());
        metaLabel.setText(article.getSource() + "   |   " + article.getPublishedAt());
        summaryArea.setText(article.getSummary());
        summaryArea.setCaretPosition(0);
        openButton.setEnabled(true);
    }

    public void clear() {
        this.current = null;
        titleLabel.setText("");
        metaLabel.setText("");
        summaryArea.setText("");
        openButton.setEnabled(false);
    }

    private void openCurrent() {
        if (current == null) {
            return;
        }
        if (!Desktop.isDesktopSupported()
                || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            JOptionPane.showMessageDialog(this, current.getUrl(),
                    "Article URL", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            Desktop.getDesktop().browse(URI.create(current.getUrl()));
        } catch (IOException | RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Could not open browser: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
