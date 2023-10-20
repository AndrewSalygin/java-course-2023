package edu.hw2;

import edu.hw2.Task3.DefaultConnectionManager;
import edu.hw2.Task3.FaultyConnectionManager;
import edu.hw2.Task3.PopularCommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw2.Task3.RandomProbability.random;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task3Test {
    @Test
    @DisplayName("FaultyConnection (Fail) - количество попыток не хватило для установления соединения")
    void FaultyConnectionCommandFailedExecuteTest() {
        random.setSeed(41);
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(new FaultyConnectionManager(), 4);
        Throwable thrown = catchThrowable(commandExecutor::updatePackages);
        assertThat(thrown).hasMessage("Превышено количество подключений.");
    }

    @Test
    @DisplayName("FaultyConnection (Success) - количество попыток хватило для установления соединения")
    void FaultyConnectionCommandSuccessExecuteTest() {
        random.setSeed(41);
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(new FaultyConnectionManager(), 5);
        assertThatCode(commandExecutor::updatePackages).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("DefaultConnectionManager (Fail) - количество попыток не хватило для установления соединения")
    void DefaultConnectionManagerCommandFailedExecuteTest() {
        random.setSeed(587);
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(new DefaultConnectionManager(), 3);
        Throwable thrown = catchThrowable(commandExecutor::updatePackages);
        assertThat(thrown).hasMessage("Превышено количество подключений.");
    }

    @Test
    @DisplayName("DefaultConnectionManager (Success) - количество попыток хватило для установления соединения")
    void DefaultConnectionManagerCommandSuccessExecuteTest() {
        random.setSeed(41);
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(new DefaultConnectionManager(), 4);
        assertThatCode(commandExecutor::updatePackages).doesNotThrowAnyException();
    }
}
