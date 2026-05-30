package source;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.AbstractHttpSource;
import model.Article;
import model.NewsSourceException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class HackerNewsSource extends AbstractHttpSource {

    private static final String TOP_STORIES =
            "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String ITEM =
            "https://hacker-news.firebaseio.com/v0/item/";
    private static final int LIMIT = 15;

    @Override
    public String getName() {
        return "Hacker News";
    }

    @Override
    public List<Article> fetchLatest() throws NewsSourceException {
        String idsBody = httpGet(TOP_STORIES);
        List<Article> articles = new ArrayList<>();
        try {
            JsonArray ids = JsonParser.parseString(idsBody).getAsJsonArray();
            int count = Math.min(LIMIT, ids.size());
            for (int i = 0; i < count; i++) {
                Article article = fetchItem(ids.get(i).getAsLong());
                if (article != null) {
                    articles.add(article);
                }
            }
        } catch (RuntimeException e) {
            throw new NewsSourceException("Could not parse response from " + getName(), e);
        }
        return articles;
    }

    private Article fetchItem(long id) throws NewsSourceException {
        JsonElement parsed = JsonParser.parseString(httpGet(ITEM + id + ".json"));
        if (parsed.isJsonNull()) {
            return null;
        }
        JsonObject item = parsed.getAsJsonObject();
        String title = textOf(item, "title");
        if (title.isBlank()) {
            return null;
        }
        String url = textOf(item, "url");
        if (url.isBlank()) {
            url = "https://news.ycombinator.com/item?id=" + id;
        }
        String author = textOf(item, "by");
        String summary = author.isBlank() ? "" : "Posted by " + author;
        String published = "";
        JsonElement time = item.get("time");
        if (time != null && !time.isJsonNull()) {
            published = Instant.ofEpochSecond(time.getAsLong()).toString();
        }
        return new Article(title, summary, url, getName(), published);
    }

    private static String textOf(JsonObject obj, String key) {
        JsonElement el = obj.get(key);
        return el == null || el.isJsonNull() ? "" : el.getAsString();
    }
}
