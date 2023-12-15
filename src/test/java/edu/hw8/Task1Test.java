package edu.hw8;

import edu.hw8.Task1.Client;
import edu.hw8.Task1.Server;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    @Test
    public void exampleTest() {
        Server server = new Server(5738, 4);
        CompletableFuture<String> responseFuture = new CompletableFuture<>();

        new Thread(() -> {
            server.startServer();
            Client client = new Client("localhost", 5738);

            String result = client.sendWord("личности");
            responseFuture.complete(result);
        }).start();

        try {
            String responseServer = responseFuture.get(3, TimeUnit.SECONDS);
            assertEquals("Не переходи на личности там, где их нет", responseServer);
        } catch (InterruptedException | ExecutionException | TimeoutException ignored) {}
    }
}
