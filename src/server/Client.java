package server;

import states.ConnectState;
import states.DataState;
import states.dataState.Customer;
import states.dataState.CustomerUpdated;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    FileTransferProcessor ftp;
    File myFile = new File("CustomerData.ser");
    static ArrayList<CustomerUpdated> TempCustomers = new ArrayList<>();


    public void downloadFile() {
        try (Socket socket = new Socket(ConnectState.connectIP, Server.PORT)) {
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            pr.println("DOWNLOAD");
            ftp = new FileTransferProcessor(socket);
            ftp.receiveFile("CustomerData.ser");
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
            //System.out.println("File Sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeData() {
        downloadFile();
        DataState.SaveArray();
        CustomerUpdated customerUpdated = null;
        for(CustomerUpdated c : DataState.Customers){
            if(c.ID.equals(DataState.CurrentCustomer.ID)){
                customerUpdated = DataState.CurrentCustomer;
            }
        }
        if(customerUpdated == null){
            DataState.Customers.add(DataState.CurrentCustomer);
        }
        DataState.SaveArray();
    }
}


