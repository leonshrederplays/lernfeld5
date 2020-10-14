package commands;

public class Help {

    public void helper() {
        System.out.print("""
                Here is a list of available commands:

                help -- shows a list of all available commands
                ingreds / ingreds <ingredient name/ingredient ID> -- list all ingredients / lists the details of one igredient
                recipe / recipe <recipe name/recipe ID> -- lists all recipes / lists the details of one recipe
                orders / orders <order ID> -- lists all orders / lists the details of one order
                customers / customers <customer ID/customer lastname firstname> -- lists all customers / lists the details of one customer
                recreate -- recreates the Database --WARNING-- THIS WILL RESET ALL DATA TO ITS DEFAULT VALUES!
                reload -- reloads the entire lists of ingredients, orders etc...
                exit -- closes the program


                """);
    }

}
