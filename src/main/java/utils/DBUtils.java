package utils;


import java.sql.*;

public class DBUtils {


    public Connection connector() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://51.38.194.126:3306";
            String user = "shreder";
            String pass = "like1234";
            conn = DriverManager.getConnection(url, user, pass);
            conn.setAutoCommit(false);
            createSQL();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

    public void createSQL(){
        try (Connection conn = connector()) {
            // Hier SQL Befehl in den String schreiben.
            String sql = "CREATE DATABASE IF NOT EXISTS krautundrueben;\n" +
                    "USE krautundrueben;\n" +
                    "\n" +
                    "\n" +
                    "CREATE TABLE KUNDE (\n" +
                    "    KUNDENNR        INTEGER NOT NULL,\n" +
                    "    NACHNAME        VARCHAR(50),\n" +
                    "    VORNAME         VARCHAR(50),\n" +
                    "    GEBURTSDATUM\t  DATE,\n" +
                    "\t STRASSE         VARCHAR(50),\n" +
                    "\t HAUSNR\t\t\t  VARCHAR(6),\t\t\t\n" +
                    "    PLZ             VARCHAR(5),\n" +
                    "    ORT             VARCHAR(50),\n" +
                    "    TELEFON         VARCHAR(25),\n" +
                    "    EMAIL           VARCHAR(50)\n" +
                    "    );\n" +
                    "\n" +
                    "CREATE TABLE ZUTAT(\n" +
                    "    ZUTATENNR\t\t\tINTEGER NOT NULL,\n" +
                    "    BEZEICHNUNG      VARCHAR(50),\n" +
                    "    EINHEIT        \tVARCHAR (25),\n" +
                    "    NETTOPREIS       DECIMAL(10,2),\n" +
                    "    BESTAND          INTEGER,\n" +
                    "    LIEFERANT\t\t\tINTEGER,\n" +
                    "\t KALORIEN\t\t\tINTEGER,\n" +
                    "\t KOHLENHYDRATE\t\tDECIMAL (10,2),\n" +
                    "\t PROTEIN\t\t\t\tDECIMAL(10,2)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE BESTELLUNG (\n" +
                    "    BESTELLNR        INTEGER AUTO_INCREMENT NOT NULL,\n" +
                    "    KUNDENNR         INTEGER,\n" +
                    "    BESTELLDATUM     DATE,\n" +
                    "    RECHNUNGSBETRAG  DECIMAL(10,2),\n" +
                    "    PRIMARY KEY (BESTELLNR)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE BESTELLUNGZUTAT (\n" +
                    "    BESTELLNR       INTEGER NOT NULL,\n" +
                    "    ZUTATENNR       INTEGER,\n" +
                    "    MENGE     \t\t  INTEGER\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE LIEFERANT (\n" +
                    "    LIEFERANTENNR   INTEGER NOT NULL,\n" +
                    "    LIEFERANTENNAME VARCHAR(50),\n" +
                    "    STRASSE         VARCHAR(50),\n" +
                    "    HAUSNR\t\t\t  VARCHAR(6),\n" +
                    "    PLZ             VARCHAR(5),\n" +
                    "    ORT             VARCHAR(50),\n" +
                    "    TELEFON\t\t\t  VARCHAR(25),\n" +
                    "    EMAIL           VARCHAR(50)\n" +
                    ");\n" +
                    "CREATE TABLE REZEPT (\n" +
                    "REZEPTNR \t\tINTEGER NOT NULL,\n" +
                    "REZEPTNAME \t\tVARCHAR(50),\n" +
                    "KATEGORIE \t\tVARCHAR(50),\n" +
                    "GESAMTKALORIEN \tINTEGER,\n" +
                    "GESAMTKH \t\tINTEGER,\n" +
                    "GESAMTPROTEIN \tINTEGER\n" +
                    ");\n" +
                    "CREATE TABLE REZEPTZUTAT (\n" +
                    "REZEPTNR \tINTEGER NOT NULL,\n" +
                    "ZUTATENNR \tINTEGER,\n" +
                    "MENGE \t\tINTEGER\n" +
                    "\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "ALTER TABLE ZUTAT ADD PRIMARY KEY (ZUTATENNR);\n" +
                    "ALTER TABLE KUNDE ADD PRIMARY KEY (KUNDENNR);\n" +
                    "ALTER TABLE LIEFERANT ADD PRIMARY KEY (LIEFERANTENNR);\n" +
                    "ALTER TABLE BESTELLUNGZUTAT ADD PRIMARY KEY (BESTELLNR,ZUTATENNR);\n" +
                    "ALTER TABLE REZEPT ADD PRIMARY KEY (REZEPTNR);\n" +
                    "ALTER TABLE REZEPTZUTAT ADD PRIMARY KEY (REZEPTNR,ZUTATENNR);\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "ALTER TABLE ZUTAT ADD FOREIGN KEY (LIEFERANT) REFERENCES LIEFERANT (LIEFERANTENNR);\n" +
                    "ALTER TABLE BESTELLUNGZUTAT ADD FOREIGN KEY (BESTELLNR) REFERENCES BESTELLUNG (BESTELLNR);\n" +
                    "ALTER TABLE BESTELLUNG ADD FOREIGN KEY (KUNDENNR) REFERENCES KUNDE (KUNDENNR);\n" +
                    "ALTER TABLE BESTELLUNGZUTAT ADD FOREIGN KEY (ZUTATENNR) REFERENCES ZUTAT (ZUTATENNR);\n" +
                    "ALTER TABLE REZEPTZUTAT ADD FOREIGN KEY (ZUTATENNR) REFERENCES ZUTAT (ZUTATENNR);\n" +
                    "ALTER TABLE REZEPTZUTAT ADD FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR);";
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setObject(1, "Hendrik");
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("No requests");
                    } else {
                        do {
                            // rs.getObject or etc. And Column Number required.
                            rs.getString(1);
                        } while (rs.next());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recreateSQL(){

    }
}
