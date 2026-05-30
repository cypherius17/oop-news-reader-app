package service;

import model.Article;
import model.NewsSource;
import model.NewsSourceException;
import source.HackerNewsSource;
import source.SpaceflightSource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NewsService {

    private static final Logger LOG = Logger.getLogger(NewsService.class.getName());

    private final List<NewsSource> sources;

    public NewsService() {
        this.sources = new ArrayList<>();
        this.sources.add(new SpaceflightSource());
        this.sources.add(new HackerNewsSource());
    }

    public List<NewsSource> getSources() {
        return new ArrayList<>(sources);
    }

    public List<Article> fetchFrom(NewsSource source) throws NewsSourceException {
        return source.fetchLatest();
    }

    public List<Article> fetchAll() {
        List<Article> all = new ArrayList<>();
        for (NewsSource source : sources) {
            try {
                all.addAll(source.fetchLatest());
            } catch (NewsSourceException e) {
                LOG.warning(e.getMessage());
            }
        }
        return all;
    }
}
