package server;

import branch.Main;

import java.io.*;
import java.net.*;
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
    int filesize = 6022386;
    int bytesRead;
    int current = 0;

    public Server() {

    }

    Scanner in;
    InputStream is;
    PrintWriter pr;
    String FileName;
    int FileSize;

    public void manageData() throws IOException {
        socket = serverSocket.accept();
        in = new Scanner(socket.getInputStream());
        is = socket.getInputStream();
        pr = new PrintWriter(socket.getOutputStream(), true);
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
        File myFile = new File("MyData.ser");
        byte[] mybytearray = new byte[(int) myFile.length()];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        System.out.println(bis.read(mybytearray, 0, mybytearray.length));
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending to client...");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
        socket.close();
    }

    private void downloadData() throws IOException {

        FileName = in.nextLine();
        FileSize = in.nextInt();
        FileOutputStream fos = new FileOutputStream(FileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte[] filebyte = new byte[FileSize];

        int file = is.read(filebyte, 0, filebyte.length);
        bos.write(filebyte, 0, file);
        System.out.println("Incoming File: " + FileName);
        System.out.println("Size: " + FileSize + "Byte");
        if (FileSize == file) System.out.println("File is verified");
        else System.out.println("File is corrupted. File Recieved " + file + " Byte");
        pr.println("File Recieved Successfully.");
        bos.close();
        socket.close();
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
