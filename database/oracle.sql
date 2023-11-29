-- Create tables for Oracle
CREATE TABLE Article(
   id_article VARCHAR2(50) PRIMARY KEY,
   code VARCHAR2(50) UNIQUE NOT NULL,
   nom VARCHAR2(100),
   type NUMBER,
   unite VARCHAR2(50) NOT NULL
);

CREATE TABLE Magasin(
   id_magasin VARCHAR2(50) PRIMARY KEY,
   nom VARCHAR2(50) NOT NULL
);

CREATE SEQUENCE s_entree
   INCREMENT BY 1
   START WITH 1;

CREATE SEQUENCE s_sortie
   INCREMENT BY 1
   START WITH 1;

CREATE TABLE Mouvement(
   id_mouvement VARCHAR2(50) PRIMARY KEY,
   date_mouvement DATE NOT NULL,
   prix_unitaire NUMBER(10,2) DEFAULT 0 NOT NULL,
   quantite NUMBER(10,2) DEFAULT 0 NOT NULL,
   id_article VARCHAR2(50) REFERENCES Article(id_article),
   id_magasin VARCHAR2(50) REFERENCES Magasin(id_magasin),
   status NUMBER DEFAULT 0 NOT NULL,
   source VARCHAR2(50) REFERENCES Mouvement(id_mouvement),
   parent VARCHAR2(50) REFERENCES Mouvement(id_mouvement),
   type NUMBER NOT NULL
);

CREATE SEQUENCE s_employe START WITH 1 INCREMENT BY 1;

CREATE TABLE Employe(
   id_employe VARCHAR2(50) PRIMARY KEY,
   nom VARCHAR2(100) NOT NULL,
   prenom VARCHAR2(100) NOT NULL,
   telephone VARCHAR2(50),
   genre VARCHAR2(10) NOT NULL,
   password VARCHAR2(100) NOT NULL,
   date_naissance DATE,
   email VARCHAR2(100) NOT NULL
);

CREATE TABLE validation_mouvement (
   id_validation VARCHAR2(50) PRIMARY KEY,
   date_validation DATE NOT NULL,
   id_employe VARCHAR2(50) REFERENCES Employe(id_employe),
   id_mouvement VARCHAR2(50) REFERENCES Mouvement(id_mouvement)
);

-- Create views for Oracle
CREATE OR REPLACE VIEW v_sortie AS
SELECT *
FROM Mouvement
WHERE type = 2;

CREATE OR REPLACE VIEW v_entree AS
SELECT *
FROM Mouvement
WHERE type = 1;
