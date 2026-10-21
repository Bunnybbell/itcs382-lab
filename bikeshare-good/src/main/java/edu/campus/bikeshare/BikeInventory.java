package edu.campus.bikeshare;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Map;

public final class BikeInventory {
    private final Map<Station, Deque<String>> bikes =
            new EnumMap<>(Station.class);

    public BikeInventory() {
        int id = 1;
        for (Station station : Station.values()) {
            Deque<String> available = new ArrayDeque<>();
            for (int i = 0; i < 2; i++) {
                available.add("B" + id++);
            }
            bikes.put(station, available);
        }
    }

    public String takeBike(Station station) {
        String bikeId = bikes.get(station).poll();
        if (bikeId == null) {
            throw new IllegalStateException("No bikes available");
        }
        return bikeId;
    }

    public void returnBike(String bikeId, Station station) {
        bikes.get(station).add(bikeId);
    }
}
