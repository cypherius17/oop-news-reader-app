package ui;

import model.Article;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.function.Consumer;

public class ArticleListPanel extends JPanel {

    private final DefaultListModel<Article> model = new DefaultListModel<>();
    private final JList<Article> list = new JList<>(model);
    private Consumer<Article> selectionListener;

    public ArticleListPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Headlines"));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ArticleRenderer());
        list.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && selectionListener != null) {
                Article selected = list.getSelectedValue();
                if (selected != null) {
                    selectionListener.accept(selected);
                }
            }
        });
        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void setSelectionListener(Consumer<Article> listener) {
        this.selectionListener = listener;
    }

    public void setArticles(List<Article> articles) {
        model.clear();
        for (Article article : articles) {
            model.addElement(article);
        }
    }

    public void clear() {
        model.clear();
    }

    private static class ArticleRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Article) {
                setText(((Article) value).getTitle());
            }
            return this;
        }
    }
}
