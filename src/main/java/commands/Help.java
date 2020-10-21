package commands;

public class Help {

    public void helper() {
        System.out.println(" ");
        System.out.print("""
                      Here is a list of available commands:

                      help -- shows a list of all available commands
                      ingreds / ingreds <ingredient name/ingredient ID> -- list all ingredients / lists the details of one igredient
                      recipe / recipe <recipe name/recipe ID> -- lists all recipes / lists the details of one recipe
                      orders / orders <order ID> -- lists all orders / lists the details of one order
                      customers / customers <customer ID / customer lastname firstname> -- lists all customers / lists the details of one customer
                      categories / categories <category ID / category name> -- lists all categories / lists all recipes of one category
                      addrecipe -- adds a recipe		
                      buy recipe <recipe ID / recipe name> -- buys a recipe
                      buy ingred <ingredient ID / ingredient name> -- buys an ingredient
                      recreate -- recreates the Database --WARNING-- THIS WILL RESET ALL DATA TO ITS DEFAULT VALUES!
                      reload -- reloads the entire lists of ingredients, orders etc...
                      exit -- closes the program

                      """);
    }

}
