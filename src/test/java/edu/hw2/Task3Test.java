package edu.hw2;

import edu.hw2.Task3.DefaultConnectionManager;
import edu.hw2.Task3.FaultyConnectionManager;
import edu.hw2.Task3.PopularCommandExecutor;
import edu.hw2.Task3.RandomProbability;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Random;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task3Test {
    static Random RANDOM;
    @BeforeAll
    static void setUp() {
        try {
            Class<?> clazz = RandomProbability.class;
            Field field = clazz.getDeclaredField("RANDOM");
            field.setAccessible(true);
            RANDOM = (Random) field.get("RANDOM");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("FaultyConnection (Fail) - количество попыток не хватило для установления соединения")
    void FaultyConnectionCommandFailedExecuteTest() {
        RANDOM.setSeed(41);
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(new FaultyConnectionManager(), 4);
        Throwable thrown = catchThrowable(commandExecutor::updatePackages);
        assertThat(thrown).hasMessage("Превышено количество подключений.");
    }

    @Test
    @DisplayName("FaultyConnection (Success) - количество попыток хватило для установления соединения")
    void FaultyConnectionCommandSuccessExecuteTest() {
        RANDOM.setSeed(41);
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(new FaultyConnectionManager(), 5);
        assertThatCode(commandExecutor::updatePackages).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("DefaultConnectionManager (Fail) - количество попыток не хватило для установления соединения")
    void DefaultConnectionManagerCommandFailedExecuteTest() {
        RANDOM.setSeed(587);
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(new DefaultConnectionManager(), 3);
        Throwable thrown = catchThrowable(commandExecutor::updatePackages);
        assertThat(thrown).hasMessage("Превышено количество подключений.");
    }

    @Test
    @DisplayName("DefaultConnectionManager (Success) - количество попыток хватило для установления соединения")
    void DefaultConnectionManagerCommandSuccessExecuteTest() {
        RANDOM.setSeed(41);
        PopularCommandExecutor commandExecutor = new PopularCommandExecutor(new DefaultConnectionManager(), 4);
        assertThatCode(commandExecutor::updatePackages).doesNotThrowAnyException();
    }
}
