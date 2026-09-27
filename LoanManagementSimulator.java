import java.util.ArrayList;
import java.util.Scanner;

class Customer {
    private int customerId;
    private String name;
    private String phone;

    public Customer(int customerId, String name, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void displayCustomer() {
        System.out.println("Customer ID : " + customerId);
        System.out.println("Name        : " + name);
        System.out.println("Phone       : " + phone);
    }
}

class Loan {
    private int loanId;
    private int customerId;
    private double principalAmount;
    private double interestRate;
    private int tenureMonths;
    private double outstandingAmount;
    private String status;

    public Loan(int loanId, int customerId, double principalAmount,
                double interestRate, int tenureMonths) {

        this.loanId = loanId;
        this.customerId = customerId;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.outstandingAmount = calculateTotalPayable();
        this.status = "ACTIVE";
    }

    public int getLoanId() {
        return loanId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public double getOutstandingAmount() {
        return outstandingAmount;
    }

    public String getStatus() {
        return status;
    }

    public double calculateTotalPayable() {
        double interest = principalAmount * interestRate * tenureMonths / (12 * 100);
        return principalAmount + interest;
    }

    public double calculateEMI() {
        double monthlyRate = interestRate / (12 * 100);

        if (monthlyRate == 0) {
            return principalAmount / tenureMonths;
        }

        double emi = principalAmount * monthlyRate
                * Math.pow(1 + monthlyRate, tenureMonths)
                / (Math.pow(1 + monthlyRate, tenureMonths) - 1);

        return emi;
    }

    public void makePayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return;
        }

        if (status.equals("CLOSED")) {
            System.out.println("Loan is already closed.");
            return;
        }

        if (amount > outstandingAmount) {
            amount = outstandingAmount;
        }

        outstandingAmount -= amount;

        if (outstandingAmount <= 0.01) {
            outstandingAmount = 0;
            status = "CLOSED";
        }

        System.out.printf("Payment successful: ₹%.2f%n", amount);
        System.out.printf("Remaining balance: ₹%.2f%n", outstandingAmount);
    }

    public void displayLoan() {
        System.out.println("--------------------------------");
        System.out.println("Loan ID           : " + loanId);
        System.out.println("Customer ID       : " + customerId);
        System.out.printf("Principal Amount  : ₹%.2f%n", principalAmount);
        System.out.printf("Interest Rate     : %.2f%%%n", interestRate);
        System.out.println("Tenure            : " + tenureMonths + " months");
        System.out.printf("Total Payable     : ₹%.2f%n", calculateTotalPayable());
        System.out.printf("Monthly EMI       : ₹%.2f%n", calculateEMI());
        System.out.printf("Outstanding       : ₹%.2f%n", outstandingAmount);
        System.out.println("Status            : " + status);
    }
}

public class LoanManagementSimulator {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Loan> loans = new ArrayList<>();

    static int nextCustomerId = 1001;
    static int nextLoanId = 5001;

    public static void main(String[] args) {

        int choice;

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addCustomer();
                    break;

                case 2:
                    viewCustomers();
                    break;

                case 3:
                    searchCustomer();
                    break;

                case 4:
                    applyLoan();
                    break;

                case 5:
                    viewLoans();
                    break;

                case 6:
                    searchLoan();
                    break;

                case 7:
                    calculateLoanEMI();
                    break;

                case 8:
                    makeLoanPayment();
                    break;

                case 9:
                    updateLoanStatus();
                    break;

                case 10:
                    removeLoan();
                    break;

                case 11:
                    System.out.println("Thank you for using Loan Management Simulator.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 11);

        scanner.close();
    }

    static void displayMenu() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("      LOAN MANAGEMENT SIMULATOR");
        System.out.println("======================================");
        System.out.println("1. Add Customer");
        System.out.println("2. View All Customers");
        System.out.println("3. Search Customer by ID");
        System.out.println("4. Apply for Loan");
        System.out.println("5. View All Loans");
        System.out.println("6. Search Loan by ID");
        System.out.println("7. Calculate Loan EMI");
        System.out.println("8. Make Loan Payment");
        System.out.println("9. Update Loan Status");
        System.out.println("10. Remove Loan");
        System.out.println("11. Exit");
        System.out.println("======================================");
    }

    static void addCustomer() {

        System.out.println("\n--- Add Customer ---");

        String name = readString("Enter customer name: ");
        String phone = readPhone();

        Customer customer = new Customer(nextCustomerId, name, phone);

        customers.add(customer);

        System.out.println("Customer added successfully.");
        System.out.println("Customer ID: " + nextCustomerId);

        nextCustomerId++;
    }

    static void viewCustomers() {

        System.out.println("\n--- All Customers ---");

        if (customers.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }

        for (Customer customer : customers) {
            customer.displayCustomer();
            System.out.println("--------------------------------");
        }
    }

