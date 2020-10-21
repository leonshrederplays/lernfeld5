package commands;

public class Help {

    public void helper() {
        System.out.println(" ");
        System.out.print("""
                      Here is a list of available commands:

                      help -- shows a list of all available commands
                      ingreds / ingreds <ingredient name/ingredient ID> -- list all ingredients / lists the details of one igredient
                      ingreds unused / ingreds unused -- lists all ingredients that are not used by any recipe
                      recipe / recipe <recipe name/recipe ID> -- lists all recipes / lists the details of one recipe
                      recipe ingred / recipe ingred <ingredient ID / ingredient name> -- lists all recipes of one ingredient
                      recipe amount / recipe amount <integer> -- lists all recipes with less or equal amount of ingredients
                      orders / orders <order ID> -- lists all orders / lists the details of one order
                      customers / customers <customer ID / customer lastname firstname> -- lists all customers / lists the details of one customer
                      categories / categories <category ID / category name> -- lists all categories / lists all recipes of one category
                      allergens / allergens <allergen ID / allergen name> -- lists all allergens / lists all recipes of one allergen
                      addrecipe -- adds a recipe		
                      buy recipe <recipe ID / recipe name> -- buys a recipe (IN BETA PHASE)
                      buy ingred <ingredient ID / ingredient name> -- buys an ingredient (IN BETA PHASE)
                      recreate -- recreates the Database --WARNING-- THIS WILL RESET ALL DATA TO ITS DEFAULT VALUES!
                      reload -- reloads the entire lists of ingredients, orders etc...
                      exit -- closes the program

                      """);
    }

}
