package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    @SuppressWarnings("MagicNumber")
    public void execute(String command) {
        double probability = 0.75;

        if (RandomProbability.attempt(probability)) {
            throw new ConnectionException("Команда не выполнена из-за проблемы с сетью. ");
        }
        LOGGER.info("Команда успешно выполнена.");
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Соединение закрыто.");
    }
}
