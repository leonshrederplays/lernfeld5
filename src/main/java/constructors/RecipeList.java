package constructors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RecipeList implements Serializable {
    private BigDecimal recipeID;
    private String recipeName;
    private BigDecimal recipeCalories;
    private BigDecimal recipeCarbs;
    private BigDecimal recipeProtein;
    private List<BigDecimal> ingredients;
    private List<Integer> amount;
    private List<String> allergens;
    private List<String> categories;

    public RecipeList(BigDecimal recipeID, String recipeName, BigDecimal recipeCalories, BigDecimal recipeCarbs, BigDecimal recipeProtein, List<BigDecimal> ingredients, List<Integer> amount, List<String> allergens, List<String> categories){
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

    public void setRecipeID(BigDecimal recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public BigDecimal getRecipeCalories() {
        return recipeCalories;
    }

    public void setRecipeCalories(BigDecimal recipeCalories) {
        this.recipeCalories = recipeCalories;
    }

    public BigDecimal getRecipeCarbs() {
        return recipeCarbs;
    }

    public void setRecipeCarbs(BigDecimal recipeCarbs) {
        this.recipeCarbs = recipeCarbs;
    }

    public BigDecimal getRecipeProtein() {
        return recipeProtein;
    }

    public void setRecipeProtein(BigDecimal recipeProtein) {
        this.recipeProtein = recipeProtein;
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

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
