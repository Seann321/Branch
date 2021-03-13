package server;

import states.ConnectState;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    int filesize = 6022386;
    int bytesRead;
    int current = 0;

    public void downloadFile() {
        try (Socket socket = new Socket(ConnectState.connectIP, Server.PORT)) {

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
            bos.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }

}


