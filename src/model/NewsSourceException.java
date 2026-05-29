package model;

public class NewsSourceException extends Exception {

    public NewsSourceException(String message) {
        super(message);
    }

    public NewsSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
