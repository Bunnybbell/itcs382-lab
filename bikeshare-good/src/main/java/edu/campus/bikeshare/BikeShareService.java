package edu.campus.bikeshare;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class BikeShareService {
    private final BikeInventory inventory;
    private final FeeCalculator calculator;
    private final RideStore store;
    private final Map<String, Rental> rentals = new HashMap<>();

    public BikeShareService(
            BikeInventory inventory,
            FeeCalculator calculator,
            RideStore store) {
        this.inventory = inventory;
        this.calculator = calculator;
        this.store = store;
    }

    public String rent(
            String memberId, MemberType type, Station station) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("Member ID is required");
        }
        if (rentals.containsKey(memberId)) {
            throw new IllegalStateException(
                    "Member already has a bike");
        }

        String bikeId = inventory.takeBike(station);
        rentals.put(memberId, new Rental(memberId, type, bikeId));
        return bikeId;
    }

    public Ride returnBike(
            String memberId, Station station, int minutes)
            throws IOException {
        Rental rental = rentals.get(memberId);
        if (rental == null) {
            throw new IllegalStateException("No active rental");
        }

        int fee = calculator.calculate(
                rental.memberType(), minutes);

        Ride ride = new Ride(
                memberId, rental.bikeId(), minutes, fee);

        store.save(ride);
        rentals.remove(memberId);
        inventory.returnBike(rental.bikeId(), station);

        return ride;
    }
}
