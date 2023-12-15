package edu.hw8.Task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int maxConnections;
    private final static int READABLE_BUFFER_CAPACITY = 128;

    public Server(int port, int maxConnections) {
        this.port = port;
        this.maxConnections = maxConnections;
    }

    public void startServer() {
        ExecutorService executorService = Executors.newFixedThreadPool(maxConnections);
        ServerSocketChannel serverSocketChannel;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось запустить сервер: " + e.getMessage());
        }

        while (true) {
            SocketChannel clientChannel;
            try {
                clientChannel = serverSocketChannel.accept();
                executorService.submit(() -> handleClient(clientChannel));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleClient(SocketChannel clientChannel) {
        ByteBuffer readablebyteBuffer;
        int bytesRead;
        try {
            readablebyteBuffer = ByteBuffer.allocate(READABLE_BUFFER_CAPACITY);
            bytesRead = clientChannel.read(readablebyteBuffer);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать слово клиента: " + e.getMessage());
        }

        String request = new String(readablebyteBuffer.array(), 0, bytesRead, StandardCharsets.UTF_8);
        String response = getResponse(request);
        ByteBuffer writablebyteBuffer = ByteBuffer.wrap(response.getBytes());

        try {
            clientChannel.write(writablebyteBuffer);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось получить ответ от сервера:" + e.getMessage());
        }
        try {
            clientChannel.close();
        } catch (IOException e) {
            throw new RuntimeException("Закрытие соединения с клиентом произошло с ошибкой: " + e.getMessage());
        }

    }

    private String getResponse(String keyword) {
        return switch (keyword) {
            case "личности" -> "Не переходи на личности там, где их нет";
            case "оскорбления" ->
                "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами";
            case "глупый" ->
                "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.";
            case "интеллект" -> "Чем ниже интеллект, тем громче оскорбления";
            default -> "Неизвестное ключевое слово";
        };
    }
}
