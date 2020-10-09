package constructors;

import java.math.BigDecimal;

public class CategoriesList {

    private BigDecimal categoryID;
    private String category;

    public CategoriesList(BigDecimal categoryID, String category) {
        this.categoryID = categoryID;
        this.category = category;
    }

    public BigDecimal getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(BigDecimal categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
