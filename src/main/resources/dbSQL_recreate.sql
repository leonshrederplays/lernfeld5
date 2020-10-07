DROP DATABASE IF EXISTS krautundrueben;
CREATE DATABASE IF NOT EXISTS krautundrueben;
USE krautundrueben;

CREATE TABLE IF NOT EXISTS krautundrueben.KUNDE
(
    KUNDENNR     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    NACHNAME     VARCHAR(50),
    VORNAME      VARCHAR(50),
    GEBURTSDATUM DATE,
    STRASSE      VARCHAR(50),
    HAUSNR       VARCHAR(6),
    PLZ          VARCHAR(5),
    ORT          VARCHAR(50),
    TELEFON      VARCHAR(25),
    EMAIL        VARCHAR(50),
    PRIMARY KEY (KUNDENNR)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS krautundrueben.LIEFERANT
(
    LIEFERANTENNR   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    LIEFERANTENNAME VARCHAR(50),
    STRASSE         VARCHAR(50),
    HAUSNR          VARCHAR(6),
    PLZ             VARCHAR(5),
    ORT             VARCHAR(50),
    TELEFON         VARCHAR(25),
    EMAIL           VARCHAR(50),
    PRIMARY KEY (LIEFERANTENNR)
) ENGINE = InnoDB
  AUTO_INCREMENT = 20000;

CREATE TABLE IF NOT EXISTS krautundrueben.ZUTAT
(
    ZUTATENNR     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    BEZEICHNUNG   VARCHAR(50),
    EINHEIT       VARCHAR(25),
    NETTOPREIS    DECIMAL(10, 2),
    BESTAND       INTEGER,
    LIEFERANTENNR BIGINT UNSIGNED NOT NULL,
    KALORIEN      DECIMAL,
    KOHLENHYDRATE DECIMAL(10, 2),
    PROTEIN       DECIMAL(10, 2),
    PRIMARY KEY (ZUTATENNR),
    CONSTRAINT ZUTAT_IBFK_1
        FOREIGN KEY (LIEFERANTENNR) REFERENCES LIEFERANT (LIEFERANTENNR),
    INDEX LIEFERANTENNR (LIEFERANTENNR)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000;

CREATE TABLE IF NOT EXISTS krautundrueben.BESTELLUNG
(
    BESTELLNR       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    KUNDENNR        BIGINT UNSIGNED,
    BESTELLDATUM    DATE,
    RECHNUNGSBETRAG DECIMAL(10, 2),
    PRIMARY KEY (BESTELLNR),
    CONSTRAINT BESTELLUNG_ibfk_1
        FOREIGN KEY (KUNDENNR) REFERENCES KUNDE (KUNDENNR),
    INDEX KUNDENNR (KUNDENNR)
) ENGINE = InnoDB
  AUTO_INCREMENT = 50000;

CREATE TABLE IF NOT EXISTS krautundrueben.BESTELLUNGZUTAT
(
    BESTELLNR BIGINT UNSIGNED NOT NULL,
    ZUTATENNR BIGINT UNSIGNED,
    MENGE     INTEGER,
    PRIMARY KEY (BESTELLNR, ZUTATENNR),
    CONSTRAINT BESTELLUNGUTAT_ibfk_1
        FOREIGN KEY (BESTELLNR) REFERENCES BESTELLUNG (BESTELLNR),
    CONSTRAINT BESTELLUNGZUTAT_ibfk_2
        FOREIGN KEY (ZUTATENNR) REFERENCES ZUTAT (ZUTATENNR),
    INDEX ZUTATENNR (ZUTATENNR)
);

CREATE TABLE IF NOT EXISTS krautundrueben.REZEPT
(
    REZEPTNR       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    REZEPTNAME     VARCHAR(50),
    GESAMTKALORIEN DECIMAL,
    GESAMTKH       DECIMAL,
    GESAMTPROTEIN  DECIMAL,
    GESAMTPREIS    DECIMAL,
    PRIMARY KEY (REZEPTNR)
) ENGINE = InnoDB
  AUTO_INCREMENT = 30000;

CREATE TABLE IF NOT EXISTS krautundrueben.REZEPTZUTAT
(
    REZEPTNR  BIGINT UNSIGNED NOT NULL,
    ZUTATENNR BIGINT UNSIGNED,
    MENGE     INTEGER,
    PRIMARY KEY (REZEPTNR, ZUTATENNR),
    CONSTRAINT REZEPTZUTAT_ibfk_1
        FOREIGN KEY (ZUTATENNR) REFERENCES ZUTAT (ZUTATENNR),
    CONSTRAINT REZEPTZUTAT_ibfk_2
        FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR),
    INDEX ZUTATENNR (ZUTATENNR)
);

CREATE TABLE IF NOT EXISTS krautundrueben.KATEGORIEN
(
    KATEGORIENR BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    KATEGORIE   VARCHAR(50),
    PRIMARY KEY (KATEGORIENR)
) ENGINE = InnoDB
  AUTO_INCREMENT = 47500;

CREATE TABLE IF NOT EXISTS krautundrueben.ALLERGENE
(
    ALLERGENNR BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    ALLERGEN   VARCHAR(50),
    PRIMARY KEY (ALLERGENNR)
) ENGINE = InnoDB
  AUTO_INCREMENT = 45000;

CREATE TABLE IF NOT EXISTS krautundrueben.REZEPTKATEGORIEN
(
    REZEPTNR  BIGINT UNSIGNED NOT NULL,
    KATEGORIENR BIGINT UNSIGNED,
    CONSTRAINT REZEPTKATEGORIE_ibfk_1
        FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR),
    CONSTRAINT  REZEPTKATEGORIE_ibfk_2
        FOREIGN KEY (KATEGORIENR) REFERENCES KATEGORIEN (KATEGORIENR),
    INDEX REZEPTNR (REZEPTNR)
);

CREATE TABLE IF NOT EXISTS krautundrueben.REZEPTALLERGENE
(
    REZEPTNR  BIGINT UNSIGNED NOT NULL,
    ALLERGENNR BIGINT UNSIGNED,
    CONSTRAINT REZEPTALLERGENE_ibfk_1
        FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR),
    CONSTRAINT REZEPTALLERGENE_ibfk_2
        FOREIGN KEY (ALLERGENNR) REFERENCES ALLERGENE (ALLERGENNR),
    INDEX REZEPTNR (REZEPTNR)
);

CREATE TABLE IF NOT EXISTS krautundrueben.BESTELLUNGREZEPT
(
    BESTELLNR BIGINT UNSIGNED,
    REZEPTNR  BIGINT UNSIGNED NOT NULL,
    MENGE     INTEGER,
    PRIMARY KEY (BESTELLNR, REZEPTNR),
    CONSTRAINT BESTELLUNGREZEPT_ibfk_1
        FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR),
    CONSTRAINT BESTELLUNGREZEPT_ibfk_2
        FOREIGN KEY (BESTELLNR) REFERENCES BESTELLUNG (BESTELLNR)
);

CREATE INDEX REZEPTNR
    ON BESTELLUNGREZEPT (REZEPTNR);
