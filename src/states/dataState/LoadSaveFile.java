package states.dataState;

import java.io.*;

public class LoadSaveFile {

    public static void createFile() {
        // create a file object for the current location
        File file = new File("info.txt");
        try {
            // trying to create a file based on the object
            boolean value = file.createNewFile();
            if (value) {
                System.out.println("The new file is created.");
            } else {
                System.out.println("The file already exists.");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void addPerson(String text) {
        String data = "This is the data in the output file";
        try {
            // Creates a Writer using FileWriter
            FileWriter output = new FileWriter("info.txt",true);

            // Writes string to the file
            output.write(text);
            // Closes the writer
            output.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}



