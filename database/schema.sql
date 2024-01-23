CREATE TABLE employees(
    id_employee VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(255)
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


CREATE VIEW v_produit AS
SELECT 
    p.id_produit,
    p.nom,
    pfp.id_format,
    p.duree,
    pfp.prix_de_vente
FROM 
    produit p
JOIN
    produit_format_prix pfp
ON
    p.id_produit = pfp.id_produit;

CREATE VIEW v_produit_effectif AS
SELECT
    e.id_effectif,
    e.id_categorie,
    e.id_produit,
    e.id_format,
    e.nombre,
    p.duree,
    p.prix_de_vente
FROM
    effectifs e
JOIN
    v_produit p
ON
    e.id_produit = p.id_produit AND
    e.id_format = p.id_format;

CREATE VIEW v_produit_effectif_prix AS
SELECT
    pe.id_effectif,
    pe.id_categorie,
    pe.id_produit,
    pe.id_format,
    pp.prix,
    pe.nombre,
    pe.duree,
    pe.prix_de_vente
FROM
    v_produit_effectif pe
JOIN
    v_produit_prix pp
ON
    pe.id_produit = pp.id_produit AND
    pe.id_format = pp.id_format;

INSERT INTO categories(id_categorie,nom,salaire) VALUES('CAT2','transporteur',ROUND(CAST(RANDOM() * (300) + 900 AS NUMERIC)));

INSERT INTO effectifs(id_effectif,id_categorie,id_produit,id_format,nombre) VALUES('EFF3','CAT2','PRO1','1',ROUND(CAST(RANDOM() * (5) + 2 AS NUMERIC)));
INSERT INTO effectifs(id_effectif,id_categorie,id_produit,id_format,nombre) VALUES('EFF4','CAT2','PRO1','2',10);
UPDATE produit SET duree = ROUND(CAST(RANDOM() * (5) + 10 AS NUMERIC)) WHERE id_produit = 'PRO2';
UPDATE produit SET duree = ROUND(CAST(RANDOM() * (5) + 10 AS NUMERIC)) WHERE id_produit = 'PRO3';
UPDATE produit SET duree = ROUND(CAST(RANDOM() * (5) + 10 AS NUMERIC)) WHERE id_produit = 'PRO4';

UPDATE categories SET salaire = ROUND(CAST(RANDOM() * (20) + 5 AS NUMERIC)) WHERE id_categorie = 'CAT2';

ALTER TABLE quantite ADD COLUMN id_quantite VARCHAR(10) PRIMARY KEY;

CREATE SEQUENCE seq_quantite;

INSERT INTO produit_format_prix(id_produit,id_format,prix_de_vente)
SELECT
    'PRO' || LPAD(CAST(ROW_NUMBER() OVER () AS TEXT),1,'0') AS id_produit,
    '2',
    ROUND(CAST(RANDOM() * (30000) + 30000 AS NUMERIC)) AS prix_de_vente
FROM generate_series(1,4);

CREATE VIEW v_benefice AS
SELECT 
    id_produit,
    id_format,
    prix_de_vente,
    (SUM(c.salaire * nombre)) * duree AS salaire,
    prix,
    (SUM(c.salaire * nombre) * duree + prix) AS depense,
    (prix_de_vente - (SUM(c.salaire * nombre) * duree + prix)) AS benefice 
FROM
    v_produit_effectif_prix pep
JOIN
    categories c
ON
    pep.id_categorie = c.id_categorie
GROUP BY
    id_produit,
    id_format,
    prix_de_vente,
    duree,
    prix;