import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class CyberIncidentResponseSimulator {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    static class Incident {
        int id;
        String type;
        String sourceIP;
        Severity severity;
        String status;
        LocalDateTime detectedAt;
        String analystAction;

        Incident(int id, String type, String sourceIP, Severity severity) {
            this.id = id;
            this.type = type;
            this.sourceIP = sourceIP;
            this.severity = severity;
            this.status = "OPEN";
            this.detectedAt = LocalDateTime.now();
            this.analystAction = "Pending";
        }

        void display() {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            System.out.println("\n----------------------------------------");
            System.out.println("Incident ID     : " + id);
            System.out.println("Incident Type   : " + type);
            System.out.println("Source IP       : " + sourceIP);
            System.out.println("Severity        : " + severity);
            System.out.println("Status          : " + status);
            System.out.println("Detected At     : " + detectedAt.format(formatter));
            System.out.println("Analyst Action  : " + analystAction);
            System.out.println("----------------------------------------");
        }
    }

    static ArrayList<Incident> incidents = new ArrayList<>();
    static HashMap<String, Integer> blockedIPs = new HashMap<>();

    static int nextIncidentId = 1001;
    static int responseScore = 0;

    static String[] incidentTypes = {
            "Brute Force Attack",
            "Suspicious Login",
            "Malware Detection",
            "Port Scanning",
            "Phishing Attempt",
            "Unauthorized Access",
            "Data Exfiltration",
            "DDoS Activity"
    };

    static String[] sampleIPs = {
            "192.168.10.15",
            "10.0.0.45",
            "172.16.5.21",
            "203.0.113.45",
            "198.51.100.27",
            "185.45.72.19"
    };

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("     CYBER INCIDENT RESPONSE SIMULATOR");
        System.out.println("==============================================");
        System.out.println("Welcome, SOC Analyst!");

        boolean running = true;

        while (running) {

            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    generateIncident();
                    break;

                case 2:
                    viewIncidents();
                    break;

                case 3:
                    analyzeIncident();
                    break;

                case 4:
                    blockIP();
                    break;

                case 5:
                    resetCredential();
                    break;

                case 6:
                    closeIncident();
                    break;

                case 7:
                    displayStatistics();
                    break;

                case 8:
                    saveReport();
                    break;

                case 9:
                    System.out.println("\nExiting Cyber Incident Response Simulator...");
                    System.out.println("Final Response Score: " + responseScore);
                    System.out.println("Stay alert. Stay secure! 🛡️");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    static void displayMenu() {

        System.out.println("\n============== SOC DASHBOARD ==============");
        System.out.println("1. Generate Security Incident");
        System.out.println("2. View All Incidents");
        System.out.println("3. Analyze Incident");
        System.out.println("4. Block Suspicious IP");
        System.out.println("5. Reset Compromised Credential");
        System.out.println("6. Close Incident");
        System.out.println("7. Security Statistics");
        System.out.println("8. Save Incident Report");
        System.out.println("9. Exit");
        System.out.println("============================================");
    }

    static void generateIncident() {

        String type = incidentTypes[random.nextInt(incidentTypes.length)];
        String ip = sampleIPs[random.nextInt(sampleIPs.length)];

        Severity severity = generateSeverity();

        Incident incident =
                new Incident(nextIncidentId++, type, ip, severity);

        incidents.add(incident);

        System.out.println("\n🚨 SECURITY INCIDENT DETECTED!");

        incident.display();

        if (severity == Severity.CRITICAL) {
            System.out.println("⚠️ CRITICAL ALERT: Immediate response required!");
        } else if (severity == Severity.HIGH) {
            System.out.println("⚠️ HIGH PRIORITY: Investigate immediately.");
        } else {
            System.out.println("ℹ️ Incident logged for investigation.");
        }
    }

    static Severity generateSeverity() {

        int value = random.nextInt(100);

        if (value < 15) {
            return Severity.CRITICAL;
        } else if (value < 40) {
            return Severity.HIGH;
        } else if (value < 70) {
            return Severity.MEDIUM;
        } else {
            return Severity.LOW;
        }
    }

    static void viewIncidents() {

        if (incidents.isEmpty()) {
            System.out.println("\nNo security incidents found.");
            return;
        }

        System.out.println("\n========== INCIDENT LOG ==========");

        for (Incident incident : incidents) {
            incident.display();
        }
    }

    static void analyzeIncident() {

        if (incidents.isEmpty()) {
            System.out.println("\nNo incidents available for analysis.");
            return;
        }

        int id = readInt("Enter Incident ID to analyze: ");

        Incident incident = findIncident(id);

        if (incident == null) {
            System.out.println("Incident not found.");
            return;
        }

        System.out.println("\nAnalyzing Incident " + incident.id + "...");
        System.out.println("Checking source IP...");
        System.out.println("Checking severity...");
        System.out.println("Checking attack pattern...");
        System.out.println("Analysis completed.");

        incident.analystAction = "Incident analyzed";

        if (incident.severity == Severity.CRITICAL) {
            responseScore += 20;
            System.out.println("Recommended Action: Isolate affected system immediately.");
        } else if (incident.severity == Severity.HIGH) {
            responseScore += 15;
            System.out.println("Recommended Action: Block source and investigate.");
        } else if (incident.severity == Severity.MEDIUM) {
            responseScore += 10;
            System.out.println("Recommended Action: Monitor and investigate.");
        } else {
            responseScore += 5;
            System.out.println("Recommended Action: Continue monitoring.");
        }
    }

    static void blockIP() {

        if (incidents.isEmpty()) {
            System.out.println("\nNo incidents available.");
            return;
        }

        int id = readInt("Enter Incident ID: ");

        Incident incident = findIncident(id);

        if (incident == null) {
            System.out.println("Incident not found.");
            return;
        }

        String ip = incident.sourceIP;

        if (blockedIPs.containsKey(ip)) {
            System.out.println("IP " + ip + " is already blocked.");
            return;
        }

        blockedIPs.put(ip, id);

        incident.analystAction = "Source IP blocked";
        responseScore += 15;

        System.out.println("\n🛡️ IP BLOCKED SUCCESSFULLY");
        System.out.println("Blocked IP: " + ip);
    }

    static void resetCredential() {

        if (incidents.isEmpty()) {
            System.out.println("\nNo incidents available.");
            return;
        }

        int id = readInt("Enter Incident ID: ");

        Incident incident = findIncident(id);

        if (incident == null) {
            System.out.println("Incident not found.");
            return;
        }

        System.out.println("\nCredential reset process started...");
        System.out.println("Invalidating old session...");
        System.out.println("Generating secure credential...");
        System.out.println("Credential reset completed.");

        incident.analystAction = "Credential reset";
        responseScore += 10;
    }

    static void closeIncident() {

        if (incidents.isEmpty()) {
            System.out.println("\nNo incidents available.");
            return;
        }

        int id = readInt("Enter Incident ID to close: ");

        Incident incident = findIncident(id);

        if (incident == null) {
            System.out.println("Incident not found.");
            return;
        }

        if (incident.status.equals("CLOSED")) {
            System.out.println("Incident is already closed.");
            return;
        }

        incident.status = "CLOSED";
        incident.analystAction = "Incident resolved and closed";

        responseScore += 20;

        System.out.println("\n✅ Incident " + id + " closed successfully.");
    }

    static void displayStatistics() {

        int open = 0;
        int closed = 0;
        int critical = 0;
        int high = 0;
        int medium = 0;
        int low = 0;

        for (Incident incident : incidents) {

            if (incident.status.equals("OPEN")) {
                open++;
            } else {
                closed++;
            }

            switch (incident.severity) {
                case CRITICAL:
                    critical++;
                    break;

                case HIGH:
                    high++;
                    break;

                case MEDIUM:
                    medium++;
                    break;

                case LOW:
                    low++;
                    break;
            }
        }

        System.out.println("\n=========== SECURITY STATISTICS ===========");
        System.out.println("Total Incidents : " + incidents.size());
        System.out.println("Open Incidents  : " + open);
        System.out.println("Closed Incidents: " + closed);
        System.out.println("--------------------------------------------");
        System.out.println("Critical        : " + critical);
        System.out.println("High            : " + high);
        System.out.println("Medium          : " + medium);
        System.out.println("Low             : " + low);
        System.out.println("--------------------------------------------");
        System.out.println("Blocked IPs     : " + blockedIPs.size());
        System.out.println("Response Score  : " + responseScore);
        System.out.println("============================================");
    }

    static void saveReport() {

        if (incidents.isEmpty()) {
            System.out.println("\nNo incidents available to save.");
            return;
        }

        String fileName = "CyberIncidentReport.txt";

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("CYBER INCIDENT RESPONSE REPORT\n");
            writer.write("====================================\n\n");

            writer.write("Generated At: "
                    + LocalDateTime.now()
                    + "\n\n");

            for (Incident incident : incidents) {

                writer.write("Incident ID    : " + incident.id + "\n");
                writer.write("Type           : " + incident.type + "\n");
                writer.write("Source IP      : " + incident.sourceIP + "\n");
                writer.write("Severity       : " + incident.severity + "\n");
                writer.write("Status         : " + incident.status + "\n");
                writer.write("Analyst Action : " + incident.analystAction + "\n");
                writer.write("------------------------------------\n");
            }

            writer.write("\nBlocked IP Count: "
                    + blockedIPs.size()
                    + "\n");

            writer.write("Response Score: "
                    + responseScore
                    + "\n");

            System.out.println("\n✅ Report saved successfully.");
            System.out.println("File: " + fileName);

        } catch (IOException e) {

            System.out.println("Error while saving report.");
            System.out.println("Reason: " + e.getMessage());
        }
    }

    static Incident findIncident(int id) {

        for (Incident incident : incidents) {

            if (incident.id == id) {
                return incident;
            }
        }

        return null;
    }

    static int readInt(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }
}