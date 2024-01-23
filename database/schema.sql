CREATE TABLE employees(
    id_employee VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    date DATE,
    taux_horaire DOUBLE PRECISION
);
CREATE TABLE categories(
    id_categorie VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(255),
    salaire DOUBLE PRECISION
);
CREATE TABLE effectifs(
    id_effectif VARCHAR(10) PRIMARY KEY,
    id_categorie VARCHAR(10) REFERENCES categories(id_categorie),
    id_produit VARCHAR(10) REFERENCES produit(id_produit),
    id_format VARCHAR(10) REFERENCES format(id_format),
    nombre INT DEFAULT 0
);
CREATE TABLE produit_format_prix(
    id_produit VARCHAR(10) REFERENCES produit(id_produit),
    id_format VARCHAR(10) REFERENCES format(id_format),
    prix_de_vente DOUBLE PRECISION
);
ALTER TABLE produit ADD COLUMN duree INT DEFAULT 0;
CREATE SEQUENCE empl_seq;
CREATE SEQUENCE cat_seq;
CREATE SEQUENCE eff_seq;
SELECT id_produit, id_format, prix, (prix * 1.5) AS prix_final, ((prix * 1.5) - prix ) AS benefice FROM v_produit_prix;
SELECT * FROM produit;
ALTER TABLE quantite ADD COLUMN id_quantite VARCHAR(10) PRIMARY KEY;
CREATE SEQUENCE seq_quantite;
CREATE TABLE experience(
    id_experience VARCHAR(50) PRIMARY KEY,
    designation VARCHAR(255),
    debut INTEGER,
    fin INTEGER,
    augmentation DOUBLE PRECISION
);
ALTER TABLE experience ALTER COLUMN fin TYPE NUMERIC;
CREATE SEQUENCE exp_seq;
ALTER TABLE employees ADD COLUMN id_categorie VARCHAR(50) REFERENCES categories(id_categorie);