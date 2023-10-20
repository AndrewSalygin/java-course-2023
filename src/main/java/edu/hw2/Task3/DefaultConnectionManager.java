package edu.hw2.Task3;

public class DefaultConnectionManager implements ConnectionManager {
    @Override
    @SuppressWarnings("MagicNumber")
    public Connection getConnection() {
        double probability = 0.7;

        if (RandomProbability.random.nextDouble() < probability) {
            return new FaultyConnection();
        }
        return new StableConnection();
    }
}
