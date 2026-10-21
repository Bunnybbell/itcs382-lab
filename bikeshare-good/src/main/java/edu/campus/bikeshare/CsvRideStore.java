package edu.campus.bikeshare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class CsvRideStore implements RideStore {
    private final Path path;

    public CsvRideStore(Path path) {
        this.path = path;
    }

    @Override
    public void save(Ride ride) throws IOException {
        boolean needsHeader = Files.notExists(path)
                || Files.size(path) == 0;

        try (var writer = Files.newBufferedWriter(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            if (needsHeader) {
                writer.write("memberId,bikeId,minutes,fee");
                writer.newLine();
            }

            writer.write(csv(ride.memberId()) + ","
                    + csv(ride.bikeId()) + ","
                    + ride.minutes() + ","
                    + ride.fee());
            writer.newLine();
        }
    }

    private String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
