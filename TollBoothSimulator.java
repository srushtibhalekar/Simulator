import java.util.Scanner;

public class TollBoothSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int totalVehicles = 0;
        double totalCollection = 0;

        System.out.println("================================");
        System.out.println("      🚧 TOLL BOOTH SIMULATOR");
        System.out.println("================================");

        while (true) {

            System.out.println("\n===== TOLL MENU =====");
            System.out.println("1. Car      - ₹50");
            System.out.println("2. Bike     - ₹20");
            System.out.println("3. Bus      - ₹100");
            System.out.println("4. Truck    - ₹150");
            System.out.println("5. View Collection");
            System.out.println("6. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();

            double toll = 0;

            switch (choice) {

                case 1:
                    toll = 50;
                    System.out.println("🚗 Car passed.");
                    break;

                case 2:
                    toll = 20;
                    System.out.println("🏍️ Bike passed.");
                    break;

                case 3:
                    toll = 100;
                    System.out.println("🚌 Bus passed.");
                    break;

                case 4:
                    toll = 150;
                    System.out.println("🚛 Truck passed.");
                    break;

                case 5:
                    System.out.println("\nVehicles Passed: " + totalVehicles);
                    System.out.println("Total Collection: ₹" + totalCollection);
                    continue;

                case 6:
                    System.out.println("\n================================");
                    System.out.println("       TOLL BOOTH CLOSED");
                    System.out.println("================================");
                    System.out.println("Total Vehicles: " + totalVehicles);
                    System.out.println("Total Collection: ₹" + totalCollection);
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
                    continue;
            }

            totalVehicles++;
            totalCollection += toll;

            System.out.println("Toll Paid: ₹" + toll);
        }
    }
}