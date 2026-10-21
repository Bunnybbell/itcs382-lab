package edu.campus.bikeshare;

import java.nio.file.Path;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        BikeShareService service = new BikeShareService(
                new BikeInventory(),
                new FeeCalculator(),
                new CsvRideStore(Path.of("rides.csv")));

        new ConsoleMenu(service).run();
    }
}
