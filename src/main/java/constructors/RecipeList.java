package constructors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RecipeList implements Serializable {
    private final BigDecimal recipeID;
    private final String recipeName;
    private final BigDecimal recipeCalories;
    private final BigDecimal recipeCarbs;
    private final BigDecimal recipeProtein;
    private List<BigDecimal> ingredients;
    private List<Integer> amount;
    private final List<BigDecimal> allergens;
    private final List<BigDecimal> categories;

    public RecipeList(BigDecimal recipeID, String recipeName, BigDecimal recipeCalories, BigDecimal recipeCarbs, BigDecimal recipeProtein, List<BigDecimal> ingredients, List<Integer> amount, List<BigDecimal> allergens, List<BigDecimal> categories){
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeCalories = recipeCalories;
        this.recipeCarbs = recipeCarbs;
        this.recipeProtein = recipeProtein;
        this.ingredients = ingredients;
        this.amount = amount;
        this.allergens = allergens;
        this.categories = categories;
    }

    public BigDecimal getRecipeID() {
        return recipeID;
    }
    

    public String getRecipeName() {
        return recipeName;
    }
    

    public List<BigDecimal> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<BigDecimal> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Integer> getAmount() {
        return amount;
    }

    public void setAmount(List<Integer> amount) {
        this.amount = amount;
    }

    public List<BigDecimal> getAllergens() {
        return allergens;
    }

    public List<BigDecimal> getCategories() {
        return categories;
    }
    
}
