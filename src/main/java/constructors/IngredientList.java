package constructors;

import java.io.Serializable;
import java.math.BigDecimal;

public class IngredientList implements Serializable {
    private int ingredientID;
    private String ingredientName;
    private String unit;
    private double nettoprice;
    private int amount;
    private int supplierID;
    private String supplierName;
    private int calorie;
    private double carbohydrates;
    private BigDecimal protein;

    public IngredientList(int ingredientID, String ingredientName, String unit, double nettoprice, int amount, int supplierID, String supplierName, int calorie, double carbohydrates, BigDecimal protein) {
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.unit = unit;
        this.nettoprice = nettoprice;
        this.amount = amount;
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.calorie = calorie;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
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

    public double getNettoprice() {
        return nettoprice;
    }

    public void setNettoprice(double nettoprice) {
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

}
