package edu.project3;


import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class LogRecord implements Comparable<LogRecord> {
    private final String remoteAddr;
    private final String remoteUser;
    private final OffsetDateTime timeLocal;
    private final Request request;
    private final Response status;
    private final int bodyBytesSent;
    private final String httpReferer;
    private final String httpUserAgent;

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public OffsetDateTime getTimeLocal() {
        return timeLocal;
    }

    public Request getRequest() {
        return request;
    }

    public Response getStatus() {
        return status;
    }

    public int getBodyBytesSent() {
        return bodyBytesSent;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    @SuppressWarnings("MagicNumber")
    public LogRecord(String inputRecord) {
        String regex =
            "^((\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}"
                + "|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}"
                + "(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}"
                + "(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:"
                + "((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+"
                + "|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])"
                + "|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]"
                + "|(2[0-4]|1?[0-9])?[0-9]))) -(( )| (\\S+) )- \\[(.+)] \"([A-Z]+) (\\S+) (HTTP/[0-9.]+)\" "
                + "(\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\"$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputRecord);

        if (matcher.find()) {
            if (matcher.group(2) == null) {
                this.remoteAddr = matcher.group(3);
            } else {
                this.remoteAddr = matcher.group(2);
            }
            if (matcher.group(34) == null) {
                this.remoteUser = matcher.group(35);
            } else {
                this.remoteUser = matcher.group(34);
            }
            this.timeLocal =
                OffsetDateTime.parse(
                    matcher.group(36),
                    DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH)
                );
            this.request = new Request(RequestMethod.valueOf(matcher.group(37)), matcher.group(38), matcher.group(39));
            this.status = getResponseByCode(Integer.parseInt(matcher.group(40)));
            this.bodyBytesSent = Integer.parseInt(matcher.group(41));
            this.httpReferer = matcher.group(42);
            this.httpUserAgent = matcher.group(43);
        } else {
            throw new IllegalArgumentException("Неверный формат записи: " + inputRecord);
        }
    }

    @SuppressWarnings({"MagicNumber", "InnerAssignment"})
    private static Response getResponseByCode(int code) {
        Response response;
        switch (code) {
            case 200 -> response = Response.OK;
            case 206 -> response = Response.PARTIAL_CONTENT;
            case 304 -> response = Response.NOT_MODIFIED;
            case 403 -> response = Response.FORBIDDEN;
            case 404 -> response = Response.NOT_FOUND;
            case 416 -> response = Response.REQUEST_RANGE_NOT_SATISFIABLE;
            default -> response = Response.UNKNOWN;
        }
        return response;
    }

    @Override
    public int compareTo(@NotNull LogRecord o) {
        return this.timeLocal.compareTo(o.timeLocal);
    }
}
