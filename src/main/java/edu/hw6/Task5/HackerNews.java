package edu.hw6.Task5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private static final String TOP_ARTICLES_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String NEWS_URL_FORMAT = "https://hacker-news.firebaseio.com/v0/item/%d.json";

    private static final int STATUS_OK = 200;
    private final HttpClient httpClient;

    public HackerNews() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public long[] hackerNewsTopStories() {
        HttpRequest request = HttpRequest.newBuilder(URI.create(TOP_ARTICLES_URL)).build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == STATUS_OK) {
                return Arrays.stream(response.body().substring(1, response.body().length() - 1).split(","))
                    .mapToLong(Long::parseLong)
                    .toArray();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new long[0];
    }

    public String news(long id) {
        try {
            String url = String.format(NEWS_URL_FORMAT, id);
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == STATUS_OK) {
                Pattern pattern = Pattern.compile("\"title\":\"(.*?)\"");
                Matcher matcher = pattern.matcher(response.body());

                if (matcher.find()) {
                    return matcher.group(1);
                } else {
                    throw new RuntimeException("Вернулся некорректный ответ с сервера.");
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
