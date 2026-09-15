import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

class Appointment {

    private int appointmentId;
    private String name;
    private String phone;
    private LocalDate date;
    private LocalTime time;
    private String purpose;

    public Appointment(int appointmentId, String name, String phone,
                       LocalDate date, LocalTime time, String purpose) {

        this.appointmentId = appointmentId;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.purpose = purpose;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void displayAppointment() {

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("-----------------------------------");
        System.out.println("Appointment ID : " + appointmentId);
        System.out.println("Name           : " + name);
        System.out.println("Phone          : " + phone);
        System.out.println("Date           : " + date.format(dateFormatter));
        System.out.println("Time           : " + time.format(timeFormatter));
        System.out.println("Purpose        : " + purpose);
        System.out.println("-----------------------------------");
    }
}

public class AppointmentSchedulerSimulator {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Appointment> appointments =
            new ArrayList<>();

    static int nextAppointmentId = 101;

    static DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    static DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n==================================");
            System.out.println("     APPOINTMENT SCHEDULER");
            System.out.println("==================================");
            System.out.println("1. Add Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. Search Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. Check Appointment Status");
            System.out.println("6. Exit");
            System.out.println("==================================");

            choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addAppointment();
                    break;

                case 2:
                    viewAppointments();
                    break;

                case 3:
                    searchAppointment();
                    break;

                case 4:
                    cancelAppointment();
                    break;

                case 5:
                    checkAppointmentStatus();
                    break;

                case 6:
                    System.out.println("\nThank you for using Appointment Scheduler!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 6);

        scanner.close();
    }

    // Add Appointment
    static void addAppointment() {

        System.out.println("\n========== ADD APPOINTMENT ==========");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine().trim();

        if (phone.isEmpty()) {
            System.out.println("Phone number cannot be empty.");
            return;
        }

        LocalDate date = readDate();

        LocalTime time = readTime();

        // Check duplicate date and time
        for (Appointment appointment : appointments) {

            if (appointment.getDate().equals(date)
                    && appointment.getTime().equals(time)) {

                System.out.println(
                        "\nThis time slot is already booked."
                );

                return;
            }
        }

        System.out.print("Enter Purpose: ");
        String purpose = scanner.nextLine().trim();

        if (purpose.isEmpty()) {
            System.out.println("Purpose cannot be empty.");
            return;
        }

        Appointment appointment =
                new Appointment(
                        nextAppointmentId,
                        name,
                        phone,
                        date,
                        time,
                        purpose
                );

        appointments.add(appointment);

        System.out.println("\nAppointment added successfully!");
        System.out.println("Appointment ID: " + nextAppointmentId);

        nextAppointmentId++;
    }

    // View Appointments
    static void viewAppointments() {

        System.out.println("\n======= ALL APPOINTMENTS =======");

        if (appointments.isEmpty()) {

            System.out.println("No appointments found.");

            return;
        }

        for (Appointment appointment : appointments) {
            appointment.displayAppointment();
        }
    }

    // Search Appointment
    static void searchAppointment() {

        System.out.println("\n======= SEARCH APPOINTMENT =======");

        System.out.print("Enter name to search: ");
        String searchName = scanner.nextLine().trim();

        boolean found = false;

        for (Appointment appointment : appointments) {

            if (appointment.getName()
                    .equalsIgnoreCase(searchName)) {

                appointment.displayAppointment();

                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointment found for " + searchName);
        }
    }

    // Cancel Appointment
    static void cancelAppointment() {

        System.out.println("\n======= CANCEL APPOINTMENT =======");

        int id = readInteger("Enter Appointment ID: ");

        boolean found = false;

        for (int i = 0; i < appointments.size(); i++) {

            if (appointments.get(i).getAppointmentId() == id) {

                appointments.remove(i);

                System.out.println(
                        "Appointment cancelled successfully."
                );

                found = true;

                break;
            }
        }

        if (!found) {
            System.out.println("Appointment ID not found.");
        }
    }

    // Check Appointment Status
    static void checkAppointmentStatus() {

        System.out.println("\n======= APPOINTMENT STATUS =======");

        int id = readInteger("Enter Appointment ID: ");

        for (Appointment appointment : appointments) {

            if (appointment.getAppointmentId() == id) {

                LocalDate today = LocalDate.now();
                LocalTime currentTime = LocalTime.now();

                LocalDate appointmentDate =
                        appointment.getDate();

                LocalTime appointmentTime =
                        appointment.getTime();

                System.out.println(
                        "\nAppointment ID: " + id
                );

                if (appointmentDate.isBefore(today)) {

                    System.out.println("Status: COMPLETED / EXPIRED");

                } else if (appointmentDate.isEqual(today)
                        && appointmentTime.isBefore(currentTime)) {

                    System.out.println("Status: COMPLETED / EXPIRED");

                } else if (appointmentDate.isEqual(today)) {

                    System.out.println("Status: TODAY");

                } else {

                    System.out.println("Status: UPCOMING");
                }

                return;
            }
        }

        System.out.println("Appointment ID not found.");
    }

    // Read Date
    static LocalDate readDate() {

        while (true) {

            System.out.print(
                    "Enter Date (DD-MM-YYYY): "
            );

            String input = scanner.nextLine();

            try {

                LocalDate date =
                        LocalDate.parse(input, dateFormatter);

                if (date.isBefore(LocalDate.now())) {

                    System.out.println(
                            "Please enter today or a future date."
                    );

                    continue;
                }

                return date;

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date format. Use DD-MM-YYYY."
                );
            }
        }
    }

    // Read Time
    static LocalTime readTime() {

        while (true) {

            System.out.print(
                    "Enter Time (HH:MM): "
            );

            String input = scanner.nextLine();

            try {

                return LocalTime.parse(input, timeFormatter);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid time format. Use HH:MM."
                );
            }
        }
    }

    // Read Integer
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
}