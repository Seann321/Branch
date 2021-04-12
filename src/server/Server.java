package server;

import branch.Main;
import states.DataState;
import states.dataState.Customer;
import states.dataState.EditCustomer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    public static boolean runServer = false;
    private Thread thread;
    public static final int PORT = 1453;
    ServerSocket serverSocket;
    Socket socket;
    FileTransferProcessor ftp;
    File myFile = new File("CustomerData.ser");


    public Server() {
    }

    Scanner in;

    public void manageData() throws IOException {
        socket = serverSocket.accept();
        ftp = new FileTransferProcessor(socket);
        in = new Scanner(socket.getInputStream());
        String line = in.nextLine();
        if (line.equals("UPLOAD")) {
            downloadData();
        } else if (line.equals("DOWNLOAD")) {
            uploadData();
        } else {
            System.out.println(line);
        }
    }

    private void uploadData() throws IOException {
        ftp.sendFile(myFile);
    }

    private void downloadData() throws IOException {
        ftp.receiveFile("CustomerData.ser");
        DataState.UpdateFromData();
    }



    private void init() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on " + getIPAddress() + ":" + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String IP = "";

    public static String getIPAddress() {
        if (!IP.equals("")) {
            return IP;
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // *EDIT*
                    if (addr instanceof Inet6Address) continue;
                    IP = addr.getHostAddress();
                    return addr.getHostAddress();
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return IP;
    }

    private boolean running = false;

    @Override
    public void run() {
        while (running) {
            try {
                manageData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        init();
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