    static void searchCustomer() {

        System.out.println("\n--- Search Customer ---");

        int id = readInt("Enter customer ID: ");

        Customer customer = findCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        customer.displayCustomer();

        System.out.println("\nCustomer Loans:");

        boolean foundLoan = false;

        for (Loan loan : loans) {

            if (loan.getCustomerId() == id) {
                loan.displayLoan();
                foundLoan = true;
            }
        }

        if (!foundLoan) {
            System.out.println("No loans found for this customer.");
        }
    }

    static void applyLoan() {

        System.out.println("\n--- Apply for Loan ---");

        if (customers.isEmpty()) {
            System.out.println("Please add a customer first.");
            return;
        }

        int customerId = readInt("Enter customer ID: ");

        Customer customer = findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        double principalAmount;

        do {
            principalAmount = readDouble("Enter loan amount: ");

            if (principalAmount <= 0) {
                System.out.println("Loan amount must be greater than zero.");
            }

        } while (principalAmount <= 0);

        double interestRate;

        do {
            interestRate = readDouble("Enter annual interest rate (%): ");

            if (interestRate < 0) {
                System.out.println("Interest rate cannot be negative.");
            }

        } while (interestRate < 0);

        int tenureMonths;

        do {
            tenureMonths = readInt("Enter tenure in months: ");

            if (tenureMonths <= 0) {
                System.out.println("Tenure must be greater than zero.");
            }

        } while (tenureMonths <= 0);

        Loan loan = new Loan(
                nextLoanId,
                customerId,
                principalAmount,
                interestRate,
                tenureMonths
        );

        loans.add(loan);

        System.out.println("\nLoan approved successfully.");
        System.out.println("Loan ID: " + nextLoanId);

        System.out.printf("Monthly EMI: ₹%.2f%n", loan.calculateEMI());

        nextLoanId++;
    }

    static void viewLoans() {

        System.out.println("\n--- All Loans ---");

        if (loans.isEmpty()) {
            System.out.println("No loans available.");
            return;
        }

        for (Loan loan : loans) {
            loan.displayLoan();
        }
    }

    static void searchLoan() {

        System.out.println("\n--- Search Loan ---");

        int loanId = readInt("Enter loan ID: ");

        Loan loan = findLoanById(loanId);

        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        loan.displayLoan();

        Customer customer = findCustomerById(loan.getCustomerId());

        if (customer != null) {
            System.out.println("\nCustomer Details:");
            customer.displayCustomer();
        }
    }

    static void calculateLoanEMI() {

        System.out.println("\n--- Calculate Loan EMI ---");

        int loanId = readInt("Enter loan ID: ");

        Loan loan = findLoanById(loanId);

        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        System.out.printf("Loan Amount : ₹%.2f%n", loan.getPrincipalAmount());
        System.out.printf("Interest    : %.2f%%%n", loan.getInterestRate());
        System.out.println("Tenure      : " + loan.getTenureMonths() + " months");
        System.out.printf("Monthly EMI : ₹%.2f%n", loan.calculateEMI());
    }

    static void makeLoanPayment() {

        System.out.println("\n--- Make Loan Payment ---");

        int loanId = readInt("Enter loan ID: ");

        Loan loan = findLoanById(loanId);

        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        System.out.printf("Outstanding Balance: ₹%.2f%n",
                loan.getOutstandingAmount());

        double amount = readDouble("Enter payment amount: ");

        loan.makePayment(amount);
    }

    static void updateLoanStatus() {

        System.out.println("\n--- Update Loan Status ---");

        int loanId = readInt("Enter loan ID: ");

        Loan loan = findLoanById(loanId);

        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        System.out.println("Current Status: " + loan.getStatus());

        System.out.println("\n1. Mark as ACTIVE");
        System.out.println("2. Mark as CLOSED");

        int choice = readInt("Enter choice: ");

        if (choice == 1) {

            System.out.println(
                    "Status update option is available through payment completion."
            );

        } else if (choice == 2) {

            if (loan.getOutstandingAmount() == 0) {
                System.out.println("Loan is already closed.");
            } else {
                System.out.println(
                        "Loan cannot be manually closed while balance is outstanding."
                );
            }

        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void removeLoan() {

        System.out.println("\n--- Remove Loan ---");

        int loanId = readInt("Enter loan ID: ");

        Loan loan = findLoanById(loanId);

        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        loans.remove(loan);

        System.out.println("Loan removed successfully.");
    }

    static Customer findCustomerById(int id) {

        for (Customer customer : customers) {

            if (customer.getCustomerId() == id) {
                return customer;
            }
        }

        return null;
    }

    static Loan findLoanById(int id) {

        for (Loan loan : loans) {

            if (loan.getLoanId() == id) {
                return loan;
            }
        }

        return null;
    }

    static String readString(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    static String readPhone() {

        while (true) {

            System.out.print("Enter phone number: ");

            String phone = scanner.nextLine().trim();

            if (phone.matches("\\d{10}")) {
                return phone;
            }

            System.out.println("Please enter a valid 10-digit phone number.");
        }
    }

    static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(scanner.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid amount.");
            }
        }
    }
}