package constructors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class OrderList implements Serializable {

    private BigDecimal BESTELLNR;
    private BigDecimal KUNDENNR;
    private Date BESTELLDATUM;
    private BigDecimal RECHNUNGSBETRAG;

    public OrderList(BigDecimal BESTELLNR, BigDecimal KUNDENNR, Date BESTELLDATUM, BigDecimal RECHNUNGSBETRAG) {

        this.BESTELLNR = BESTELLNR;
        this.KUNDENNR = KUNDENNR;
        this.BESTELLDATUM = BESTELLDATUM;
        this.RECHNUNGSBETRAG = RECHNUNGSBETRAG;
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
}






