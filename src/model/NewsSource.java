package model;

import java.util.List;

public interface NewsSource {

    String getName();

    List<Article> fetchLatest() throws NewsSourceException;
}
