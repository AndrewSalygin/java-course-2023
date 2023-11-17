package edu.hw6.Task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    AbstractFilter REGULAR_FILE = Files::isRegularFile;
    AbstractFilter READABLE = Files::isReadable;
    AbstractFilter WRITABLE = Files::isWritable;

    static AbstractFilter largerThan(int size) {
        return path -> {
            try {
                return Files.size(path) > size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    @SuppressWarnings("MagicNumber")
    static AbstractFilter magicNumber(int... magicBytes) {
        return path -> {
            try {
                byte[] fileBytes = Files.readAllBytes(path);

                if (fileBytes.length < magicBytes.length) {
                    return false;
                }

                for (int i = 0; i < magicBytes.length; i++) {
                    if ((fileBytes[i] & 0xFF) != magicBytes[i]) {
                        return false;
                    }
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        };
    }

    static AbstractFilter globMatches(String glob) {
        return path -> path.getFileSystem().getPathMatcher("glob:" + glob).matches(path.getFileName());
    }

    static AbstractFilter regexContains(String regex) {
        return path -> {
            String fileName = path.getFileName().toString();
            return Pattern.compile(regex).matcher(fileName).find();
        };
    }

    default AbstractFilter and(AbstractFilter otherFilter) {
        return path -> this.accept(path) && otherFilter.accept(path);
    }
}
