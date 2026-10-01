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

public class NetworkTrafficAnalyzerSimulator {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    enum TrafficStatus {
        NORMAL,
        SUSPICIOUS,
        MALICIOUS
    }

    static class NetworkPacket {
        int packetId;
        String sourceIP;
        String destinationIP;
        int sourcePort;
        int destinationPort;
        String protocol;
        int packetSize;
        TrafficStatus status;
        LocalDateTime timestamp;

        NetworkPacket(
                int packetId,
                String sourceIP,
                String destinationIP,
                int sourcePort,
                int destinationPort,
                String protocol,
                int packetSize) {

            this.packetId = packetId;
            this.sourceIP = sourceIP;
            this.destinationIP = destinationIP;
            this.sourcePort = sourcePort;
            this.destinationPort = destinationPort;
            this.protocol = protocol;
            this.packetSize = packetSize;
            this.status = TrafficStatus.NORMAL;
            this.timestamp = LocalDateTime.now();
        }

        void display() {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            System.out.println("--------------------------------------------");
            System.out.println("Packet ID        : " + packetId);
            System.out.println("Source IP        : " + sourceIP);
            System.out.println("Destination IP   : " + destinationIP);
            System.out.println("Source Port      : " + sourcePort);
            System.out.println("Destination Port : " + destinationPort);
            System.out.println("Protocol         : " + protocol);
            System.out.println("Packet Size      : " + packetSize + " bytes");
            System.out.println("Status           : " + status);
            System.out.println("Timestamp        : " + timestamp.format(formatter));
            System.out.println("--------------------------------------------");
        }
    }

    static ArrayList<NetworkPacket> packets = new ArrayList<>();
    static HashSet<String> blockedIPs = new HashSet<>();
    static HashSet<Integer> suspiciousPorts = new HashSet<>();

    static HashMap<String, Integer> ipTrafficCount = new HashMap<>();
    static HashMap<String, Integer> protocolCount = new HashMap<>();

    static int nextPacketId = 1001;

    static String[] internalIPs = {
            "192.168.1.10",
            "192.168.1.20",
            "192.168.1.30",
            "192.168.1.40",
            "10.0.0.15"
    };

    static String[] externalIPs = {
            "203.0.113.10",
            "198.51.100.20",
            "192.0.2.15",
            "185.44.72.91",
            "45.76.33.12",
            "91.205.172.44"
    };

    static String[] protocols = {
            "TCP",
            "UDP",
            "HTTP",
            "HTTPS",
            "DNS",
            "SSH"
    };

    static int[] commonPorts = {
            21,
            22,
            25,
            53,
            80,
            110,
            143,
            443,
            8080
    };

