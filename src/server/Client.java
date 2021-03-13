package server;

import states.ConnectState;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    FileTransferProcessor ftp;
    File myFile = new File("MyData.ser");

    public void downloadFile() {
        try (Socket socket = new Socket(ConnectState.connectIP, Server.PORT)) {
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            pr.println("DOWNLOAD");
            ftp = new FileTransferProcessor(socket);
            ftp.receiveFile("MyData.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadFile() {
        try (Socket socket = new Socket(ConnectState.connectIP, Server.PORT)) {
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            pr.println("UPLOAD");
            ftp = new FileTransferProcessor(socket);
            ftp.sendFile(myFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


