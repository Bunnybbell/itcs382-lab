package edu.campus.bikeshare;

public record Rental(
        String memberId,
        MemberType memberType,
        String bikeId
) {
}