    public static void main(String[] args) {

        initializeSuspiciousPorts();

        System.out.println("==============================================");
        System.out.println("     NETWORK TRAFFIC ANALYZER SIMULATOR");
        System.out.println("==============================================");
        System.out.println("Synthetic traffic analysis environment");
        System.out.println("No real network traffic is captured.");

        boolean running = true;

        while (running) {

            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    generatePackets();
                    break;

                case 2:
                    viewAllPackets();
                    break;

                case 3:
                    analyzeTraffic();
                    break;

                case 4:
                    detectSuspiciousIPs();
                    break;

                case 5:
                    detectHighVolumeTraffic();
                    break;

                case 6:
                    blockIP();
                    break;

                case 7:
                    displayStatistics();
                    break;

                case 8:
                    sortPacketsBySize();
                    break;

                case 9:
                    searchByIP();
                    break;

                case 10:
                    saveReport();
                    break;

                case 11:
                    System.out.println("\nExiting Network Traffic Analyzer...");
                    System.out.println("Total packets analyzed: " + packets.size());
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    static void displayMenu() {

        System.out.println("\n============== TRAFFIC DASHBOARD ==============");
        System.out.println("1. Generate Network Traffic");
        System.out.println("2. View All Packets");
        System.out.println("3. Analyze Traffic");
        System.out.println("4. Detect Suspicious IPs");
        System.out.println("5. Detect High-Volume Traffic");
        System.out.println("6. Block IP");
        System.out.println("7. Traffic Statistics");
        System.out.println("8. Sort Packets by Size");
        System.out.println("9. Search Traffic by IP");
        System.out.println("10. Save Analysis Report");
        System.out.println("11. Exit");
        System.out.println("================================================");
    }

    static void initializeSuspiciousPorts() {

        suspiciousPorts.add(23);
        suspiciousPorts.add(4444);
        suspiciousPorts.add(5555);
        suspiciousPorts.add(6667);
        suspiciousPorts.add(31337);
    }

    static void generatePackets() {

        int count = readInt("How many packets should be generated? ");

        if (count <= 0) {
            System.out.println("Packet count must be greater than zero.");
            return;
        }

        if (count > 100) {
            System.out.println("Maximum 100 packets can be generated at once.");
            return;
        }

        System.out.println("\nGenerating synthetic network traffic...");

        for (int i = 0; i < count; i++) {

            String sourceIP;
            String destinationIP;

            if (random.nextBoolean()) {
                sourceIP = internalIPs[random.nextInt(internalIPs.length)];
                destinationIP = externalIPs[random.nextInt(externalIPs.length)];
            } else {
                sourceIP = externalIPs[random.nextInt(externalIPs.length)];
                destinationIP = internalIPs[random.nextInt(internalIPs.length)];
            }

            int sourcePort = random.nextInt(65535) + 1;

            int destinationPort;

            if (random.nextInt(10) < 8) {
                destinationPort =
                        commonPorts[random.nextInt(commonPorts.length)];
            } else {
                int[] suspiciousPortArray = {
                        23,
                        4444,
                        5555,
                        6667,
                        31337
                };

                destinationPort =
                        suspiciousPortArray[
                                random.nextInt(suspiciousPortArray.length)
                        ];
            }

            String protocol =
                    protocols[random.nextInt(protocols.length)];

            int packetSize =
                    random.nextInt(1450) + 64;

            NetworkPacket packet =
                    new NetworkPacket(
                            nextPacketId++,
                            sourceIP,
                            destinationIP,
                            sourcePort,
                            destinationPort,
                            protocol,
                            packetSize
                    );

            packets.add(packet);

            updateTrafficStatistics(packet);
        }

        System.out.println(
                "✅ " + count + " synthetic packets generated successfully."
        );
    }

    static void updateTrafficStatistics(NetworkPacket packet) {

        ipTrafficCount.put(
                packet.sourceIP,
                ipTrafficCount.getOrDefault(packet.sourceIP, 0) + 1
        );

        protocolCount.put(
                packet.protocol,
                protocolCount.getOrDefault(packet.protocol, 0) + 1
        );
    }

    static void viewAllPackets() {

        if (packets.isEmpty()) {
            System.out.println("\nNo packets available.");
            return;
        }

        System.out.println("\n=========== NETWORK PACKETS ===========");

        for (NetworkPacket packet : packets) {
            packet.display();
        }
    }

    static void analyzeTraffic() {

        if (packets.isEmpty()) {
            System.out.println("\nNo traffic available for analysis.");
            return;
        }

        System.out.println("\n========== TRAFFIC ANALYSIS ==========");

        int normal = 0;
        int suspicious = 0;
        int malicious = 0;

        for (NetworkPacket packet : packets) {

            if (suspiciousPorts.contains(packet.destinationPort)) {

                packet.status = TrafficStatus.SUSPICIOUS;
            }

            if (blockedIPs.contains(packet.sourceIP)) {

                packet.status = TrafficStatus.MALICIOUS;
            }

            if (packet.packetSize > 1300 &&
                    packet.destinationPort == 23) {

                packet.status = TrafficStatus.MALICIOUS;
            }

            switch (packet.status) {

                case NORMAL:
                    normal++;
                    break;

                case SUSPICIOUS:
                    suspicious++;
                    break;

                case MALICIOUS:
                    malicious++;
                    break;
            }
        }

        System.out.println("Normal Packets     : " + normal);
        System.out.println("Suspicious Packets : " + suspicious);
        System.out.println("Malicious Packets  : " + malicious);

        System.out.println("---------------------------------------");

        if (malicious > 0) {
            System.out.println("⚠️ MALICIOUS traffic detected!");
        } else if (suspicious > 0) {
            System.out.println("⚠️ Suspicious traffic detected.");
        } else {
            System.out.println("✅ No suspicious traffic detected.");
        }
    }

    static void detectSuspiciousIPs() {

        if (packets.isEmpty()) {
            System.out.println("\nNo traffic available.");
            return;
        }

        HashSet<String> suspiciousIPs = new HashSet<>();

        for (NetworkPacket packet : packets) {

            if (suspiciousPorts.contains(packet.destinationPort)) {
                suspiciousIPs.add(packet.sourceIP);
            }
        }

        System.out.println("\n========== SUSPICIOUS IPS ==========");

        if (suspiciousIPs.isEmpty()) {
            System.out.println("No suspicious IPs detected.");
            return;
        }

        for (String ip : suspiciousIPs) {
            System.out.println("⚠️ Suspicious IP: " + ip);
        }

        System.out.println("Total Suspicious IPs: "
                + suspiciousIPs.size());
    }

    static void detectHighVolumeTraffic() {

        if (packets.isEmpty()) {
            System.out.println("\nNo traffic available.");
            return;
        }

        System.out.println("\n======= HIGH-VOLUME TRAFFIC =======");

        boolean found = false;

        for (String ip : ipTrafficCount.keySet()) {

            int count = ipTrafficCount.get(ip);

            if (count >= 5) {

                System.out.println(
                        "⚠️ " + ip + " generated " + count + " packets."
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No high-volume IP detected.");
        }
    }

    static void blockIP() {

        System.out.print("Enter IP address to block: ");

        String ip = scanner.nextLine().trim();

        if (ip.isEmpty()) {
            System.out.println("IP address cannot be empty.");
            return;
        }

        if (blockedIPs.contains(ip)) {
            System.out.println("IP is already blocked.");
            return;
        }

        blockedIPs.add(ip);

        System.out.println("\n🛡️ IP BLOCKED");
        System.out.println("Blocked IP: " + ip);

        for (NetworkPacket packet : packets) {

            if (packet.sourceIP.equals(ip)) {
                packet.status = TrafficStatus.MALICIOUS;
            }
        }
    }

    static void displayStatistics() {

        if (packets.isEmpty()) {
            System.out.println("\nNo traffic statistics available.");
            return;
        }

        int totalBytes = 0;

        int normal = 0;
        int suspicious = 0;
        int malicious = 0;

        for (NetworkPacket packet : packets) {

            totalBytes += packet.packetSize;

            switch (packet.status) {

                case NORMAL:
                    normal++;
                    break;

                case SUSPICIOUS:
                    suspicious++;
                    break;

                case MALICIOUS:
                    malicious++;
                    break;
            }
        }

        System.out.println("\n========== TRAFFIC STATISTICS ==========");

        System.out.println("Total Packets    : " + packets.size());
        System.out.println("Total Data       : " + totalBytes + " bytes");
        System.out.println("Normal Traffic   : " + normal);
        System.out.println("Suspicious       : " + suspicious);
        System.out.println("Malicious        : " + malicious);
        System.out.println("Blocked IPs      : " + blockedIPs.size());

        System.out.println("\nProtocol Statistics:");

        for (String protocol : protocolCount.keySet()) {

            System.out.println(
                    protocol + " : " + protocolCount.get(protocol)
            );
        }

        System.out.println("\nIP Traffic Statistics:");

        for (String ip : ipTrafficCount.keySet()) {

            System.out.println(
                    ip + " : " + ipTrafficCount.get(ip) + " packets"
            );
        }
    }

    static void sortPacketsBySize() {

        if (packets.isEmpty()) {
            System.out.println("\nNo packets available.");
            return;
        }

        ArrayList<NetworkPacket> sortedPackets =
                new ArrayList<>(packets);

        Collections.sort(
                sortedPackets,
                Comparator.comparingInt(
                        packet -> packet.packetSize
                )
        );

        System.out.println("\n====== PACKETS SORTED BY SIZE ======");

        for (NetworkPacket packet : sortedPackets) {

            System.out.println(
                    "Packet " + packet.packetId
                            + " | "
                            + packet.packetSize
                            + " bytes"
                            + " | "
                            + packet.status
            );
        }
    }

    static void searchByIP() {

        if (packets.isEmpty()) {
            System.out.println("\nNo packets available.");
            return;
        }

        System.out.print("Enter IP address to search: ");

        String ip = scanner.nextLine().trim();

        boolean found = false;

        System.out.println("\n========== SEARCH RESULTS ==========");

        for (NetworkPacket packet : packets) {

            if (packet.sourceIP.equals(ip)
                    || packet.destinationIP.equals(ip)) {

                packet.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No traffic found for IP: " + ip);
        }
    }

    static void saveReport() {

        if (packets.isEmpty()) {
            System.out.println("\nNo traffic available to save.");
            return;
        }

        String fileName = "NetworkTrafficReport.txt";

        try (FileWriter writer = new FileWriter(fileName)) {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy HH:mm:ss"
                    );

            writer.write("NETWORK TRAFFIC ANALYSIS REPORT\n");
            writer.write("========================================\n\n");

            writer.write(
                    "Generated At: "
                            + LocalDateTime.now().format(formatter)
                            + "\n\n"
            );

            writer.write(
                    "Total Packets: "
                            + packets.size()
                            + "\n"
            );

            writer.write(
                    "Blocked IPs: "
                            + blockedIPs.size()
                            + "\n\n"
            );

            writer.write("PACKET DETAILS\n");
            writer.write("----------------------------------------\n");

            for (NetworkPacket packet : packets) {

                writer.write(
                        "Packet ID: "
                                + packet.packetId
                                + "\n"
                );

                writer.write(
                        "Source IP: "
                                + packet.sourceIP
                                + "\n"
                );

                writer.write(
                        "Destination IP: "
                                + packet.destinationIP
                                + "\n"
                );

                writer.write(
                        "Source Port: "
                                + packet.sourcePort
                                + "\n"
                );

                writer.write(
                        "Destination Port: "
                                + packet.destinationPort
                                + "\n"
                );

                writer.write(
                        "Protocol: "
                                + packet.protocol
                                + "\n"
                );

                writer.write(
                        "Packet Size: "
                                + packet.packetSize
                                + " bytes\n"
                );

                writer.write(
                        "Status: "
                                + packet.status
                                + "\n"
                );

                writer.write("----------------------------------------\n");
            }

            writer.write("\nBLOCKED IP ADDRESSES\n");
            writer.write("----------------------------------------\n");

            for (String ip : blockedIPs) {
                writer.write(ip + "\n");
            }

            System.out.println(
                    "\n✅ Report saved successfully."
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