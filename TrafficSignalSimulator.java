import java.util.Scanner;

public class TrafficSignalSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("     TRAFFIC SIGNAL SIMULATOR");
        System.out.println("================================");

        System.out.println("\nChoose a signal:");
        System.out.println("1. RED");
        System.out.println("2. YELLOW");
        System.out.println("3. GREEN");

        System.out.print("\nEnter your choice: ");
        int choice = sc.nextInt();

        switch (choice) {

            case 1:
                System.out.println("\n🔴 RED LIGHT");
                System.out.println("STOP! Vehicles must wait.");
                break;

            case 2:
                System.out.println("\n🟡 YELLOW LIGHT");
                System.out.println("GET READY! Signal is about to change.");
                break;

            case 3:
                System.out.println("\n🟢 GREEN LIGHT");
                System.out.println("GO! Vehicles can move.");
                break;

            default:
                System.out.println("\n❌ Invalid choice!");
                System.out.println("Please select 1, 2, or 3.");
        }

        sc.close();
    }
}