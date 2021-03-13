package server;

import states.ConnectState;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    int filesize = 6022386;
    int bytesRead;
    int current = 0;

    public void downloadFile() {
        try (Socket socket = new Socket("192.168.0.11", Server.PORT)) {

            byte[] mybytearray = new byte[filesize];
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream("MyData.ser");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            pr.println("DOWNLOAD");
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            int count;
            byte[] buffer = new byte[8192]; // or 4096, or more
            while ((count = is.read(buffer)) > 0)
            {
                bos.write(buffer, 0, count);
            }
            bos.flush();
            bos.close();
            socket.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    public void uploadFile() {
        try (Socket socket = new Socket("192.168.0.11", Server.PORT)) {
            String FileName = "MyData.ser";
            File MyFile = new File(FileName);
            int FileSize = (int) MyFile.length();
            OutputStream os = socket.getOutputStream();
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(MyFile));
            pr.println("UPLOAD");
            pr.println(FileName);
            pr.println(FileSize);
            byte[] filebyte = new byte[FileSize];
            bis.read(filebyte, 0, filebyte.length);
            os.write(filebyte, 0, filebyte.length);
            os.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


