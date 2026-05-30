package model;

import java.util.Objects;

public final class Article {

    private final String title;
    private final String summary;
    private final String url;
    private final String source;
    private final String publishedAt;

    public Article(String title, String summary, String url, String source, String publishedAt) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be empty");
        }
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("url must not be empty");
        }
        this.title = title.trim();
        this.summary = summary == null ? "" : summary.trim();
        this.url = url.trim();
        this.source = source == null || source.isBlank() ? "Unknown" : source.trim();
        this.publishedAt = publishedAt == null ? "" : publishedAt.trim();
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    @Override
    public String toString() {
        return title + " (" + source + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        Article other = (Article) o;
        return url.equals(other.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
