package edu.hw9.Task2;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public final class ParallelFileSystemProcessor {
    private ParallelFileSystemProcessor() {}

    public static List<Path> findDirectoriesWithManyFiles(String rootPath, int threshold) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new FindDirectoriesTask(Path.of(rootPath), threshold));
    }

    public static List<Path> findFilesMatchingPredicate(String rootPath, Predicate<File> predicate) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new FindFilesTask(Path.of(rootPath), predicate));
    }

    static class FindDirectoriesTask extends RecursiveTask<List<Path>> {
        private final Path path;
        private final int threshold;

        FindDirectoriesTask(Path path, int threshold) {
            this.path = path;
            this.threshold = threshold;
        }

        @Override
        protected List<Path> compute() {
            List<Path> result = new ArrayList<>();

            File currentDirectory = path.toFile();
            File[] files = currentDirectory.listFiles();

            if (files != null) {
                List<FindDirectoriesTask> subTasks = new ArrayList<>();

                for (File file : files) {
                    if (file.isDirectory()) {
                        FindDirectoriesTask subTask = new FindDirectoriesTask(Path.of(file.getPath()), threshold);
                        subTask.fork();
                        subTasks.add(subTask);
                    }
                }

                for (FindDirectoriesTask subTask : subTasks) {
                    result.addAll(subTask.join());
                }

                if (files.length > threshold) {
                    result.add(Path.of(currentDirectory.getPath()));
                }
            }

            return result;
        }
    }

    static class FindFilesTask extends RecursiveTask<List<Path>> {
        private final Path path;
        private final Predicate<File> predicate;

        FindFilesTask(Path path, Predicate<File> predicate) {
            this.path = path;
            this.predicate = predicate;
        }

        @Override
        protected List<Path> compute() {
            List<Path> result = new ArrayList<>();

            File currentDirectory = path.toFile();
            File[] files = currentDirectory.listFiles();

            if (files != null) {
                List<FindFilesTask> subTasks = new ArrayList<>();

                for (File file : files) {
                    if (file.isDirectory()) {
                        FindFilesTask subTask = new FindFilesTask(Path.of(file.getPath()), predicate);
                        subTask.fork();
                        subTasks.add(subTask);
                    } else {
                        if (predicate.test(file)) {
                            result.add(Path.of(file.getPath()));
                        }
                    }
                }

                for (FindFilesTask subTask : subTasks) {
                    result.addAll(subTask.join());
                }
            }

            return result;
        }
    }
}
