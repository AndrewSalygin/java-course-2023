package edu.project3;

public final class Main {

    private Main() {

    }

    public static void main(String[] args) {
        CreatorFileLogReport creatorFileLogReport = new CreatorFileLogReport(args);
        creatorFileLogReport.createFile();
    }
}
