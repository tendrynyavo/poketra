CREATE TABLE Article(
   id_article VARCHAR(50) ,
   code VARCHAR(50)  NOT NULL,
   nom VARCHAR(100) ,
   type INTEGER,
   PRIMARY KEY(id_article),
   UNIQUE(code)
);

CREATE TABLE unite (
   id_unite VARCHAR(50) PRIMARY KEY,
   nom VARCHAR(50) NOT NULL
);

CREATE TABLE article_unite (
   id SERIAL PRIMARY KEY,
   id_unite VARCHAR(50) REFERENCES unite(id_unite),
   id_article VARCHAR(50) REFERENCES article(id_article),
   value DOUBLE PRECISION NOT NULL
);

CREATE TABLE Magasin(
   id_magasin VARCHAR(50) ,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_magasin)
);

CREATE TABLE Sortie(
   id_sortie VARCHAR(50) ,
   date_sortie DATE NOT NULL,
   quantite DOUBLE PRECISION NOT NULL,
   id_magasin VARCHAR(50) ,
   id_article VARCHAR(50) ,
   status INTEGER NOT NULL DEFAULT 0,
   id_unite INTEGER REFERENCES article_unite(id),
   PRIMARY KEY(id_sortie),
   FOREIGN KEY(id_magasin) REFERENCES Magasin(id_magasin),
   FOREIGN KEY(id_article) REFERENCES Article(id_article)
);

CREATE SEQUENCE seq_sortie
   INCREMENT BY 1
   START WITH 1;

CREATE TABLE Entree(
   id_entree VARCHAR(50) ,
   date_entree DATE NOT NULL,
   quantite DOUBLE PRECISION NOT NULL,
   prix_unitaire DOUBLE PRECISION NOT NULL,
   id_magasin VARCHAR(50) ,
   id_article VARCHAR(50) ,
   id_unite INTEGER REFERENCES article_unite(id),
   PRIMARY KEY(id_entree),
   FOREIGN KEY(id_magasin) REFERENCES Magasin(id_magasin),
   FOREIGN KEY(id_article) REFERENCES Article(id_article)
);

CREATE TABLE Mouvement(
   id_sortie VARCHAR(50) ,
   id_entree VARCHAR(50) ,
   quantite DOUBLE PRECISION NOT NULL,
   PRIMARY KEY(id_sortie, id_entree),
   FOREIGN KEY(id_sortie) REFERENCES Sortie(id_sortie),
   FOREIGN KEY(id_entree) REFERENCES Entree(id_entree)
);

CREATE SEQUENCE seq_validation
   INCREMENT BY 1
   START WITH 1;

CREATE SEQUENCE s_employe
   INCREMENT BY 1
   START WITH 1;

CREATE SEQUENCE seq_entree
   INCREMENT BY 1
   START WITH 1;

CREATE TABLE Employe(
   id_employe VARCHAR(50) ,
   nom VARCHAR(100)  NOT NULL,
   prenom VARCHAR(100)  NOT NULL,
   telephone VARCHAR(50) ,
   genre VARCHAR(10)  NOT NULL,
   password VARCHAR(100)  NOT NULL,
   date_naissance DATE,
   email VARCHAR(100)  NOT NULL,
   PRIMARY KEY(id_employe)
);

CREATE TABLE validation_mouvement (
   id_validation VARCHAR(50) PRIMARY KEY,
   date DATE NOT NULL,
   id_employe VARCHAR(50) REFERENCES employe(id_employe),
   id_sortie VARCHAR(50) REFERENCES sortie(id_sortie)
);

CREATE OR REPLACE VIEW v_mouvement_validation AS
SELECT s.*, v.id_validation, v.date as date_validation, v.id_employe
FROM sortie s
   JOIN validation_mouvement v ON s.id_sortie=v.id_sortie
WHERE status >= 10;

CREATE OR REPLACE VIEW v_last_mouvement AS
SELECT id_article, id_magasin, MAX(date_validation) AS date, SUM(quantite) AS quantite
FROM v_mouvement_validation
GROUP BY id_article, id_magasin;

CREATE OR REPLACE VIEW v_unite AS 
SELECT  u.id_unite, a.id, u.nom, a.id_article, a.value
FROM article_unite a
   JOIN unite u ON a.id_unite=u.id_unite;

CREATE OR REPLACE VIEW v_last_unite AS
SELECT id_article, MIN(value) AS value
FROM v_unite
GROUP BY id_article;