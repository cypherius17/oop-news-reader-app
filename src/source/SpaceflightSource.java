package source;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.AbstractHttpSource;
import model.Article;
import model.NewsSourceException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SpaceflightSource extends AbstractHttpSource {

    private static final Logger LOG = Logger.getLogger(SpaceflightSource.class.getName());

    private static final String ENDPOINT =
            "https://api.spaceflightnewsapi.net/v4/articles/?limit=20";

    @Override
    public String getName() {
        return "Spaceflight News";
    }

    @Override
    public List<Article> fetchLatest() throws NewsSourceException {
        String body = httpGet(ENDPOINT);
        List<Article> articles = new ArrayList<>();
        try {
            JsonObject root = JsonParser.parseString(body).getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");
            for (JsonElement element : results) {
                JsonObject item = element.getAsJsonObject();
                String title = textOf(item, "title");
                String url = textOf(item, "url");
                if (title.isBlank() || url.isBlank()) {
                    continue;
                }
                articles.add(new Article(
                        title,
                        textOf(item, "summary"),
                        url,
                        textOf(item, "news_site"),
                        textOf(item, "published_at")));
            }
        } catch (RuntimeException e) {
            throw new NewsSourceException("Could not parse response from " + getName(), e);
        }
        LOG.info("Parsed " + articles.size() + " articles from " + getName());
        return articles;
    }

    private static String textOf(JsonObject obj, String key) {
        JsonElement el = obj.get(key);
        return el == null || el.isJsonNull() ? "" : el.getAsString();
    }
}
