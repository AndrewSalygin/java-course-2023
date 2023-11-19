package edu.project3;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CreatorLogReport {
    // --path - первый обязательный параметр
    public LogReport createLogReport(Parameters parameters) {
        Stream<LogRecord> logRecordStream = createStreamFromDataInputs(parameters);
        return new LogReport(logRecordStream, parameters);
    }

    private Stream<LogRecord> createStreamFromDataInputs(Parameters parameters) {
        LinkedList<LogRecord> logRecordList = new LinkedList<>();
        HttpClient httpClient = HttpClient.newHttpClient();

        for (String path : parameters.getPaths()) {
            if (path.matches("^https?://\\S+$")) {
                HttpRequest request = HttpRequest.newBuilder(URI.create(path)).build();
                try {
                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                        String[] lines = response.body().split("\n");
                        for (String line : lines) {
                            logRecordList.addLast(new LogRecord(line));
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Path filePath = Paths.get(path);
                try {
                    List<String> lines = Files.readAllLines(filePath);
                    for (String line : lines) {
                        logRecordList.addLast(new LogRecord(line));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return logRecordList.stream();
    }
}
