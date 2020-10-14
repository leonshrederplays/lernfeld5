package commands;

import utils.DBUtils;

import java.util.Scanner;

public class Recreate {

    public void recreate() {
        Scanner recreateInput = new Scanner(System.in);
        System.out.println("This will reset the Database to its default state. Are you sure? (Y/N)");
        String confirm = recreateInput.next();
        if (confirm.equalsIgnoreCase("y")){
            DBUtils.recreateSQL();
            DBUtils.selectData();
            System.out.println("Database successfully recreated. Default values were restored.");
        } else if (confirm.equalsIgnoreCase("n")){
            System.out.println("Databse was not recreated.");
        }
    }

}
