package edu.hw6.Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ClonerFile {

    private ClonerFile() {}

    public static void cloneFile(Path path) {
        if (!Files.exists(path)) {
            throw new RuntimeException("Клонируемого файла не существует");
        }
        String fullFileName = String.valueOf(path.getFileName());
        String fileName = fullFileName.substring(0, fullFileName.lastIndexOf('.'));
        String fileExtension = fullFileName.substring(fullFileName.lastIndexOf('.'));
        Path destinationPath = path.getParent();

        Path tmpPath = destinationPath.resolve(fileName + " — копия" + fileExtension);
        int index = 2;
        try {
            while (Files.exists(tmpPath)) {
                tmpPath = destinationPath.resolve(fileName + " — копия (" + index + ")" + fileExtension);
                index++;
            }
            Files.copy(path, tmpPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
