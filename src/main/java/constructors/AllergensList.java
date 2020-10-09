package constructors;

import java.math.BigDecimal;

public class AllergensList {

    private BigDecimal allergenID;
    private String allergen;

    public AllergensList(BigDecimal allergenID, String allergen) {
        this.allergenID = allergenID;
        this.allergen = allergen;
    }

    public BigDecimal getAllergenID() {
        return allergenID;
    }

    public void setAllergenID(BigDecimal allergenID) {
        this.allergenID = allergenID;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }
}
