package commands;

public class Shutdown {

    public static void shutdown(){
        System.out.println("Shutting down...");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
