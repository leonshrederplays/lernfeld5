package constructors;

import java.io.Serializable;

public class IngredientList implements Serializable {
    private int ingredientID;
    private String ingredientName;
    private String unit;
    private int nettoprice;
    private int amount;
    private int supplierID;
    private int calorie;
    private int carbohydrates;
    private int protein;

    public IngredientList(int ingredientID, String ingredientName, String unit, int nettoprice, int amount, int supplierID, int calorie, int carbohydrates, int protein) {
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.unit = unit;
        this.nettoprice = nettoprice;
        this.amount = amount;
        this.supplierID = supplierID;
        this.calorie = calorie;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getNettoprice() {
        return nettoprice;
    }

    public void setNettoprice(int nettoprice) {
        this.nettoprice = nettoprice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

}
