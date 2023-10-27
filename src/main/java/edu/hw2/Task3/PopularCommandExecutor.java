package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;
    private final static Logger LOGGER = LogManager.getLogger();

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) {
        Connection connection = manager.getConnection();
        for (int i = 0; i < maxAttempts; i++) {
            try {
                connection.execute(command);
                return;
            } catch (ConnectionException ex) {
                LOGGER.info("Попытка повторного подключения. ", ex);
            }
        }
        try {
            connection.close();
        } catch (Exception ex) {
            LOGGER.info("Ошибка закрытия соединения: ", ex);
        }

        throw new ConnectionException("Превышено количество подключений.");
    }
}
