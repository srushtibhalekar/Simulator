import java.util.ArrayList;
import java.util.Scanner;

class Expense {

    String category;
    double amount;

    Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

public class ExpenseTrackerSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Expense> expenses = new ArrayList<>();

        System.out.println("================================");
        System.out.println("     💰 EXPENSE TRACKER");
        System.out.println("================================");

        while (true) {

            System.out.println("\n===== EXPENSE MENU =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Spending");
            System.out.println("4. Highest Expense");
            System.out.println("5. Delete Expense");
            System.out.println("6. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter expense category: ");
                    String category = sc.nextLine();

                    System.out.print("Enter amount: ₹");
                    double amount = sc.nextDouble();

                    if (amount <= 0) {
                        System.out.println("❌ Amount must be greater than 0.");
                    } else {
                        expenses.add(new Expense(category, amount));
                        System.out.println("✅ Expense added successfully!");
                    }

                    break;

                case 2:

                    if (expenses.isEmpty()) {
                        System.out.println("📭 No expenses recorded.");
                    } else {

                        System.out.println("\n===== EXPENSE LIST =====");

                        for (int i = 0; i < expenses.size(); i++) {

                            Expense expense = expenses.get(i);

                            System.out.println(
                                    (i + 1) + ". "
                                            + expense.category
                                            + " - ₹"
                                            + expense.amount
                            );
                        }
                    }

                    break;

                case 3:

                    if (expenses.isEmpty()) {
                        System.out.println("📭 No expenses available.");
                    } else {

                        double total = 0;

                        for (Expense expense : expenses) {
                            total += expense.amount;
                        }

                        System.out.println(
                                "\n💰 Total Spending: ₹" + total
                        );
                    }

                    break;

                case 4:

                    if (expenses.isEmpty()) {
                        System.out.println("📭 No expenses available.");
                    } else {

                        Expense highest = expenses.get(0);

                        for (Expense expense : expenses) {

                            if (expense.amount > highest.amount) {
                                highest = expense;
                            }
                        }

                        System.out.println("\n🔥 Highest Expense");
                        System.out.println("Category: " + highest.category);
                        System.out.println("Amount: ₹" + highest.amount);
                    }

                    break;

                case 5:

                    if (expenses.isEmpty()) {
                        System.out.println("📭 No expenses to delete.");
                        break;
                    }

                    System.out.print("Enter expense number to delete: ");
                    int index = sc.nextInt();

                    if (index >= 1 && index <= expenses.size()) {

                        Expense removed = expenses.remove(index - 1);

                        System.out.println(
                                "🗑️ Deleted: "
                                        + removed.category
                                        + " - ₹"
                                        + removed.amount
                        );

                    } else {
                        System.out.println("❌ Invalid expense number.");
                    }

                    break;

                case 6:

                    System.out.println("\n================================");
                    System.out.println("      💰 EXPENSE TRACKER CLOSED");
                    System.out.println("================================");

                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}