package model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractHttpSource implements NewsSource {

    private static final Logger LOG = Logger.getLogger(AbstractHttpSource.class.getName());

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    protected String httpGet(String url) throws NewsSourceException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .header("Accept", "application/json")
                .GET()
                .build();
        LOG.info("GET " + url);
        long start = System.currentTimeMillis();
        try {
            HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            LOG.info(getName() + " responded " + status
                    + " in " + (System.currentTimeMillis() - start) + " ms");
            if (status < 200 || status >= 300) {
                throw new NewsSourceException(getName() + " returned HTTP " + status);
            }
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new NewsSourceException("Fetch from " + getName() + " was interrupted", e);
        } catch (IOException e) {
            throw new NewsSourceException("Failed to fetch from " + getName(), e);
        }
    }

    @Override
    public abstract List<Article> fetchLatest() throws NewsSourceException;
}
