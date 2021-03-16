package server;

import states.ConnectState;
import states.DataState;
import states.dataState.Customer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    FileTransferProcessor ftp;
    File myFile = new File("MyData.ser");
    static ArrayList<Customer> TempCustomers = new ArrayList<>();


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
        mergeData();
        try (Socket socket = new Socket(ConnectState.connectIP, Server.PORT)) {
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            pr.println("UPLOAD");
            ftp = new FileTransferProcessor(socket);
            ftp.sendFile(myFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeData() {
        updateFromData();
        if(TempCustomers.contains(DataState.CurrentCustomer)){
            System.out.println(DataState.CurrentCustomer.getName());
        }
        DataState.Customers = TempCustomers;
    }

    private void updateFromData() {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("MyData.ser");
            in = new ObjectInputStream(fis);
            TempCustomers.clear();
            TempCustomers = (ArrayList) in.readObject();
            System.out.println(TempCustomers.size());
            in.close();
            fis.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}


