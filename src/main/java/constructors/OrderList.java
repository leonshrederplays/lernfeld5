package constructors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrderList implements Serializable {

    private BigDecimal BESTELLNR;
    private BigDecimal KUNDENNR;
    private Date BESTELLDATUM;
    private BigDecimal RECHNUNGSBETRAG;
    private List<BigDecimal> INGREDS;
    private List<Integer> INGREDAMOUNT;
    private List<BigDecimal> RECIPES;
    private List<Integer> RECIPEAMOUNT;

    public OrderList(BigDecimal BESTELLNR, BigDecimal KUNDENNR, Date BESTELLDATUM, BigDecimal RECHNUNGSBETRAG, List<BigDecimal> INGREDS, List<Integer> INGREDAMOUNT, List<BigDecimal> RECIPES, List<Integer> RECIPEAMOUNT) {
        this.BESTELLNR = BESTELLNR;
        this.KUNDENNR = KUNDENNR;
        this.BESTELLDATUM = BESTELLDATUM;
        this.RECHNUNGSBETRAG = RECHNUNGSBETRAG;
        this.INGREDS = INGREDS;
        this.INGREDAMOUNT = INGREDAMOUNT;
        this.RECIPES = RECIPES;
        this.RECIPEAMOUNT = RECIPEAMOUNT;
    }

    public BigDecimal getBESTELLNR() {
        return BESTELLNR;
    }

    public void setBESTELLNR(BigDecimal BESTELLNR) {
        this.BESTELLNR = BESTELLNR;
    }

    public BigDecimal getKUNDENNR() {
        return KUNDENNR;
    }

    public void setKUNDENNR(BigDecimal KUNDENNR) {
        this.KUNDENNR = KUNDENNR;
    }

    public Date getBESTELLDATUM() {
        return BESTELLDATUM;
    }

    public void setBESTELLDATUM(Date BESTELLDATUM) {
        this.BESTELLDATUM = BESTELLDATUM;
    }

    public BigDecimal getRECHNUNGSBETRAG() {
        return RECHNUNGSBETRAG;
    }

    public void setRECHNUNGSBETRAG(BigDecimal RECHNUNGSBETRAG) {
        this.RECHNUNGSBETRAG = RECHNUNGSBETRAG;
    }

    public List<BigDecimal> getINGREDS() {
        return INGREDS;
    }

    public void setINGREDS(List<BigDecimal> INGREDS) {
        this.INGREDS = INGREDS;
    }

    public List<Integer> getINGREDAMOUNT() {
        return INGREDAMOUNT;
    }

    public void setINGREDAMOUNT(List<Integer> INGREDAMOUNT) {
        this.INGREDAMOUNT = INGREDAMOUNT;
    }

    public List<BigDecimal> getRECIPES() {
        return RECIPES;
    }

    public void setRECIPES(List<BigDecimal> RECIPES) {
        this.RECIPES = RECIPES;
    }

    public List<Integer> getRECIPEAMOUNT() {
        return RECIPEAMOUNT;
    }

    public void setRECIPEAMOUNT(List<Integer> RECIPEAMOUNT) {
        this.RECIPEAMOUNT = RECIPEAMOUNT;
    }
}






