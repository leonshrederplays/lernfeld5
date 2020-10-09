package commands;

public class Help {

    public void helper() {
        System.out.print("Here is a list of available commands:\n" +
                "\n" +
                "help -- shows a list of all available commands\n" +
                "ingreds / ingreds <ingredient name/ingredient ID> -- list all ingredients / lists the details of one igredient\n" +
                "recipe / recipe <recipe name/recipe ID> -- lists all recipes / lists the details of one recipe\n" +
                "orders / orders <order ID> -- lists all orders / lists the details of one order\n" +
                "customers / customers <customer ID/customer lastname firstname> -- lists all customers / lists the details of one customer\n" +
                "recreate -- recreates the Database --WARNING-- THIS WILL RESET ALL DATA TO ITS DEFAULT VALUES!\n" +
                "reload -- reloads the entire lists of ingredients, orders etc...\n" +
                "exit -- closes the program\n" +
                "\n\n");
    }

}
