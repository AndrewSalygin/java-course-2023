package edu.hw6.Task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PortScanner {
    private PortScanner() {
    }

    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 49151;
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Map<Integer, String> KNOWN_PORTS = Map.ofEntries(
        Map.entry(135, "EPMAP"),
        Map.entry(137, "NetBIOS Name Service"),
        Map.entry(138, "NetBIOS Datagram Service"),
        Map.entry(139, "NetBIOS Session Service"),
        Map.entry(445, "Microsoft-DS Active Directory"),
        Map.entry(1900, "Simple Service Discovery Protocol (SSDP)"),
        Map.entry(5432, "PostgreSQL"),
        Map.entry(5353, "Multicast DNS (MDNS)"),
        Map.entry(5355, "LLMNR")
    );

    public static void scanPorts() {
        for (int port = MIN_PORT; port <= MAX_PORT; port++) {
            LOGGER.info(scanTCPPort(port));
            LOGGER.info(scanUPDPort(port));
        }
    }

    private static String scanTCPPort(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
            return ProtocolType.TCP.getMessage() + " " + port + " " + ProtocolStatus.FREE.getMessage();
        } catch (IOException e) {
            return ProtocolType.TCP.getMessage() + " " + port + " "
                + KNOWN_PORTS.getOrDefault(port, ProtocolStatus.UNKNOWN.getMessage());
        }
    }

    private static String scanUPDPort(int port) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(port);
            datagramSocket.close();
            return ProtocolType.UPD.getMessage() + " " + port + " " + ProtocolStatus.FREE.getMessage();
        } catch (IOException e) {
            return ProtocolType.UPD.getMessage() + " " + port + " "
                + KNOWN_PORTS.getOrDefault(port, ProtocolStatus.UNKNOWN.getMessage());
        }
    }
}
