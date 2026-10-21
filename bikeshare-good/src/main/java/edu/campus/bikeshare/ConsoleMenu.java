package edu.campus.bikeshare;

import java.io.IOException;
import java.util.Scanner;

public final class ConsoleMenu {
    private final BikeShareService service;

    public ConsoleMenu(BikeShareService service) {
        this.service = service;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Command (rent/return/quit): ");
                if (!scanner.hasNextLine()) {
                    return;
                }

                String command = scanner.nextLine()
                        .trim().toLowerCase();

                if (command.equals("quit")) {
                    return;
                }

                try {
                    switch (command) {
                        case "rent" -> rent(scanner);
                        case "return" -> returnBike(scanner);
                        default -> System.out.println(
                                "Unknown command");
                    }
                } catch (IllegalArgumentException
                        | IllegalStateException
                        | IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }

    private void rent(Scanner scanner) {
        String memberId = ask(scanner, "Member ID: ");
        MemberType type = MemberType.valueOf(
                ask(scanner, "Type (STUDENT/STAFF): ")
                        .toUpperCase());
        Station station = readStation(scanner);

        String bikeId = service.rent(memberId, type, station);
        System.out.println("Rented bike: " + bikeId);
    }

    private void returnBike(Scanner scanner) throws IOException {
        String memberId = ask(scanner, "Member ID: ");
        Station station = readStation(scanner);
        int minutes = Integer.parseInt(
                ask(scanner, "Ride minutes: "));

        Ride ride = service.returnBike(
                memberId, station, minutes);

        System.out.println("--- RECEIPT ---");
        System.out.println("Bike: " + ride.bikeId());
        System.out.println("Minutes: " + ride.minutes());
        System.out.println("Fee: " + ride.fee() + " baht");
    }

    private Station readStation(Scanner scanner) {
        return Station.valueOf(
                ask(scanner, "Station (LIBRARY/CANTEEN/DORM): ")
                        .toUpperCase());
    }

    private String ask(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
