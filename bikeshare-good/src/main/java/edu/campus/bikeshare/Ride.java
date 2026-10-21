package edu.campus.bikeshare;

public record Ride(
        String memberId,
        String bikeId,
        int minutes,
        int fee
) {
}