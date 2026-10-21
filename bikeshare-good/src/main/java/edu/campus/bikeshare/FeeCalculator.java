package edu.campus.bikeshare;

public final class FeeCalculator {
    public int calculate(MemberType type, int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException(
                    "Minutes must be positive");
        }

        int rate = type == MemberType.STUDENT ? 2 : 3;
        return Math.multiplyExact(rate, minutes);
    }
}