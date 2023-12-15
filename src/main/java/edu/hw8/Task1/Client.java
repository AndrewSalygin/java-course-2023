package edu.hw8.Task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class Client {
    private final SocketChannel socketChannel;
    private final static int READABLE_BUFFER_CAPACITY = 1024;

    public Client(String serverIP, int serverPort) {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(serverIP, serverPort));
        } catch (IOException e) {
            throw new RuntimeException("Соединение открыть не удалось");
        }
    }

    public String sendWord(String word) {
        ByteBuffer writableBuffer = ByteBuffer.wrap(word.getBytes());
        try {
            socketChannel.write(writableBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<String> responseFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            ByteBuffer readableBuffer = ByteBuffer.allocate(READABLE_BUFFER_CAPACITY);
            try {
                int bytesRead = socketChannel.read(readableBuffer);
                String response = new String(readableBuffer.array(), 0, bytesRead, StandardCharsets.UTF_8).trim();

                responseFuture.complete(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return responseFuture.join();
    }
}
