import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {

    private int accountId;
    private String accountHolderName;
    private String phone;
    private double balance;
    private boolean active;

    private ArrayList<String> transactions;

    public BankAccount(int accountId, String accountHolderName, String phone, double initialDeposit) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.phone = phone;
        this.balance = initialDeposit;
        this.active = true;
        this.transactions = new ArrayList<>();

        transactions.add("Account created with initial deposit: ₹" + initialDeposit);
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getPhone() {
        return phone;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public boolean deposit(double amount) {

        if (!active) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        balance += amount;
        transactions.add("Deposited: ₹" + amount);

        return true;
    }

    public boolean withdraw(double amount) {

        if (!active) {
            return false;
        }

        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        transactions.add("Withdrawn: ₹" + amount);

        return true;
    }

    public boolean transferTo(BankAccount receiver, double amount) {

        if (!active || !receiver.active) {
            return false;
        }

        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        receiver.balance += amount;

        transactions.add(
                "Transferred ₹" + amount + " to Account ID: " + receiver.accountId
        );

        receiver.transactions.add(
                "Received ₹" + amount + " from Account ID: " + accountId
        );

        return true;
    }

    public void closeAccount() {

        if (balance == 0) {
            active = false;
            transactions.add("Account closed.");
        }
    }

    public void displayAccount() {

        System.out.println("----------------------------------------");
        System.out.println("Account ID       : " + accountId);
        System.out.println("Account Holder   : " + accountHolderName);
        System.out.println("Phone            : " + phone);
        System.out.println("Balance          : ₹" + balance);
        System.out.println("Status           : " + (active ? "ACTIVE" : "CLOSED"));
        System.out.println("----------------------------------------");
    }

    public void displayTransactions() {

        System.out.println("\n===== Transaction History =====");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (int i = 0; i < transactions.size(); i++) {
            System.out.println((i + 1) + ". " + transactions.get(i));
        }
    }
}

public class BankingTransactionSimulator {

    private static Scanner scanner = new Scanner(System.in);

    private static ArrayList<BankAccount> accounts =
            new ArrayList<>();

    private static int nextAccountId = 1001;

    public static void main(String[] args) {

        while (true) {

            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    viewAllAccounts();
                    break;

                case 3:
                    searchAccount();
                    break;

                case 4:
                    depositMoney();
                    break;

                case 5:
                    withdrawMoney();
                    break;

                case 6:
                    transferMoney();
                    break;

                case 7:
                    checkBalance();
                    break;

                case 8:
                    viewTransactions();
                    break;

                case 9:
                    closeAccount();
                    break;

                case 10:
                    System.out.println("\nThank you for using Banking Transaction Simulator.");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {

        System.out.println("\n========================================");
        System.out.println("     BANKING TRANSACTION SIMULATOR");
        System.out.println("========================================");
        System.out.println("1. Create Bank Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Search Account by ID");
        System.out.println("4. Deposit Money");
        System.out.println("5. Withdraw Money");
        System.out.println("6. Transfer Money");
        System.out.println("7. Check Balance");
        System.out.println("8. View Transaction History");
        System.out.println("9. Close Account");
        System.out.println("10. Exit");
        System.out.println("========================================");
    }

    private static void createAccount() {

        System.out.println("\n===== Create Bank Account =====");

        String name = readString("Enter account holder name: ");
        String phone = readPhone("Enter 10-digit phone number: ");

        double initialDeposit;

        while (true) {

            initialDeposit = readDouble("Enter initial deposit: ₹");

            if (initialDeposit >= 0) {
                break;
            }

            System.out.println("Initial deposit cannot be negative.");
        }

        BankAccount account =
                new BankAccount(
                        nextAccountId,
                        name,
                        phone,
                        initialDeposit
                );

        accounts.add(account);

        System.out.println("\nAccount created successfully!");
        System.out.println("Account ID: " + nextAccountId);

        nextAccountId++;
    }

    private static void viewAllAccounts() {

        System.out.println("\n===== All Bank Accounts =====");

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        for (BankAccount account : accounts) {
            account.displayAccount();
        }
    }

    private static void searchAccount() {

        int id = readInt("Enter Account ID: ");

        BankAccount account = findAccountById(id);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.displayAccount();
    }

    private static void depositMoney() {

        System.out.println("\n===== Deposit Money =====");

        int id = readInt("Enter Account ID: ");

        BankAccount account = findAccountById(id);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (!account.isActive()) {
            System.out.println("Account is closed.");
            return;
        }

        double amount = readDouble("Enter deposit amount: ₹");

        if (account.deposit(amount)) {
            System.out.println("Money deposited successfully.");
            System.out.println("New Balance: ₹" + account.getBalance());
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    private static void withdrawMoney() {

        System.out.println("\n===== Withdraw Money =====");

        int id = readInt("Enter Account ID: ");

        BankAccount account = findAccountById(id);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (!account.isActive()) {
            System.out.println("Account is closed.");
            return;
        }

        double amount = readDouble("Enter withdrawal amount: ₹");

        if (account.withdraw(amount)) {
            System.out.println("Money withdrawn successfully.");
            System.out.println("Remaining Balance: ₹" + account.getBalance());
        } else {
            System.out.println(
                    "Withdrawal failed. Check amount and available balance."
            );
        }
    }

    private static void transferMoney() {

        System.out.println("\n===== Transfer Money =====");

        int senderId = readInt("Enter Sender Account ID: ");
        int receiverId = readInt("Enter Receiver Account ID: ");

        if (senderId == receiverId) {
            System.out.println("Sender and receiver cannot be the same.");
            return;
        }

        BankAccount sender = findAccountById(senderId);
        BankAccount receiver = findAccountById(receiverId);

        if (sender == null) {
            System.out.println("Sender account not found.");
            return;
        }

        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        if (!sender.isActive() || !receiver.isActive()) {
            System.out.println("Both accounts must be active.");
            return;
        }

        double amount = readDouble("Enter transfer amount: ₹");

        if (sender.transferTo(receiver, amount)) {

            System.out.println("Transfer successful.");
            System.out.println(
                    "Sender Balance: ₹" + sender.getBalance()
            );

        } else {

            System.out.println(
                    "Transfer failed. Check amount and available balance."
            );
        }
    }

    private static void checkBalance() {

        System.out.println("\n===== Check Balance =====");

        int id = readInt("Enter Account ID: ");

        BankAccount account = findAccountById(id);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println(
                "Account Holder: " + account.getAccountHolderName()
        );

        System.out.println(
                "Current Balance: ₹" + account.getBalance()
        );

        System.out.println(
                "Status: " + (account.isActive() ? "ACTIVE" : "CLOSED")
        );
    }

    private static void viewTransactions() {

        System.out.println("\n===== View Transaction History =====");

        int id = readInt("Enter Account ID: ");

        BankAccount account = findAccountById(id);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.displayTransactions();
    }

    private static void closeAccount() {

        System.out.println("\n===== Close Bank Account =====");

        int id = readInt("Enter Account ID: ");

        BankAccount account = findAccountById(id);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (!account.isActive()) {
            System.out.println("Account is already closed.");
            return;
        }

        if (account.getBalance() != 0) {

            System.out.println(
                    "Account cannot be closed because balance is not zero."
            );

            System.out.println(
                    "Current Balance: ₹" + account.getBalance()
            );

            return;
        }

        account.closeAccount();

        System.out.println("Account closed successfully.");
    }

    private static BankAccount findAccountById(int id) {

        for (BankAccount account : accounts) {

            if (account.getAccountId() == id) {
                return account;
            }
        }

        return null;
    }

    private static String readString(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static String readPhone(String message) {

        while (true) {

            System.out.print(message);

            String phone = scanner.nextLine().trim();

            if (phone.matches("\\d{10}")) {
                return phone;
            }

            System.out.println(
                    "Please enter a valid 10-digit phone number."
            );
        }
    }

    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                int value = Integer.parseInt(
                        scanner.nextLine().trim()
                );

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid integer."
                );
            }
        }
    }

    private static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                double value = Double.parseDouble(
                        scanner.nextLine().trim()
                );

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}