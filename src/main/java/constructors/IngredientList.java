package constructors;

import java.io.Serializable;
import java.math.BigDecimal;

public class IngredientList implements Serializable {
    private BigDecimal ingredientID;
    private String ingredientName;
    private String unit;
    private BigDecimal nettoprice;
    private int amount;
    private BigDecimal supplierID;
    private String supplierName;
    private BigDecimal calorie;
    private BigDecimal carbohydrates;
    private BigDecimal protein;

    public IngredientList(BigDecimal ingredientID, String ingredientName, String unit, BigDecimal nettoprice, int amount, BigDecimal supplierID, String supplierName, BigDecimal calorie, BigDecimal carbohydrates, BigDecimal protein) {
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

    public void setIngredientID(BigDecimal ingredientID) {
        this.ingredientID = ingredientID;
    }

    public void setNettoprice(BigDecimal nettoprice) {
        this.nettoprice = nettoprice;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(BigDecimal supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getCalorie() {
        return calorie;
    }

    public void setCalorie(BigDecimal calorie) {
        this.calorie = calorie;
    }

    public BigDecimal getIngredientID() {
        return ingredientID;
    }

    public BigDecimal getNettoprice() {
        return nettoprice;
    }

    public BigDecimal getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(BigDecimal carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

}
