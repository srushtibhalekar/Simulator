import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class LogThreatDetectionSimulator {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    enum ThreatLevel {
        NORMAL,
        WARNING,
        HIGH,
        CRITICAL
    }

    static class LogEntry {

        int logId;
        String ipAddress;
        String username;
        String action;
        String resource;
        boolean successful;
        LocalDateTime timestamp;
        ThreatLevel threatLevel;

        LogEntry(
                int logId,
                String ipAddress,
                String username,
                String action,
                String resource,
                boolean successful,
                LocalDateTime timestamp) {

            this.logId = logId;
            this.ipAddress = ipAddress;
            this.username = username;
            this.action = action;
            this.resource = resource;
            this.successful = successful;
            this.timestamp = timestamp;
            this.threatLevel = ThreatLevel.NORMAL;
        }

        void display() {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            System.out.println("--------------------------------------------");
            System.out.println("Log ID       : " + logId);
            System.out.println("IP Address   : " + ipAddress);
            System.out.println("Username     : " + username);
            System.out.println("Action       : " + action);
            System.out.println("Resource     : " + resource);
            System.out.println("Result       : "
                    + (successful ? "SUCCESS" : "FAILED"));
            System.out.println("Timestamp    : "
                    + timestamp.format(formatter));
            System.out.println("Threat Level : " + threatLevel);
            System.out.println("--------------------------------------------");
        }
    }

    static ArrayList<LogEntry> logs = new ArrayList<>();

    static HashMap<String, Integer> failedAttempts =
            new HashMap<>();

    static HashMap<String, Integer> ipActivity =
            new HashMap<>();

    static HashSet<String> flaggedIPs =
            new HashSet<>();

    static int nextLogId = 1001;

    static String[] ipAddresses = {
            "192.168.1.10",
            "192.168.1.20",
            "192.168.1.30",
            "10.0.0.15",
            "10.0.0.25",
            "203.0.113.10",
            "198.51.100.20",
            "192.0.2.50"
    };

    static String[] usernames = {
            "admin",
            "user01",
            "developer",
            "manager",
            "analyst",
            "guest"
    };

    static String[] actions = {
            "LOGIN",
            "LOGOUT",
            "READ",
            "DOWNLOAD",
            "UPDATE",
            "DELETE"
    };

    static String[] resources = {
            "/dashboard",
            "/profile",
            "/reports",
            "/database",
            "/admin",
            "/users",
            "/backup",
            "/config"
    };

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("       LOG THREAT DETECTION SIMULATOR");
        System.out.println("==============================================");
        System.out.println("Synthetic server-log analysis environment");
        System.out.println("No real system logs are accessed.");

        boolean running = true;

        while (running) {

            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    generateLogs();
                    break;

                case 2:
                    viewAllLogs();
                    break;

                case 3:
                    analyzeLogs();
                    break;

                case 4:
                    detectFailedLoginPatterns();
                    break;

                case 5:
                    detectSuspiciousIPs();
                    break;

                case 6:
                    detectSensitiveResourceAccess();
                    break;

                case 7:
                    filterByThreatLevel();
                    break;

                case 8:
                    searchByIP();
                    break;

                case 9:
                    displayStatistics();
                    break;

                case 10:
                    sortLogsByTime();
                    break;

                case 11:
                    saveReport();
                    break;

                case 12:
                    System.out.println("\nExiting Log Threat Detection Simulator...");
                    System.out.println("Total logs analyzed: "
                            + logs.size());
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }

        scanner.close();
    }

    static void displayMenu() {

        System.out.println("\n============== LOG ANALYZER ==============");
        System.out.println("1. Generate Server Logs");
        System.out.println("2. View All Logs");
        System.out.println("3. Analyze Logs");
        System.out.println("4. Detect Failed Login Patterns");
        System.out.println("5. Detect Suspicious IPs");
        System.out.println("6. Detect Sensitive Resource Access");
        System.out.println("7. Filter by Threat Level");
        System.out.println("8. Search Logs by IP");
        System.out.println("9. Threat Statistics");
        System.out.println("10. Sort Logs by Time");
        System.out.println("11. Save Security Report");
        System.out.println("12. Exit");
        System.out.println("============================================");
    }

    static void generateLogs() {

        int count = readInt(
                "How many synthetic logs should be generated? "
        );

        if (count <= 0) {
            System.out.println(
                    "Log count must be greater than zero."
            );
            return;
        }

        if (count > 100) {
            System.out.println(
                    "Maximum 100 logs can be generated at once."
            );
            return;
        }

        System.out.println("\nGenerating synthetic server logs...");

        for (int i = 0; i < count; i++) {

            String ip =
                    ipAddresses[
                            random.nextInt(ipAddresses.length)
                    ];

            String username =
                    usernames[
                            random.nextInt(usernames.length)
                    ];

            String action =
                    actions[
                            random.nextInt(actions.length)
                    ];

            String resource =
                    resources[
                            random.nextInt(resources.length)
                    ];

            boolean successful =
                    random.nextInt(100) >= 25;

            LocalDateTime timestamp =
                    generateTimestamp();

            LogEntry log =
                    new LogEntry(
                            nextLogId++,
                            ip,
                            username,
                            action,
                            resource,
                            successful,
                            timestamp
                    );

            logs.add(log);

            updateStatistics(log);
        }

        System.out.println(
                "✅ " + count
                        + " synthetic logs generated successfully."
        );
    }

    static LocalDateTime generateTimestamp() {

        LocalDateTime now = LocalDateTime.now();

        int daysAgo = random.nextInt(3);

        int hour;

        /*
         * Some logs intentionally use unusual hours
         * so that the analysis feature can detect them.
         */
        if (random.nextInt(100) < 20) {
            hour = random.nextInt(5);
        } else {
            hour = 7 + random.nextInt(15);
        }

        int minute = random.nextInt(60);
        int second = random.nextInt(60);

        return now
                .minusDays(daysAgo)
                .withHour(hour)
                .withMinute(minute)
                .withSecond(second);
    }

    static void updateStatistics(LogEntry log) {

        ipActivity.put(
                log.ipAddress,
                ipActivity.getOrDefault(
                        log.ipAddress,
                        0
                ) + 1
        );

        if (!log.successful &&
                log.action.equals("LOGIN")) {

            failedAttempts.put(
                    log.ipAddress,
                    failedAttempts.getOrDefault(
                            log.ipAddress,
                            0
                    ) + 1
            );
        }
    }

    static void viewAllLogs() {

        if (logs.isEmpty()) {
            System.out.println("\nNo logs available.");
            return;
        }

        System.out.println("\n=========== SERVER LOGS ===========");

        for (LogEntry log : logs) {
            log.display();
        }
    }

    static void analyzeLogs() {

        if (logs.isEmpty()) {
            System.out.println(
                    "\nNo logs available for analysis."
            );
            return;
        }

        /*
         * Clear old flags before performing a fresh analysis.
         */
        flaggedIPs.clear();

        for (LogEntry log : logs) {

            log.threatLevel = ThreatLevel.NORMAL;

            int failed =
                    failedAttempts.getOrDefault(
                            log.ipAddress,
                            0
                    );

            int activity =
                    ipActivity.getOrDefault(
                            log.ipAddress,
                            0
                    );

            boolean unusualTime =
                    isUnusualTime(log.timestamp);

            boolean sensitiveResource =
                    log.resource.equals("/admin")
                            || log.resource.equals("/database")
                            || log.resource.equals("/backup")
                            || log.resource.equals("/config");

            /*
             * CRITICAL conditions
             */
            if (failed >= 5 && unusualTime) {

                log.threatLevel =
                        ThreatLevel.CRITICAL;

                flaggedIPs.add(log.ipAddress);
            }

            /*
             * HIGH conditions
             */
            else if (failed >= 4
                    || (sensitiveResource
                    && !log.successful)
                    || activity >= 10) {

                log.threatLevel =
                        ThreatLevel.HIGH;

                flaggedIPs.add(log.ipAddress);
            }

            /*
             * WARNING conditions
             */
            else if (!log.successful
                    || unusualTime
                    || sensitiveResource) {

                log.threatLevel =
                        ThreatLevel.WARNING;
            }
        }

        System.out.println(
                "\n✅ Log analysis completed."
        );

        displayStatistics();
    }

    static boolean isUnusualTime(
            LocalDateTime timestamp) {

        LocalTime time =
                timestamp.toLocalTime();

        /*
         * Consider 12 AM - 5 AM as unusual.
         */
        return time.isBefore(
                LocalTime.of(5, 0)
        );
    }

    static void detectFailedLoginPatterns() {

        if (logs.isEmpty()) {
            System.out.println(
                    "\nNo logs available."
            );
            return;
        }

        System.out.println(
                "\n======= FAILED LOGIN PATTERNS ======="
        );

        boolean found = false;

        for (String ip : failedAttempts.keySet()) {

            int attempts =
                    failedAttempts.get(ip);

            if (attempts >= 3) {

                System.out.println(
                        "⚠️ IP: "
                                + ip
                                + " | Failed Login Attempts: "
                                + attempts
                );

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No repeated failed-login pattern detected."
            );
        }
    }

    static void detectSuspiciousIPs() {

        if (logs.isEmpty()) {
            System.out.println(
                    "\nNo logs available."
            );
            return;
        }

        /*
         * Ensure analysis has been performed.
         */
        analyzeLogs();

        System.out.println(
                "\n========== FLAGGED IPs =========="
        );

        if (flaggedIPs.isEmpty()) {

            System.out.println(
                    "No suspicious IPs detected."
            );

            return;
        }

        for (String ip : flaggedIPs) {

            System.out.println(
                    "⚠️ Suspicious IP: " + ip
            );
        }

        System.out.println(
                "Total flagged IPs: "
                        + flaggedIPs.size()
        );
    }

    static void detectSensitiveResourceAccess() {

        if (logs.isEmpty()) {
            System.out.println(
                    "\nNo logs available."
            );
            return;
        }

        System.out.println(
                "\n====== SENSITIVE RESOURCE ACCESS ======"
        );

        boolean found = false;

        for (LogEntry log : logs) {

            boolean sensitive =
                    log.resource.equals("/admin")
                            || log.resource.equals("/database")
                            || log.resource.equals("/backup")
                            || log.resource.equals("/config");

            if (sensitive) {

                log.display();
                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No sensitive resource access found."
            );
        }
    }

    static void filterByThreatLevel() {

        if (logs.isEmpty()) {
            System.out.println(
                    "\nNo logs available."
            );
            return;
        }

        System.out.println(
                "\nSelect Threat Level:"
        );

        System.out.println("1. NORMAL");
        System.out.println("2. WARNING");
        System.out.println("3. HIGH");
        System.out.println("4. CRITICAL");

        int choice =
                readInt("Enter choice: ");

        ThreatLevel selected;

        switch (choice) {

            case 1:
                selected = ThreatLevel.NORMAL;
                break;

            case 2:
                selected = ThreatLevel.WARNING;
                break;

            case 3:
                selected = ThreatLevel.HIGH;
                break;

            case 4:
                selected = ThreatLevel.CRITICAL;
                break;

            default:
                System.out.println(
                        "Invalid threat level."
                );
                return;
        }

        boolean found = false;

        System.out.println(
                "\n====== " + selected + " LOGS ======"
        );

        for (LogEntry log : logs) {

            if (log.threatLevel == selected) {

                log.display();
                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No logs found for "
                            + selected
                            + " level."
            );
        }
    }

    static void searchByIP() {

        if (logs.isEmpty()) {
            System.out.println(
                    "\nNo logs available."
            );
            return;
        }

        System.out.print(
                "Enter IP address to search: "
        );

        String ip =
                scanner.nextLine().trim();

        boolean found = false;

        System.out.println(
                "\n========== SEARCH RESULTS =========="
        );

        for (LogEntry log : logs) {

            if (log.ipAddress.equals(ip)) {

                log.display();
                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No logs found for IP: "
                            + ip
            );
        }
    }

    static void displayStatistics() {

        if (logs.isEmpty()) {
            System.out.println(
                    "\nNo statistics available."
            );
            return;
        }

        int normal = 0;
        int warning = 0;
        int high = 0;
        int critical = 0;

        int successful = 0;
        int failed = 0;

        for (LogEntry log : logs) {

            switch (log.threatLevel) {

                case NORMAL:
                    normal++;
                    break;

                case WARNING:
                    warning++;
                    break;

                case HIGH:
                    high++;
                    break;

                case CRITICAL:
                    critical++;
                    break;
            }

            if (log.successful) {
                successful++;
            } else {
                failed++;
            }
        }

        System.out.println(
                "\n========== THREAT STATISTICS =========="
        );

        System.out.println(
                "Total Logs       : " + logs.size()
        );

        System.out.println(
                "Successful Events: " + successful
        );

        System.out.println(
                "Failed Events    : " + failed
        );

        System.out.println("----------------------------------------");

        System.out.println(
                "NORMAL           : " + normal
        );

        System.out.println(
                "WARNING          : " + warning
        );

        System.out.println(
                "HIGH             : " + high
        );

        System.out.println(
                "CRITICAL         : " + critical
        );

        System.out.println("----------------------------------------");

        System.out.println(
                "Unique IPs       : "
                        + ipActivity.size()
        );

        System.out.println(
                "Flagged IPs      : "
                        + flaggedIPs.size()
        );
    }

    static void sortLogsByTime() {

        if (logs.isEmpty()) {
            System.out.println(
                    "\nNo logs available."
            );
            return;
        }

        ArrayList<LogEntry> sortedLogs =
                new ArrayList<>(logs);

        Collections.sort(
                sortedLogs,
                Comparator.comparing(
                        log -> log.timestamp
                )
        );

        System.out.println(
                "\n====== LOGS SORTED BY TIME ======"
        );

        for (LogEntry log : sortedLogs) {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy HH:mm:ss"
                    );

            System.out.println(
                    "Log "
                            + log.logId
                            + " | "
                            + log.timestamp.format(formatter)
                            + " | "
                            + log.ipAddress
                            + " | "
                            + log.threatLevel
            );
        }
    }

    static void saveReport() {

        if (logs.isEmpty()) {

            System.out.println(
                    "\nNo logs available to save."
            );

            return;
        }

        /*
         * Analyze before creating the report
         * so threat levels are current.
         */
        analyzeLogs();

        String fileName =
                "LogThreatDetectionReport.txt";

        try (FileWriter writer =
                     new FileWriter(fileName)) {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy HH:mm:ss"
                    );

            writer.write(
                    "LOG THREAT DETECTION REPORT\n"
            );

            writer.write(
                    "========================================\n\n"
            );

            writer.write(
                    "Generated At: "
                            + LocalDateTime.now()
                            .format(formatter)
                            + "\n\n"
            );

            writer.write(
                    "Total Logs: "
                            + logs.size()
                            + "\n"
            );

            writer.write(
                    "Unique IPs: "
                            + ipActivity.size()
                            + "\n"
            );

            writer.write(
                    "Flagged IPs: "
                            + flaggedIPs.size()
                            + "\n\n"
            );

            writer.write(
                    "LOG DETAILS\n"
            );

            writer.write(
                    "----------------------------------------\n"
            );

            for (LogEntry log : logs) {

                writer.write(
                        "Log ID: "
                                + log.logId
                                + "\n"
                );

                writer.write(
                        "IP Address: "
                                + log.ipAddress
                                + "\n"
                );

                writer.write(
                        "Username: "
                                + log.username
                                + "\n"
                );

                writer.write(
                        "Action: "
                                + log.action
                                + "\n"
                );

                writer.write(
                        "Resource: "
                                + log.resource
                                + "\n"
                );

                writer.write(
                        "Result: "
                                + (log.successful
                                ? "SUCCESS"
                                : "FAILED")
                                + "\n"
                );

                writer.write(
                        "Timestamp: "
                                + log.timestamp
                                .format(formatter)
                                + "\n"
                );

                writer.write(
                        "Threat Level: "
                                + log.threatLevel
                                + "\n"
                );

                writer.write(
                        "----------------------------------------\n"
                );
            }

            writer.write(
                    "\nFLAGGED IP ADDRESSES\n"
            );

            writer.write(
                    "----------------------------------------\n"
            );

            for (String ip : flaggedIPs) {

                writer.write(ip + "\n");
            }

            System.out.println(
                    "\n✅ Security report saved successfully."
            );

            System.out.println(
                    "File: " + fileName
            );

        } catch (IOException e) {

            System.out.println(
                    "Error while saving report."
            );

            System.out.println(
                    "Reason: " + e.getMessage()
            );
        }
    }

    static int readInt(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}