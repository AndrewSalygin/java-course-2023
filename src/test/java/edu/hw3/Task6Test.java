package edu.hw3;

import edu.hw3.Task6.MyStockMarket;
import edu.hw3.Task6.Stock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task6Test {
    static MyStockMarket market;
    @BeforeAll
    static void setUp() {
        market = new MyStockMarket();
    }
    @Test
    @DisplayName("Наиболее дорогая акция")
    void mostValuableStockTest() {
        market.add(new Stock("AMZN", 127.74));
        market.add(new Stock("AAPL", 168.22));
        market.add(new Stock("NVDA", 405.21));
        market.add(new Stock("TSLA", 207.30));
        market.remove(new Stock("NVDA", 405.21));
        assertThat(market.mostValuableStock()).isEqualTo(new Stock("TSLA", 207.30));
    }

    @Test
    @DisplayName("Пустой тикер")
    void emptyTickerTest() {
        Throwable thrown = catchThrowable(() -> market.add(new Stock("", 127.74)));
        assertThat(thrown).hasMessage("Тикер акции не должен быть пустым.");
    }

    @Test
    @DisplayName("Отрицательная цена акции")
    void negativePriceTest() {
        Throwable thrown = catchThrowable(() -> market.add(new Stock("AMZN", -127.74)));
        assertThat(thrown).hasMessage("Значение акции должно быть больше нуля.");
    }

    @Test
    @DisplayName("Нулевая цена акции")
    void zeroPriceTest() {
        Throwable thrown = catchThrowable(() -> market.add(new Stock("AMZN", 0)));
        assertThat(thrown).hasMessage("Значение акции должно быть больше нуля.");
    }
}
