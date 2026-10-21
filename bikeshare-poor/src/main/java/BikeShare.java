import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class BikeShare {

    public static String[] stations = {"Library", "Canteen", "Dorm"};
    public static String[] bikeStation = {
        "Library", "Library",
        "Canteen", "Canteen",
        "Dorm", "Dorm"
    };

    public static String[] renters = new String[6];
    public static int[] memberTypes = new int[6];
    public static int[] fees = {2, 3};
    public static Path file = Path.of("rides.csv");

    public static int findBike(String station, boolean renting, String memberId) {
        for (int i = 0; i < bikeStation.length; i++) {
            if (renting && bikeStation[i].equalsIgnoreCase(station)
                    && renters[i] == null) {
                return i;
            }
            if (!renting && memberId.equals(renters[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void processRide(Scanner scanner, boolean renting) {
        try {
            System.out.print("Member ID: ");
            String id = scanner.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("Invalid member ID.");
                return;
            }

            if (renting) {
                if (findBike("", false, id) != -1) {
                    System.out.println("Member already has a bike.");
                    return;
                }

                System.out.print("Member type (1=Student, 2=Staff): ");
                int type = Integer.parseInt(scanner.nextLine().trim());

                if (type != 1 && type != 2) {
                    System.out.println("Invalid member type.");
                    return;
                }

                System.out.print("Station (Library/Canteen/Dorm): ");
                String station = scanner.nextLine().trim();

                int bike = findBike(station, true, id);

                if (bike == -1) {
                    System.out.println("No available bike or invalid station.");
                    return;
                }

                renters[bike] = id;
                memberTypes[bike] = type;
                bikeStation[bike] = "";

                System.out.println("Rented Bike " + (bike + 1));
            } else {
                int bike = findBike("", false, id);

                if (bike == -1) {
                    System.out.println("No active rental found.");
                    return;
                }

                System.out.print("Return station (Library/Canteen/Dorm): ");
                String station = scanner.nextLine().trim();

                String selectedStation = null;
                for (String s : stations) {
                    if (s.equalsIgnoreCase(station)) {
                        selectedStation = s;
                        break;
                    }
                }

                if (selectedStation == null) {
                    System.out.println("Invalid station.");
                    return;
                }

                System.out.print("Ride time (minutes): ");
                int minutes = Integer.parseInt(scanner.nextLine().trim());

                if (minutes <= 0) {
                    System.out.println("Minutes must be positive.");
                    return;
                }

                int fee = minutes * fees[memberTypes[bike] - 1];

                String record = (bike + 1) + "," + id + ","
                        + memberTypes[bike] + "," + selectedStation
                        + "," + minutes + "," + fee;

                try (BufferedWriter writer = Files.newBufferedWriter(
                        file,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND)) {
                    writer.write(record);
                    writer.newLine();
                }

                bikeStation[bike] = selectedStation;
                renters[bike] = null;
                memberTypes[bike] = 0;

                System.out.println("--- Receipt ---");
                System.out.println("Bike: " + (bike + 1));
                System.out.println("Minutes: " + minutes);
                System.out.println("Fee: " + fee + " baht");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (ArithmeticException e) {
            System.out.println("Fee calculation error.");
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== BikeShare ===");
                System.out.println("1. Rent bike");
                System.out.println("2. Return bike");
                System.out.println("Type quit to exit");
                System.out.print("Command: ");

                if (!scanner.hasNextLine()) {
                    break;
                }

                String command = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("quit")) {
                    break;
                } else if (command.equals("1")) {
                    processRide(scanner, true);
                } else if (command.equals("2")) {
                    processRide(scanner, false);
                } else {
                    System.out.println("Invalid command.");
                }
            }
        }

        System.out.println("Goodbye!");
    }
}

