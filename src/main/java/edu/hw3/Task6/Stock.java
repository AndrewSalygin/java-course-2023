package edu.hw3.Task6;

import java.util.Objects;

public class Stock {
    private String ticker;

    private Double price;

    public Stock(String ticker, double price) {
        if (Objects.equals(ticker, "")) {
            throw new IllegalArgumentException("Тикер акции не должен быть пустым.");
        }
        this.ticker = ticker;
        if (price <= 0) {
            throw new IllegalArgumentException("Значение акции должно быть больше нуля.");
        }
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public Double getPrice() {
        return price;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return Objects.equals(ticker, stock.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker);
    }

    @Override public String toString() {
        return ticker + " : " + price;
    }
}
