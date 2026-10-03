import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class SecurityEventCorrelationSimulator {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    enum ThreatLevel {
        NORMAL,
        WARNING,
        HIGH,
        CRITICAL
    }

    static class SecurityEvent {

        int eventId;
        String ipAddress;
        String username;
        String eventType;
        String resource;
        boolean successful;
        LocalDateTime timestamp;
        ThreatLevel threatLevel;

        SecurityEvent(
                int eventId,
                String ipAddress,
                String username,
                String eventType,
                String resource,
                boolean successful,
                LocalDateTime timestamp) {

            this.eventId = eventId;
            this.ipAddress = ipAddress;
            this.username = username;
            this.eventType = eventType;
            this.resource = resource;
            this.successful = successful;
            this.timestamp = timestamp;
            this.threatLevel = ThreatLevel.NORMAL;
        }

        void display() {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy HH:mm:ss"
                    );

            System.out.println("--------------------------------------------");
            System.out.println("Event ID     : " + eventId);
            System.out.println("IP Address   : " + ipAddress);
            System.out.println("Username     : " + username);
            System.out.println("Event Type   : " + eventType);
            System.out.println("Resource     : " + resource);
            System.out.println("Result       : "
                    + (successful ? "SUCCESS" : "FAILED"));
            System.out.println("Timestamp    : "
                    + timestamp.format(formatter));
            System.out.println("Threat Level : " + threatLevel);
            System.out.println("--------------------------------------------");
        }
    }

    static ArrayList<SecurityEvent> events =
            new ArrayList<>();

    static HashMap<String, Integer> failedLoginCount =
            new HashMap<>();

    static HashMap<String, Integer> ipEventCount =
            new HashMap<>();

    static HashMap<String, HashSet<String>> ipUsers =
            new HashMap<>();

    static HashSet<String> correlatedIPs =
            new HashSet<>();

    static int nextEventId = 1001;

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

    static String[] eventTypes = {
            "LOGIN",
            "LOGOUT",
            "FILE_ACCESS",
            "DATABASE_ACCESS",
            "PASSWORD_CHANGE",
            "CONFIG_ACCESS"
    };

    static String[] resources = {
            "/dashboard",
            "/profile",
            "/reports",
            "/admin",
            "/database",
            "/backup",
            "/config",
            "/users"
    };

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("     SECURITY EVENT CORRELATION SIMULATOR");
        System.out.println("==============================================");
        System.out.println("Synthetic security event environment");
        System.out.println("No real systems or networks are accessed.");

        boolean running = true;

        while (running) {

            displayMenu();

            int choice = readInt(
                    "Enter your choice: "
            );

            switch (choice) {

                case 1:
                    generateEvents();
                    break;

                case 2:
                    viewAllEvents();
                    break;

                case 3:
                    analyzeEvents();
                    break;

                case 4:
                    detectRepeatedFailures();
                    break;

                case 5:
                    detectMultiUserIPs();
                    break;

                case 6:
                    detectSensitiveAccess();
                    break;

                case 7:
                    correlateEvents();
                    break;

                case 8:
                    viewCorrelatedIncidents();
                    break;

                case 9:
                    searchByIP();
                    break;

                case 10:
                    filterByThreatLevel();
                    break;

                case 11:
                    displayStatistics();
                    break;

                case 12:
                    sortEventsByTime();
                    break;

                case 13:
                    saveReport();
                    break;

                case 14:
                    System.out.println(
                            "\nExiting Security Event Correlation Simulator..."
                    );

                    System.out.println(
                            "Total events: " + events.size()
                    );

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

        System.out.println("\n============= SECURITY ANALYZER =============");
        System.out.println("1. Generate Security Events");
        System.out.println("2. View All Events");
        System.out.println("3. Analyze Events");
        System.out.println("4. Detect Repeated Failed Logins");
        System.out.println("5. Detect Multi-User IP Activity");
        System.out.println("6. Detect Sensitive Resource Access");
        System.out.println("7. Correlate Security Events");
        System.out.println("8. View Correlated Incidents");
        System.out.println("9. Search Events by IP");
        System.out.println("10. Filter by Threat Level");
        System.out.println("11. Security Statistics");
        System.out.println("12. Sort Events by Time");
        System.out.println("13. Save Incident Report");
        System.out.println("14. Exit");
        System.out.println("==============================================");
    }

    static void generateEvents() {

        int count = readInt(
                "How many synthetic events should be generated? "
        );

        if (count <= 0) {

            System.out.println(
                    "Event count must be greater than zero."
            );

            return;
        }

        if (count > 100) {

            System.out.println(
                    "Maximum 100 events can be generated at once."
            );

            return;
        }

        System.out.println(
                "\nGenerating synthetic security events..."
        );

        for (int i = 0; i < count; i++) {

            String ip =
                    ipAddresses[
                            random.nextInt(
                                    ipAddresses.length
                            )
                    ];

            String username =
                    usernames[
                            random.nextInt(
                                    usernames.length
                            )
                    ];

            String eventType =
                    eventTypes[
                            random.nextInt(
                                    eventTypes.length
                            )
                    ];

            String resource =
                    resources[
                            random.nextInt(
                                    resources.length
                            )
                    ];

            boolean successful;

            /*
             * LOGIN events have a higher chance of failure
             * so the correlation logic can find patterns.
             */
            if (eventType.equals("LOGIN")) {

                successful =
                        random.nextInt(100) >= 35;

            } else {

                successful =
                        random.nextInt(100) >= 15;
            }

            LocalDateTime timestamp =
                    generateTimestamp();

            SecurityEvent event =
                    new SecurityEvent(
                            nextEventId++,
                            ip,
                            username,
                            eventType,
                            resource,
                            successful,
                            timestamp
                    );

            events.add(event);

            updateStatistics(event);
        }

        System.out.println(
                "✅ " + count
                        + " synthetic security events generated."
        );
    }

    static LocalDateTime generateTimestamp() {

        LocalDateTime now =
                LocalDateTime.now();

        int daysAgo =
                random.nextInt(3);

        int hour;

        /*
         * Some events are intentionally generated
         * during unusual hours.
         */
        if (random.nextInt(100) < 20) {

            hour =
                    random.nextInt(5);

        } else {

            hour =
                    7 + random.nextInt(15);
        }

        int minute =
                random.nextInt(60);

        int second =
                random.nextInt(60);

        return now
                .minusDays(daysAgo)
                .withHour(hour)
                .withMinute(minute)
                .withSecond(second);
    }

    static void updateStatistics(
            SecurityEvent event) {

        ipEventCount.put(
                event.ipAddress,
                ipEventCount.getOrDefault(
                        event.ipAddress,
                        0
                ) + 1
        );

        if (event.eventType.equals("LOGIN")
                && !event.successful) {

            failedLoginCount.put(
                    event.ipAddress,
                    failedLoginCount.getOrDefault(
                            event.ipAddress,
                            0
                    ) + 1
            );
        }

        ipUsers
                .computeIfAbsent(
                        event.ipAddress,
                        key -> new HashSet<>()
                )
                .add(event.username);
    }

    static void viewAllEvents() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo security events available."
            );

            return;
        }

        System.out.println(
                "\n============ SECURITY EVENTS ============"
        );

        for (SecurityEvent event : events) {

            event.display();
        }
    }

    static void analyzeEvents() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available for analysis."
            );

            return;
        }

        correlatedIPs.clear();

        /*
         * First calculate all IP-level patterns.
         */
        for (SecurityEvent event : events) {

            event.threatLevel =
                    ThreatLevel.NORMAL;

            int failed =
                    failedLoginCount.getOrDefault(
                            event.ipAddress,
                            0
                    );

            int activity =
                    ipEventCount.getOrDefault(
                            event.ipAddress,
                            0
                    );

            int users =
                    ipUsers
                            .getOrDefault(
                                    event.ipAddress,
                                    new HashSet<>()
                            )
                            .size();

            boolean sensitive =
                    isSensitiveResource(
                            event.resource
                    );

            boolean unusualTime =
                    isUnusualTime(
                            event.timestamp
                    );

            /*
             * CRITICAL
             */
            if (failed >= 5
                    && sensitive
                    && unusualTime) {

                event.threatLevel =
                        ThreatLevel.CRITICAL;

                correlatedIPs.add(
                        event.ipAddress
                );
            }

            /*
             * HIGH
             */
            else if (failed >= 4
                    || users >= 3
                    || (sensitive
                    && !event.successful)
                    || activity >= 10) {

                event.threatLevel =
                        ThreatLevel.HIGH;

                correlatedIPs.add(
                        event.ipAddress
                );
            }

            /*
             * WARNING
             */
            else if (!event.successful
                    || sensitive
                    || unusualTime) {

                event.threatLevel =
                        ThreatLevel.WARNING;
            }
        }

        System.out.println(
                "\n✅ Security event analysis completed."
        );

        displayStatistics();
    }

    static void correlateEvents() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available."
            );

            return;
        }

        analyzeEvents();

        System.out.println(
                "\n========= EVENT CORRELATION ========="
        );

        boolean found = false;

        for (String ip : correlatedIPs) {

            int failed =
                    failedLoginCount.getOrDefault(
                            ip,
                            0
                    );

            int activity =
                    ipEventCount.getOrDefault(
                            ip,
                            0
                    );

            int users =
                    ipUsers
                            .getOrDefault(
                                    ip,
                                    new HashSet<>()
                            )
                            .size();

            System.out.println(
                    "\nIP Address: " + ip
            );

            System.out.println(
                    "Total Events: " + activity
            );

            System.out.println(
                    "Failed Logins: " + failed
            );

            System.out.println(
                    "Different Users: " + users
            );

            System.out.println(
                    "Correlation: MULTIPLE SUSPICIOUS EVENTS"
            );

            found = true;
        }

        if (!found) {

            System.out.println(
                    "No correlated incidents detected."
            );
        }
    }

    static void detectRepeatedFailures() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available."
            );

            return;
        }

        System.out.println(
                "\n======= REPEATED LOGIN FAILURES ======="
        );

        boolean found = false;

        for (String ip : failedLoginCount.keySet()) {

            int count =
                    failedLoginCount.get(ip);

            if (count >= 3) {

                System.out.println(
                        "⚠️ IP: "
                                + ip
                                + " | Failed Logins: "
                                + count
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

    static void detectMultiUserIPs() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available."
            );

            return;
        }

        System.out.println(
                "\n========= MULTI-USER IP ACTIVITY ========="
        );

        boolean found = false;

        for (String ip : ipUsers.keySet()) {

            HashSet<String> users =
                    ipUsers.get(ip);

            if (users.size() >= 3) {

                System.out.println(
                        "⚠️ IP: " + ip
                );

                System.out.println(
                        "Users: " + users
                );

                System.out.println(
                        "User Count: "
                                + users.size()
                );

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No multi-user IP pattern detected."
            );
        }
    }

    static void detectSensitiveAccess() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available."
            );

            return;
        }

        System.out.println(
                "\n======= SENSITIVE RESOURCE ACCESS ======="
        );

        boolean found = false;

        for (SecurityEvent event : events) {

            if (isSensitiveResource(
                    event.resource)) {

                event.display();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No sensitive resource access found."
            );
        }
    }

    static boolean isSensitiveResource(
            String resource) {

        return resource.equals("/admin")
                || resource.equals("/database")
                || resource.equals("/backup")
                || resource.equals("/config");
    }

    static boolean isUnusualTime(
            LocalDateTime timestamp) {

        int hour =
                timestamp.getHour();

        return hour >= 0 && hour < 5;
    }

    static void viewCorrelatedIncidents() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available."
            );

            return;
        }

        analyzeEvents();

        System.out.println(
                "\n========= CORRELATED INCIDENTS ========="
        );

        boolean found = false;

        for (SecurityEvent event : events) {

            if (event.threatLevel ==
                        ThreatLevel.HIGH
                    || event.threatLevel ==
                        ThreatLevel.CRITICAL) {

                event.display();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No high-risk correlated incidents found."
            );
        }
    }

    static void searchByIP() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available."
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

        for (SecurityEvent event : events) {

            if (event.ipAddress.equals(ip)) {

                event.display();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No events found for IP: "
                            + ip
            );
        }
    }

    static void filterByThreatLevel() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available."
            );

            return;
        }

        /*
         * Make sure threat levels are current.
         */
        analyzeEvents();

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
                selected =
                        ThreatLevel.NORMAL;
                break;

            case 2:
                selected =
                        ThreatLevel.WARNING;
                break;

            case 3:
                selected =
                        ThreatLevel.HIGH;
                break;

            case 4:
                selected =
                        ThreatLevel.CRITICAL;
                break;

            default:
                System.out.println(
                        "Invalid threat level."
                );

                return;
        }

        boolean found = false;

        System.out.println(
                "\n========== "
                        + selected
                        + " EVENTS =========="
        );

        for (SecurityEvent event : events) {

            if (event.threatLevel ==
                    selected) {

                event.display();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No events found for "
                            + selected
                            + " level."
            );
        }
    }

    static void displayStatistics() {

        if (events.isEmpty()) {

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

        for (SecurityEvent event : events) {

            switch (event.threatLevel) {

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

            if (event.successful) {

                successful++;

            } else {

                failed++;
            }
        }

        System.out.println(
                "\n========= SECURITY STATISTICS ========="
        );

        System.out.println(
                "Total Events       : "
                        + events.size()
        );

        System.out.println(
                "Successful Events  : "
                        + successful
        );

        System.out.println(
                "Failed Events      : "
                        + failed
        );

        System.out.println("----------------------------------------");

        System.out.println(
                "NORMAL             : "
                        + normal
        );

        System.out.println(
                "WARNING            : "
                        + warning
        );

        System.out.println(
                "HIGH               : "
                        + high
        );

        System.out.println(
                "CRITICAL           : "
                        + critical
        );

        System.out.println("----------------------------------------");

        System.out.println(
                "Unique IPs         : "
                        + ipEventCount.size()
        );

        System.out.println(
                "Correlated IPs     : "
                        + correlatedIPs.size()
        );
    }

    static void sortEventsByTime() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available."
            );

            return;
        }

        ArrayList<SecurityEvent> sortedEvents =
                new ArrayList<>(events);

        Collections.sort(
                sortedEvents,
                Comparator.comparing(
                        event -> event.timestamp
                )
        );

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        System.out.println(
                "\n======= EVENTS SORTED BY TIME ======="
        );

        for (SecurityEvent event :
                sortedEvents) {

            System.out.println(
                    "Event "
                            + event.eventId
                            + " | "
                            + event.timestamp
                            .format(formatter)
                            + " | "
                            + event.ipAddress
                            + " | "
                            + event.eventType
                            + " | "
                            + event.threatLevel
            );
        }
    }

    static void saveReport() {

        if (events.isEmpty()) {

            System.out.println(
                    "\nNo events available to save."
            );

            return;
        }

        analyzeEvents();

        String fileName =
                "SecurityEventCorrelationReport.txt";

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        try (FileWriter writer =
                     new FileWriter(fileName)) {

            writer.write(
                    "SECURITY EVENT CORRELATION REPORT\n"
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
                    "Total Events: "
                            + events.size()
                            + "\n"
            );

            writer.write(
                    "Unique IPs: "
                            + ipEventCount.size()
                            + "\n"
            );

            writer.write(
                    "Correlated IPs: "
                            + correlatedIPs.size()
                            + "\n\n"
            );

            writer.write(
                    "EVENT DETAILS\n"
            );

            writer.write(
                    "----------------------------------------\n"
            );

            for (SecurityEvent event :
                    events) {

                writer.write(
                        "Event ID: "
                                + event.eventId
                                + "\n"
                );

                writer.write(
                        "IP Address: "
                                + event.ipAddress
                                + "\n"
                );

                writer.write(
                        "Username: "
                                + event.username
                                + "\n"
                );

                writer.write(
                        "Event Type: "
                                + event.eventType
                                + "\n"
                );

                writer.write(
                        "Resource: "
                                + event.resource
                                + "\n"
                );

                writer.write(
                        "Result: "
                                + (event.successful
                                ? "SUCCESS"
                                : "FAILED")
                                + "\n"
                );

                writer.write(
                        "Timestamp: "
                                + event.timestamp
                                .format(formatter)
                                + "\n"
                );

                writer.write(
                        "Threat Level: "
                                + event.threatLevel
                                + "\n"
                );

                writer.write(
                        "----------------------------------------\n"
                );
            }

            writer.write(
                    "\nCORRELATED IP ADDRESSES\n"
            );

            writer.write(
                    "----------------------------------------\n"
            );

            for (String ip :
                    correlatedIPs) {

                writer.write(
                        ip + "\n"
                );
            }

            System.out.println(
                    "\n✅ Incident report saved successfully."
            );

            System.out.println(
                    "File: " + fileName
            );

        } catch (IOException e) {

            System.out.println(
                    "Error while saving report."
            );

            System.out.println(
                    "Reason: "
                            + e.getMessage()
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