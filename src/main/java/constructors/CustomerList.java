package constructors;

import java.io.Serializable;

public class CustomerList implements Serializable {
    private int     KUNDENNR;
    private String  NACHNAME;
    private String  VORNAME;
    private String  GEBURTSDATUM;
    private String  STRASSE;
    private int     HAUSNR;
    private int     PLZ;
    private String  ORT;
    private String  TELEFON;
    private String  EMAIL;

    public CustomerList(int KUNDENNR,String NACHNAME, String VORNAME, String GEBURTSDATUM, String STRASSE, int HAUSNR, int PLZ, String ORT, String TELEFON, String EMAIL) {
        this.KUNDENNR = KUNDENNR;
        this.NACHNAME = NACHNAME;
        this.VORNAME = VORNAME;
        this.GEBURTSDATUM = GEBURTSDATUM;
        this.STRASSE = STRASSE;
        this.HAUSNR = HAUSNR;
        this.PLZ = PLZ;
        this.ORT = ORT;
        this.TELEFON = TELEFON;
        this.EMAIL = EMAIL;

    }

    public int getKUNDENNR() {
        return KUNDENNR;
    }

    public void setKUNDENNR(int KUNDENNR) {
        this.KUNDENNR = KUNDENNR;
    }

    public String getNACHNAME() {
        return NACHNAME;
    }

    public void setNACHNAME(String NACHNAME) {
        this.NACHNAME = NACHNAME;
    }

    public String getVORNAME() {
        return VORNAME;
    }

    public void setVORNAME(String VORNAME) {
        this.VORNAME = VORNAME;
    }

    public String getGEBURTSDATUM() {
        return GEBURTSDATUM;
    }

    public void setGEBURTSDATUM(String GEBURTSDATUM) {
        this.GEBURTSDATUM = GEBURTSDATUM;
    }

    public String getSTRASSE() {
        return STRASSE;
    }

    public void setSTRASSE(String STRASSE) {
        this.STRASSE = STRASSE;
    }

    public int getHAUSNR() {
        return HAUSNR;
    }

    public void setHAUSNR(int HAUSNR) {
        this.HAUSNR = HAUSNR;
    }

    public int getPLZ() {
        return PLZ;
    }

    public void setPLZ(int PLZ) {
        this.PLZ = PLZ;
    }

    public String getORT() {
        return ORT;
    }

    public void setORT(String ORT) {
        this.ORT = ORT;
    }

    public String getTELEFON() {
        return TELEFON;
    }

    public void setTELEFON(String TELEFON) {
        this.TELEFON = TELEFON;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
}
