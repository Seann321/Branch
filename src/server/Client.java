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
            System.out.println("File Sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeData() {
        updateFromData();
        for(CustomerUpdated c : TempCustomers){
            if(c.ID.equals(DataState.CurrentCustomer.ID)){
                c = DataState.CurrentCustomer;
            }else{
                TempCustomers.remove(c);
            }
        }
        DataState.Customers = TempCustomers;
        DataState.SaveArray();
    }

    private void updateFromData() {
        downloadFile();
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("CustomerData.ser");
            in = new ObjectInputStream(fis);
            TempCustomers.clear();
            TempCustomers = (ArrayList) in.readObject();
            //System.out.println(TempCustomers.size());
            in.close();
            fis.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}


