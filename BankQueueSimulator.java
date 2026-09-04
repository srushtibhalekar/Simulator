import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BankQueueSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Queue<String> queue = new LinkedList<>();

        System.out.println("================================");
        System.out.println("     🏦 BANK QUEUE SIMULATOR");
        System.out.println("================================");

        while (true) {

            System.out.println("\n===== BANK MENU =====");
            System.out.println("1. Add Customer");
            System.out.println("2. Serve Customer");
            System.out.println("3. View Queue");
            System.out.println("4. Customer Count");
            System.out.println("5. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter customer name: ");
                    String name = sc.nextLine();

                    queue.add(name);

                    System.out.println("✅ " + name + " added to the queue.");
                    break;

                case 2:
                    if (queue.isEmpty()) {
                        System.out.println("❌ No customers waiting.");
                    } else {
                        String customer = queue.poll();
                        System.out.println("👤 Serving: " + customer);
                    }
                    break;

                case 3:
                    if (queue.isEmpty()) {
                        System.out.println("📭 Queue is empty.");
                    } else {
                        System.out.println("\n👥 Waiting Customers:");

                        int number = 1;

                        for (String customer : queue) {
                            System.out.println(number + ". " + customer);
                            number++;
                        }
                    }
                    break;

                case 4:
                    System.out.println("👥 Customers waiting: " + queue.size());
                    break;

                case 5:
                    System.out.println("\n🏦 Bank queue system closed.");
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}