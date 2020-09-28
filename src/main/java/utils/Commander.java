package utils;

public class Commander {
    public void helper(){
        System.out.print("Here is a list of available commands\n" +
                "\n" +
                "help -- shows a list of all available commands\n" +
                "ingredients / ingredients <recipe name> -- lists all available ingredients/lists the ingredients of a recipe\n" +
                "exit -- exit the program\n\n");
    }
    public void ingredients(){
        System.out.println("This function is not available yet.");
    }
    public void shutdown(){
        System.exit(0);
    }
}
