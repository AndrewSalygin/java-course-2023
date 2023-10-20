package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    @SuppressWarnings("MagicNumber")
    public void execute(String command) {
        LOGGER.info("Соединение открыто.");

        double probability = 0.75;

        if (RandomProbability.random.nextDouble() < probability) {
            throw new ConnectionException("Соединение закрылось с ошибкой. ");
        }
        LOGGER.info("Команда успешно выполнена.");
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Соединение закрыто.");
    }
}
