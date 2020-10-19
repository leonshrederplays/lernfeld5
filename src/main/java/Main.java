import commands.Shutdown;
import commands.*;
import instances.ConfigInstance;
import utils.DBUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main /*extends Application*/ {

    //private Object Scene;

    public static void main(String[] args) {
        //Application.launch(args);
        // Define Filename
        File file = new File("config.ini");
        //dbUtils.error();
        try {
            // Try to create File and set DBInitialized to false
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                // Open a FileWriter to write to this File
                FileWriter myWriter = new FileWriter("config.ini");
                // Connect without Database.
                DBUtils.connector("");
                // Create Database and test Data.
                DBUtils.createSQL();
                // Write DBInitialized true.
                myWriter.write("true");
                // CLose FileWriter.
                myWriter.close();
                // Get Boolean of DBInitialized.
                boolean finished = ConfigInstance.isSQLfinished;
                do {
                    // Loop till the Database and test Data got created.
                    if (finished) {
                        // Select all Data.
                        DBUtils.selectData();
                        break;
                    }
                } while (true);
            } else {
                System.out.println("File exists: " + file.getName());
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    if (data.equals("false")) {
                        DBUtils.connector("");
                        DBUtils.createSQL();
                        FileWriter newWriter = new FileWriter("config.ini");
                        newWriter.write("true");
                        newWriter.close();
                        boolean finished = ConfigInstance.isSQLfinished;
                        do {
                            if (finished) {
                                DBUtils.selectData();
                                break;
                            }
                        } while (true);
                    } else if (data.equals("true")) {
                        DBUtils.selectData();
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}