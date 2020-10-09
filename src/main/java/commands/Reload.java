package commands;

import utils.DBUtils;

public class Reload {

    public static void reload() {
        try {
            DBUtils.selectData();
            System.out.println("Successfully reloaded all Lists");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }

}
