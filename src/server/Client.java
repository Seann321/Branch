package server;

import states.ConnectState;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    int filesize = 6022386;
    long start = System.currentTimeMillis();
    int bytesRead;
    int current = 0;

    public void downloadFile() {
        try (Socket socket = new Socket("192.168.0.11", Server.PORT)) {

            byte[] mybytearray = new byte[filesize];
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream("MyData.ser");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            bos.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    public void uploadFile() {
        try (Socket socket = new Socket("192.168.0.11", Server.PORT)) {

            File myFile = new File ("MyData.ser");
            byte [] mybytearray  = new byte [(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            System.out.println(bis.read(mybytearray,0,mybytearray.length));
            OutputStream os = socket.getOutputStream();
            System.out.println("Sending...");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}


