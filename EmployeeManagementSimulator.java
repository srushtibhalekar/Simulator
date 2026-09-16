import java.util.ArrayList;
import java.util.Scanner;

interface SalaryCalculator {
    double calculateSalary();
}

abstract class Employee implements SalaryCalculator {

    private int employeeId;
    private String name;
    private String department;

    public Employee(int employeeId, String name, String department) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public abstract String getEmployeeType();

    public void displayEmployee() {
        System.out.println("-----------------------------------");
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Name        : " + name);
        System.out.println("Department  : " + department);
        System.out.println("Type        : " + getEmployeeType());
        System.out.println("Salary      : ₹" + calculateSalary());
        System.out.println("-----------------------------------");
    }
}

class FullTimeEmployee extends Employee {

    private double monthlySalary;

    public FullTimeEmployee(int employeeId, String name,
                            String department, double monthlySalary) {

        super(employeeId, name, department);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }

    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }
}

class PartTimeEmployee extends Employee {

    private double hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int employeeId, String name,
                            String department,
                            double hoursWorked, double hourlyRate) {

        super(employeeId, name, department);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }

    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }
}

public class EmployeeManagementSimulator {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Employee> employees = new ArrayList<>();

    static int nextEmployeeId = 101;

    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n==================================");
            System.out.println("     EMPLOYEE MANAGEMENT");
            System.out.println("==================================");
            System.out.println("1. Add Full-Time Employee");
            System.out.println("2. Add Part-Time Employee");
            System.out.println("3. View All Employees");
            System.out.println("4. Search Employee");
            System.out.println("5. Remove Employee");
            System.out.println("6. Calculate Employee Salary");
            System.out.println("7. Exit");
            System.out.println("==================================");

            choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addFullTimeEmployee();
                    break;

                case 2:
                    addPartTimeEmployee();
                    break;

                case 3:
                    viewAllEmployees();
                    break;

                case 4:
                    searchEmployee();
                    break;

                case 5:
                    removeEmployee();
                    break;

                case 6:
                    calculateEmployeeSalary();
                    break;

                case 7:
                    System.out.println("\nThank you for using Employee Management!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 7);

        scanner.close();
    }

    static void addFullTimeEmployee() {

        System.out.println("\n======= ADD FULL-TIME EMPLOYEE =======");

        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter Department: ");
        String department = scanner.nextLine().trim();

        if (department.isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }

        double salary = readDouble("Enter Monthly Salary: ");

        if (salary <= 0) {
            System.out.println("Salary must be greater than 0.");
            return;
        }

        Employee employee = new FullTimeEmployee(
                nextEmployeeId,
                name,
                department,
                salary
        );

        employees.add(employee);

        System.out.println("\nFull-Time employee added successfully!");
        System.out.println("Employee ID: " + nextEmployeeId);

        nextEmployeeId++;
    }

    static void addPartTimeEmployee() {

        System.out.println("\n======= ADD PART-TIME EMPLOYEE =======");

        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter Department: ");
        String department = scanner.nextLine().trim();

        if (department.isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }

        double hoursWorked =
                readDouble("Enter Hours Worked: ");

        double hourlyRate =
                readDouble("Enter Hourly Rate: ");

        if (hoursWorked <= 0 || hourlyRate <= 0) {
            System.out.println(
                    "Hours worked and hourly rate must be greater than 0."
            );
            return;
        }

        Employee employee = new PartTimeEmployee(
                nextEmployeeId,
                name,
                department,
                hoursWorked,
                hourlyRate
        );

        employees.add(employee);

        System.out.println("\nPart-Time employee added successfully!");
        System.out.println("Employee ID: " + nextEmployeeId);

        nextEmployeeId++;
    }

    static void viewAllEmployees() {

        System.out.println("\n========== ALL EMPLOYEES ==========");

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee employee : employees) {
            employee.displayEmployee();
        }
    }

    static void searchEmployee() {

        System.out.println("\n========== SEARCH EMPLOYEE ==========");

        int id = readInteger("Enter Employee ID: ");

        for (Employee employee : employees) {

            if (employee.getEmployeeId() == id) {

                employee.displayEmployee();

                return;
            }
        }

        System.out.println("Employee ID not found.");
    }

    static void removeEmployee() {

        System.out.println("\n========== REMOVE EMPLOYEE ==========");

        int id = readInteger("Enter Employee ID: ");

        for (int i = 0; i < employees.size(); i++) {

            if (employees.get(i).getEmployeeId() == id) {

                employees.remove(i);

                System.out.println(
                        "Employee removed successfully."
                );

                return;
            }
        }

        System.out.println("Employee ID not found.");
    }

    static void calculateEmployeeSalary() {

        System.out.println("\n======= EMPLOYEE SALARY =======");

        int id = readInteger("Enter Employee ID: ");

        for (Employee employee : employees) {

            if (employee.getEmployeeId() == id) {

                System.out.println(
                        "Employee Name : " + employee.getName()
                );

                System.out.println(
                        "Employee Type : " + employee.getEmployeeType()
                );

                System.out.println(
                        "Salary        : ₹" + employee.calculateSalary()
                );

                return;
            }
        }

        System.out.println("Employee ID not found.");
    }

    static int readInteger(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    static double readDouble(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            try {

                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid amount."
                );
            }
        }
    }
}