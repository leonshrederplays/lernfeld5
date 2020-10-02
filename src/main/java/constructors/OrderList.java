package constructors;

import java.io.Serializable;
import java.sql.Date;

public class OrderList implements Serializable {

    private int BESTELLNR;
    private int KUNDENNR;
    private Date BESTELLDATUM;
    private double RECHNUNGSBETRAG;

    public OrderList(int BESTELLNR, int KUNDENNR, Date BESTELLDATUM, double RECHNUNGSBETRAG) {

        this.BESTELLNR = BESTELLNR;
        this.KUNDENNR = KUNDENNR;
        this.BESTELLDATUM = BESTELLDATUM;
        this.RECHNUNGSBETRAG = RECHNUNGSBETRAG;
    }

    public int getBESTELLNR() {
        return BESTELLNR;
    }

    public void setBESTELLNR(int BESTELLNR) {
        this.BESTELLNR = BESTELLNR;
    }

    public int getKUNDENNR() {
        return KUNDENNR;
    }

    public void setKUNDENNR(int KUNDENNR) {
        this.KUNDENNR = KUNDENNR;
    }

    public Date getBESTELLDATUM() {
        return BESTELLDATUM;
    }

    public void setBESTELLDATUM(Date BESTELLDATUM) {
        this.BESTELLDATUM = BESTELLDATUM;
    }

    public double getRECHNUNGSBETRAG() {
        return RECHNUNGSBETRAG;
    }

    public void setRECHNUNGSBETRAG(double RECHNUNGSBETRAG) {
        this.RECHNUNGSBETRAG = RECHNUNGSBETRAG;
    }
}






