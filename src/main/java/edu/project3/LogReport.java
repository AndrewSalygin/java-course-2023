package edu.project3;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class LogReport {
    private static final String FROM_STRING = "--from";
    private static final String TO_STRING = "--to";
    private static final int NUMBER_OF_RECORD = 3;
    private final List<String> files;
    private final OffsetDateTime startDateTime;
    private final OffsetDateTime endDateTime;
    private final int countOfRequest;
    private final double averageSizeOfResponse;
    private final List<Map.Entry<String, Integer>> resourcesSubList;
    private final List<Map.Entry<Response, Integer>> countOfResponsesSubList;
    private final List<Map.Entry<RequestMethod, Integer>> countOfEachRequestSubList;
    private final List<Map.Entry<String, Integer>> bytesSentByIpSubList;

    public LogReport(Stream<LogRecord> logRecordStream, Parameters parameters) {
        List<LogRecord> logRecords = logRecordStream.sorted().toList();
        files = parameters.getPaths();

        if (!parameters.getOptionalParameters().containsKey(FROM_STRING)) {
            startDateTime = logRecords.get(0).getTimeLocal();
        } else {
            startDateTime = OffsetDateTime.parse(
                parameters.getOptionalParameters().getOrDefault(FROM_STRING, "-") + "T00:00:00+00:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME
            );
        }

        if (!parameters.getOptionalParameters().containsKey(TO_STRING)) {
            endDateTime = logRecords.get(logRecords.size() - 1).getTimeLocal();
        } else {
            endDateTime = OffsetDateTime.parse(
                parameters.getOptionalParameters().getOrDefault(TO_STRING, "-") + "T23:59:59+00:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME
            );
        }

        logRecords = logRecords.stream()
            .filter(logRecord -> logRecord.getTimeLocal().isAfter(startDateTime)
                && logRecord.getTimeLocal().isBefore(endDateTime)).toList();

        countOfRequest = logRecords.size();
        long sumOfBytes = 0;

        TreeMap<String, Integer> resources = new TreeMap<>();
        TreeMap<Response, Integer> countOfResponses = new TreeMap<>();
        TreeMap<RequestMethod, Integer> countOfEachRequest = new TreeMap<>();
        TreeMap<String, Integer> bytesSentByUser = new TreeMap<>();

        String resource;
        Response response;
        RequestMethod requestMethod;
        String remoteAddr;
        int byteSent;
        for (LogRecord logRecord : logRecords) {
            sumOfBytes += logRecord.getBodyBytesSent();

            resource = logRecord.getRequest().resource();
            resources.put(resource, resources.getOrDefault(resource, 0) + 1);

            response = logRecord.getStatus();
            countOfResponses.put(response, countOfResponses.getOrDefault(response, 0) + 1);

            requestMethod = logRecord.getRequest().requestMethod();
            countOfEachRequest.put(requestMethod, countOfEachRequest.getOrDefault(requestMethod, 0) + 1);

            byteSent = logRecord.getBodyBytesSent();
            remoteAddr = logRecord.getRemoteAddr();
            bytesSentByUser.put(remoteAddr, bytesSentByUser.getOrDefault(remoteAddr, 0) + byteSent);
        }
        averageSizeOfResponse = (double) sumOfBytes / countOfRequest;

        List<Map.Entry<String, Integer>> resourcesList = new ArrayList<>(resources.entrySet());
        resourcesList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        resourcesSubList = resourcesList.subList(0, Math.min(NUMBER_OF_RECORD, resourcesList.size()));

        List<Map.Entry<Response, Integer>> countOfResponsesList = new ArrayList<>(countOfResponses.entrySet());
        countOfResponsesList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        countOfResponsesSubList =
            countOfResponsesList.subList(0, Math.min(NUMBER_OF_RECORD, countOfResponsesList.size()));

        List<Map.Entry<RequestMethod, Integer>> countOfEachRequestList = new ArrayList<>(countOfEachRequest.entrySet());
        countOfEachRequestList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        countOfEachRequestSubList =
            countOfEachRequestList.subList(0, Math.min(NUMBER_OF_RECORD, countOfEachRequestList.size()));

        List<Map.Entry<String, Integer>> bytesSentByUserList = new ArrayList<>(bytesSentByUser.entrySet());
        bytesSentByUserList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        bytesSentByIpSubList = bytesSentByUserList.subList(0, Math.min(NUMBER_OF_RECORD, bytesSentByUserList.size()));
    }

    public List<String> getFiles() {
        return files;
    }

    public OffsetDateTime getStartDateTime() {
        return startDateTime;
    }

    public OffsetDateTime getEndDateTime() {
        return endDateTime;
    }

    public int getCountOfRequest() {
        return countOfRequest;
    }

    public double getAverageSizeOfResponse() {
        return averageSizeOfResponse;
    }

    public List<Map.Entry<String, Integer>> getResourcesSubList() {
        return resourcesSubList;
    }

    public List<Map.Entry<Response, Integer>> getCountOfResponsesSubList() {
        return countOfResponsesSubList;
    }

    public List<Map.Entry<RequestMethod, Integer>> getCountOfEachRequestSubList() {
        return countOfEachRequestSubList;
    }

    public List<Map.Entry<String, Integer>> getBytesSentByIpSubList() {
        return bytesSentByIpSubList;
    }
}

