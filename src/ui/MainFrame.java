package ui;

import model.Article;
import model.NewsSource;
import model.NewsSourceException;
import service.NewsService;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

public class MainFrame extends JFrame {

    private final NewsService service = new NewsService();
    private final JComboBox<NewsSource> sourceSelector = new JComboBox<>();
    private final JButton fetchButton = new JButton("Fetch");
    private final JLabel statusLabel = new JLabel("Select a source and click Fetch");
    private final ArticleListPanel listPanel = new ArticleListPanel();
    private final ArticleDetailPanel detailPanel = new ArticleDetailPanel();

    public MainFrame() {
        super("OOP News Reader");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        buildLayout();
        populateSources();
        wireEvents();
    }

    private void buildLayout() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.add(new JLabel("Source:"));
        top.add(sourceSelector);
        top.add(fetchButton);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, listPanel, detailPanel);
        split.setDividerLocation(330);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 10, 6, 10));

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void populateSources() {
        for (NewsSource source : service.getSources()) {
            sourceSelector.addItem(source);
        }
    }

    private void wireEvents() {
        sourceSelector.setRenderer(new SourceRenderer());
        fetchButton.addActionListener(event -> fetchSelected());
        listPanel.setSelectionListener(detailPanel::show);
    }

    private void fetchSelected() {
        NewsSource source = (NewsSource) sourceSelector.getSelectedItem();
        if (source == null) {
            return;
        }
        fetchButton.setEnabled(false);
        statusLabel.setText("Loading from " + source.getName() + " ...");
        listPanel.clear();
        detailPanel.clear();

        new SwingWorker<List<Article>, Void>() {
            @Override
            protected List<Article> doInBackground() throws NewsSourceException {
                return source.fetchLatest();
            }

            @Override
            protected void done() {
                try {
                    List<Article> articles = get();
                    listPanel.setArticles(articles);
                    statusLabel.setText(articles.size()
                            + " articles from " + source.getName());
                } catch (Exception ex) {
                    statusLabel.setText("Failed: " + rootMessage(ex));
                } finally {
                    fetchButton.setEnabled(true);
                }
            }
        }.execute();
    }

    private static String rootMessage(Exception ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }

    private static class SourceRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof NewsSource) {
                setText(((NewsSource) value).getName());
            }
            return this;
        }
    }
}
