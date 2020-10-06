package constructors;

import java.io.Serializable;
import java.util.List;

public class RecipeList implements Serializable {
    private int recipeID;
    private String recipeName;
    private int recipeCalories;
    private double recipeCarbs;
    private double recipeProtein;
    private List<Integer> ingredients;
    private List<Integer> amount;
    private List<String> allergens;
    private List<String> categories;

    public RecipeList(int recipeID, String recipeName, int recipeCalories, double recipeCarbs, double recipeProtein, List<Integer> ingredients, List<Integer> amount, List<String> allergens, List<String> categories){
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

    public int getRecipeID() {
        return recipeID;
    }

    public List<Integer> getIngredients() {
        return ingredients;
    }

    public void setRecipeCarbs(double recipeCarbs) {
        this.recipeCarbs = recipeCarbs;
    }

    public void setRecipeProtein(double recipeProtein) {
        this.recipeProtein = recipeProtein;
    }

    public void setIngredients(List<Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public void setAmount(List<Integer> amount) {
        this.amount = amount;
    }

    public List<Integer> getAmount() {
        return amount;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeCalories() {
        return recipeCalories;
    }

    public void setRecipeCalories(int recipeCalories) {
        this.recipeCalories = recipeCalories;
    }

    public double getRecipeCarbs() {
        return recipeCarbs;
    }

    public void setRecipeCarbs(int recipeCarbs) {
        this.recipeCarbs = recipeCarbs;
    }

    public double getRecipeProtein() {
        return recipeProtein;
    }

    public void setRecipeProtein(int recipeProtein) {
        this.recipeProtein = recipeProtein;
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
