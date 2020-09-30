package constructors;

import java.io.Serializable;

public class RecipeList implements Serializable {
    private int recipeID;
    private String recipeName;
    private int recipeCalories;
    private int recipeCarbs;
    private int recipeProtein;

    public RecipeList(int recipeID, String recipeName, int recipeCalories, int recipeCarbs, int recipeProtein){
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeCalories = recipeCalories;
        this.recipeCarbs = recipeCarbs;
        this.recipeProtein = recipeProtein;
    }

    public int getRecipeID() {
        return recipeID;
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

    public int getRecipeCarbs() {
        return recipeCarbs;
    }

    public void setRecipeCarbs(int recipeCarbs) {
        this.recipeCarbs = recipeCarbs;
    }

    public int getRecipeProtein() {
        return recipeProtein;
    }

    public void setRecipeProtein(int recipeProtein) {
        this.recipeProtein = recipeProtein;
    }
}
