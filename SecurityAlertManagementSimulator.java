import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SecurityAlertManagementSimulator {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static ArrayList<SecurityAlert> alerts = new ArrayList<>();

    static int nextAlertId = 1001;

    static final String[] IP_ADDRESSES = {
        "192.168.1.10",
        "192.168.1.20",
        "192.168.1.30",
        "10.0.0.15",
        "10.0.0.25",
        "203.0.113.10",
        "198.51.100.20",
        "192.0.2.50"
    };

    static final String[] USERS = {
        "admin",
        "user01",
        "developer",
        "manager",
        "analyst",
        "guest"
    };

    static final String[] ANALYSTS = {
        "Asha",
        "Rahul",
        "Neha",
        "Vikram"
    };

    static final String[] ALERT_TYPES = {
        "MULTIPLE_FAILED_LOGINS",
        "SUSPICIOUS_IP",
        "SENSITIVE_RESOURCE_ACCESS",
        "UNUSUAL_LOGIN_TIME",
        "HIGH_ACTIVITY",
        "CONFIG_CHANGE"
    };

    enum Severity {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    enum AlertStatus {
        NEW,
        INVESTIGATING,
        RESOLVED,
        CLOSED
    }

    static class SecurityAlert {

        int alertId;
        String ipAddress;
        String username;
        String alertType;
        String description;
        Severity severity;
        AlertStatus status;
        String assignedAnalyst;

        LocalDateTime createdAt;
        LocalDateTime acknowledgedAt;
        LocalDateTime resolvedAt;

        SecurityAlert(
                int alertId,
                String ipAddress,
                String username,
                String alertType,
                String description,
                Severity severity,
                LocalDateTime createdAt) {

            this.alertId = alertId;
            this.ipAddress = ipAddress;
            this.username = username;
            this.alertType = alertType;
            this.description = description;
            this.severity = severity;
            this.status = AlertStatus.NEW;
            this.assignedAnalyst = "Unassigned";
            this.createdAt = createdAt;
        }

        long getResponseTimeMinutes() {

            LocalDateTime endTime;

            if (acknowledgedAt != null) {
                endTime = acknowledgedAt;
            } else {
                endTime = LocalDateTime.now();
            }

            return Math.max(
                    0,
                    Duration.between(createdAt, endTime).toMinutes()
            );
        }

        long getResolutionTimeMinutes() {

            if (resolvedAt == null) {
                return 0;
            }

            return Math.max(
                    0,
                    Duration.between(createdAt, resolvedAt).toMinutes()
            );
        }

        @Override
        public String toString() {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            return "\n----------------------------------------"
                    + "\nAlert ID       : " + alertId
                    + "\nIP Address     : " + ipAddress
                    + "\nUsername       : " + username
                    + "\nAlert Type     : " + alertType
                    + "\nDescription    : " + description
                    + "\nSeverity       : " + severity
                    + "\nStatus         : " + status
                    + "\nAssigned To    : " + assignedAnalyst
                    + "\nCreated At     : " + createdAt.format(formatter)
                    + "\nAcknowledged   : "
                    + (acknowledgedAt == null
                    ? "Not Acknowledged"
                    : acknowledgedAt.format(formatter))
                    + "\nResolved At    : "
                    + (resolvedAt == null
                    ? "Not Resolved"
                    : resolvedAt.format(formatter))
                    + "\nResponse Time  : "
                    + getResponseTimeMinutes() + " minutes"
                    + "\nResolution Time: "
                    + getResolutionTimeMinutes() + " minutes"
                    + "\n----------------------------------------";
        }
    }

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("   SECURITY ALERT MANAGEMENT SIMULATOR");
        System.out.println("==============================================");

        while (true) {

            showMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    generateAlerts();
                    break;

                case 2:
                    viewAllAlerts();
                    break;

                case 3:
                    viewNewAlerts();
                    break;

                case 4:
                    acknowledgeAlert();
                    break;

                case 5:
                    assignAlert();
                    break;

                case 6:
                    updateAlertStatus();
                    break;

                case 7:
                    filterBySeverity();
                    break;

                case 8:
                    filterByStatus();
                    break;

                case 9:
                    searchByIP();
                    break;

                case 10:
                    showStatistics();
                    break;

                case 11:
                    calculateResponseTimes();
                    break;

                case 12:
                    sortBySeverity();
                    break;

                case 13:
                    saveReport();
                    break;

                case 14:
                    System.out.println("\nExiting Security Alert Management Simulator...");
                    System.out.println("Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    static void showMenu() {

        System.out.println("\n============== MENU ==============");
        System.out.println("1. Generate Security Alerts");
        System.out.println("2. View All Alerts");
        System.out.println("3. View New Alerts");
        System.out.println("4. Acknowledge Alert");
        System.out.println("5. Assign Alert to Analyst");
        System.out.println("6. Update Alert Status");
        System.out.println("7. Filter by Severity");
        System.out.println("8. Filter by Status");
        System.out.println("9. Search Alerts by IP");
        System.out.println("10. View Alert Statistics");
        System.out.println("11. Calculate Response Times");
        System.out.println("12. Sort Alerts by Severity");
        System.out.println("13. Save Alert Report");
        System.out.println("14. Exit");
        System.out.println("==================================");
    }

    static void generateAlerts() {

        int count = readInt("How many alerts do you want to generate? ");

        if (count <= 0) {
            System.out.println("Please enter a positive number.");
            return;
        }

        for (int i = 0; i < count; i++) {

            String ip =
                    IP_ADDRESSES[random.nextInt(IP_ADDRESSES.length)];

            String username =
                    USERS[random.nextInt(USERS.length)];

            String alertType =
                    ALERT_TYPES[random.nextInt(ALERT_TYPES.length)];

            Severity severity = generateSeverity();

            String description =
                    generateDescription(alertType);

            LocalDateTime createdAt =
                    LocalDateTime.now()
                            .minusMinutes(random.nextInt(1440));

            SecurityAlert alert =
                    new SecurityAlert(
                            nextAlertId++,
                            ip,
                            username,
                            alertType,
                            description,
                            severity,
                            createdAt
                    );

            alerts.add(alert);
        }

        System.out.println("\n" + count + " security alerts generated successfully.");
    }

    static Severity generateSeverity() {

        int value = random.nextInt(100);

        if (value < 25) {
            return Severity.LOW;
        } else if (value < 55) {
            return Severity.MEDIUM;
        } else if (value < 85) {
            return Severity.HIGH;
        } else {
            return Severity.CRITICAL;
        }
    }

    static String generateDescription(String alertType) {

        switch (alertType) {

            case "MULTIPLE_FAILED_LOGINS":
                return "Multiple failed login attempts detected.";

            case "SUSPICIOUS_IP":
                return "Activity detected from a suspicious synthetic IP.";

            case "SENSITIVE_RESOURCE_ACCESS":
                return "Sensitive resource access detected.";

            case "UNUSUAL_LOGIN_TIME":
                return "Login activity detected during an unusual time.";

            case "HIGH_ACTIVITY":
                return "Unusually high activity detected.";

            case "CONFIG_CHANGE":
                return "Important configuration change detected.";

            default:
                return "Security alert detected.";
        }
    }

    static void viewAllAlerts() {

        if (alerts.isEmpty()) {
            System.out.println("\nNo alerts available.");
            return;
        }

        System.out.println("\n========== ALL SECURITY ALERTS ==========");

        for (SecurityAlert alert : alerts) {
            System.out.println(alert);
        }
    }

    static void viewNewAlerts() {

        boolean found = false;

        System.out.println("\n========== NEW ALERTS ==========");

        for (SecurityAlert alert : alerts) {

            if (alert.status == AlertStatus.NEW) {
                System.out.println(alert);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No new alerts found.");
        }
    }

    static void acknowledgeAlert() {

        SecurityAlert alert = findAlertById();

        if (alert == null) {
            return;
        }

        if (alert.status != AlertStatus.NEW) {

            System.out.println(
                    "This alert is already acknowledged or processed."
            );

            return;
        }

        alert.acknowledgedAt = LocalDateTime.now();
        alert.status = AlertStatus.INVESTIGATING;

        System.out.println(
                "Alert " + alert.alertId + " acknowledged successfully."
        );
    }

    static void assignAlert() {

        SecurityAlert alert = findAlertById();

        if (alert == null) {
            return;
        }

        System.out.println("\nAvailable Analysts:");

        for (int i = 0; i < ANALYSTS.length; i++) {
            System.out.println((i + 1) + ". " + ANALYSTS[i]);
        }

        int choice = readInt("Select analyst: ");

        if (choice < 1 || choice > ANALYSTS.length) {
            System.out.println("Invalid analyst choice.");
            return;
        }

        alert.assignedAnalyst = ANALYSTS[choice - 1];

        System.out.println(
                "Alert assigned to " + alert.assignedAnalyst + "."
        );
    }

    static void updateAlertStatus() {

        SecurityAlert alert = findAlertById();

        if (alert == null) {
            return;
        }

        System.out.println("\nSelect New Status:");
        System.out.println("1. NEW");
        System.out.println("2. INVESTIGATING");
        System.out.println("3. RESOLVED");
        System.out.println("4. CLOSED");

        int choice = readInt("Enter status: ");

        AlertStatus newStatus;

        switch (choice) {

            case 1:
                newStatus = AlertStatus.NEW;
                break;

            case 2:
                newStatus = AlertStatus.INVESTIGATING;
                break;

            case 3:
                newStatus = AlertStatus.RESOLVED;
                break;

            case 4:
                newStatus = AlertStatus.CLOSED;
                break;

            default:
                System.out.println("Invalid status.");
                return;
        }

        if (newStatus == AlertStatus.INVESTIGATING
                && alert.acknowledgedAt == null) {

            alert.acknowledgedAt = LocalDateTime.now();
        }

        if (newStatus == AlertStatus.RESOLVED
                || newStatus == AlertStatus.CLOSED) {

            if (alert.acknowledgedAt == null) {
                alert.acknowledgedAt = LocalDateTime.now();
            }

            alert.resolvedAt = LocalDateTime.now();
        }

        alert.status = newStatus;

        System.out.println(
                "Alert status updated to " + newStatus + "."
        );
    }

    static void filterBySeverity() {

        System.out.println("\nSelect Severity:");
        System.out.println("1. LOW");
        System.out.println("2. MEDIUM");
        System.out.println("3. HIGH");
        System.out.println("4. CRITICAL");

        int choice = readInt("Enter severity: ");

        Severity severity;

        switch (choice) {

            case 1:
                severity = Severity.LOW;
                break;

            case 2:
                severity = Severity.MEDIUM;
                break;

            case 3:
                severity = Severity.HIGH;
                break;

            case 4:
                severity = Severity.CRITICAL;
                break;

            default:
                System.out.println("Invalid severity.");
                return;
        }

        boolean found = false;

        for (SecurityAlert alert : alerts) {

            if (alert.severity == severity) {
                System.out.println(alert);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No alerts found with severity " + severity);
        }
    }

    static void filterByStatus() {

        System.out.println("\nSelect Status:");
        System.out.println("1. NEW");
        System.out.println("2. INVESTIGATING");
        System.out.println("3. RESOLVED");
        System.out.println("4. CLOSED");

        int choice = readInt("Enter status: ");

        AlertStatus status;

        switch (choice) {

            case 1:
                status = AlertStatus.NEW;
                break;

            case 2:
                status = AlertStatus.INVESTIGATING;
                break;

            case 3:
                status = AlertStatus.RESOLVED;
                break;

            case 4:
                status = AlertStatus.CLOSED;
                break;

            default:
                System.out.println("Invalid status.");
                return;
        }

        boolean found = false;

        for (SecurityAlert alert : alerts) {

            if (alert.status == status) {
                System.out.println(alert);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No alerts found with status " + status);
        }
    }

    static void searchByIP() {

        String ip = readString("Enter IP address to search: ");

        boolean found = false;

        for (SecurityAlert alert : alerts) {

            if (alert.ipAddress.equalsIgnoreCase(ip)) {

                System.out.println(alert);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No alerts found for IP: " + ip);
        }
    }

    static void showStatistics() {

        HashMap<Severity, Integer> severityCount =
                new HashMap<>();

        HashMap<AlertStatus, Integer> statusCount =
                new HashMap<>();

        for (Severity severity : Severity.values()) {
            severityCount.put(severity, 0);
        }

        for (AlertStatus status : AlertStatus.values()) {
            statusCount.put(status, 0);
        }

        for (SecurityAlert alert : alerts) {

            severityCount.put(
                    alert.severity,
                    severityCount.get(alert.severity) + 1
            );

            statusCount.put(
                    alert.status,
                    statusCount.get(alert.status) + 1
            );
        }

        System.out.println("\n========== ALERT STATISTICS ==========");

        System.out.println("\nTotal Alerts: " + alerts.size());

        System.out.println("\nBy Severity:");

        for (Severity severity : Severity.values()) {

            System.out.println(
                    severity + ": "
                            + severityCount.get(severity)
            );
        }

        System.out.println("\nBy Status:");

        for (AlertStatus status : AlertStatus.values()) {

            System.out.println(
                    status + ": "
                            + statusCount.get(status)
            );
        }
    }

    static void calculateResponseTimes() {

        if (alerts.isEmpty()) {
            System.out.println("\nNo alerts available.");
            return;
        }

        long totalResponseTime = 0;
        int acknowledgedCount = 0;

        long totalResolutionTime = 0;
        int resolvedCount = 0;

        for (SecurityAlert alert : alerts) {

            if (alert.acknowledgedAt != null) {

                totalResponseTime +=
                        Duration.between(
                                alert.createdAt,
                                alert.acknowledgedAt
                        ).toMinutes();

                acknowledgedCount++;
            }

            if (alert.resolvedAt != null) {

                totalResolutionTime +=
                        Duration.between(
                                alert.createdAt,
                                alert.resolvedAt
                        ).toMinutes();

                resolvedCount++;
            }
        }

        System.out.println("\n========== RESPONSE TIME ==========");

        if (acknowledgedCount > 0) {

            double averageResponse =
                    (double) totalResponseTime / acknowledgedCount;

            System.out.printf(
                    "Average Response Time: %.2f minutes%n",
                    averageResponse
            );

        } else {

            System.out.println(
                    "No alerts have been acknowledged yet."
            );
        }

        if (resolvedCount > 0) {

            double averageResolution =
                    (double) totalResolutionTime / resolvedCount;

            System.out.printf(
                    "Average Resolution Time: %.2f minutes%n",
                    averageResolution
            );

        } else {

            System.out.println(
                    "No alerts have been resolved yet."
            );
        }
    }

    static void sortBySeverity() {

        if (alerts.isEmpty()) {
            System.out.println("\nNo alerts available.");
            return;
        }

        Collections.sort(
                alerts,
                new Comparator<SecurityAlert>() {

                    @Override
                    public int compare(
                            SecurityAlert a,
                            SecurityAlert b) {

                        return b.severity.compareTo(a.severity);
                    }
                }
        );

        System.out.println(
                "\nAlerts sorted by severity."
        );

        viewAllAlerts();
    }

    static void saveReport() {

        if (alerts.isEmpty()) {

            System.out.println(
                    "\nNo alerts available to save."
            );

            return;
        }

        try (FileWriter writer =
                     new FileWriter(
                             "SecurityAlertManagementReport.txt")) {

            writer.write(
                    "SECURITY ALERT MANAGEMENT REPORT\n"
            );

            writer.write(
                    "====================================\n\n"
            );

            writer.write(
                    "Total Alerts: "
                            + alerts.size()
                            + "\n\n"
            );

            for (SecurityAlert alert : alerts) {

                writer.write(
                        alert.toString()
                                + "\n"
                );
            }

            writer.write(
                    "\n====================================\n"
            );

            writer.write(
                    "Report generated at: "
                            + LocalDateTime.now()
                            + "\n"
            );

            System.out.println(
                    "\nReport saved successfully."
            );

            System.out.println(
                    "File: SecurityAlertManagementReport.txt"
            );

        } catch (IOException e) {

            System.out.println(
                    "Error while saving report: "
                            + e.getMessage()
            );
        }
    }

    static SecurityAlert findAlertById() {

        int id = readInt("Enter Alert ID: ");

        for (SecurityAlert alert : alerts) {

            if (alert.alertId == id) {
                return alert;
            }
        }

        System.out.println(
                "Alert with ID " + id + " not found."
        );

        return null;
    }

    static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                int value =
                        Integer.parseInt(
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

    static String readString(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "Input cannot be empty."
            );
        }
    }
}