package edu.hw3.Task6;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MyStockMarket implements StockMarket {

    public static final Comparator<Stock> COAST_COMPARATOR = Comparator
        .comparingDouble(Stock::getPrice).reversed();

    private PriorityQueue<Stock> stocks;

    public MyStockMarket() {
        stocks = new PriorityQueue<>(COAST_COMPARATOR);
    }

    @Override
    public void add(Stock stock) {
        if (stocks.contains(stock)) {
            throw new IllegalArgumentException("Такая акция уже есть.");
        }
        stocks.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        if (!stocks.contains(stock)) {
            throw new IllegalArgumentException("Такой акции не существует.");
        }
        stocks.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stocks.peek();
    }
}
