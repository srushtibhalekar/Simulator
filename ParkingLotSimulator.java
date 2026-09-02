
import java.util.Scanner;

public class ParkingLotSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] parkingSlots = new String[5];

        int choice;

        System.out.println("================================");
        System.out.println("      🚗 PARKING LOT SIMULATOR");
        System.out.println("================================");

        do {
            System.out.println("\n===== PARKING MENU =====");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. View Parking Slots");
            System.out.println("4. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    boolean parked = false;

                    for (int i = 0; i < parkingSlots.length; i++) {

                        if (parkingSlots[i] == null) {

                            System.out.print("Enter vehicle number: ");
                            parkingSlots[i] = sc.next();

                            System.out.println(
                                    "✅ Vehicle parked at Slot " + (i + 1)
                            );

                            parked = true;
                            break;
                        }
                    }

                    if (!parked) {
                        System.out.println("❌ Parking Full!");
                    }
                    break;

                case 2:
                    System.out.print("Enter slot number to remove vehicle: ");
                    int slot = sc.nextInt();

                    if (slot >= 1 && slot <= parkingSlots.length) {

                        if (parkingSlots[slot - 1] != null) {

                            System.out.println(
                                    "🚗 Vehicle " + parkingSlots[slot - 1]
                                    + " removed."
                            );

                            parkingSlots[slot - 1] = null;

                        } else {
                            System.out.println("❌ Slot is already empty.");
                        }

                    } else {
                        System.out.println("❌ Invalid slot number.");
                    }
                    break;

                case 3:
                    System.out.println("\n===== PARKING STATUS =====");

                    for (int i = 0; i < parkingSlots.length; i++) {

                        if (parkingSlots[i] == null) {
                            System.out.println(
                                    "Slot " + (i + 1) + " → Empty"
                            );
                        } else {
                            System.out.println(
                                    "Slot " + (i + 1)
                                    + " → " + parkingSlots[i]
                            );
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n👋 Thank you!");
                    System.out.println("Parking system closed.");
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
            }

        } while (choice != 4);

        sc.close();
    }
}

