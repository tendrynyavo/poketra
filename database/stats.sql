CREATE SEQUENCE genre_seq;
CREATE TABLE genres (
    id_genre VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(10)
);
CREATE SEQUENCE client_seq;
CREATE TABLE client (
    id_client VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(255),
    id_genre VARCHAR(10) REFERENCES genres(id_genre)
);
CREATE SEQUENCE achat_seq;
CREATE TABLE achats(
    id_achat VARCHAR(10) PRIMARY KEY,
    id_client VARCHAR(10) REFERENCES client(id_client),
    id_produit VARCHAR(10) REFERENCES produit(id_produit),
    id_format VARCHAR(10) REFERENCES format(id_format),
    date DATE,
    quantite INT
);
ALTER TABLE achats ADD COLUMN id_format VARCHAR(10) REFERENCES format(id_format);
CREATE VIEW v_initial AS
SELECT
    p.id_produit,
    f.id_format,
    g.id_genre,
    0 AS nombre
FROM
    produit p, format f,genres g;
CREATE VIEW v_stat_produit_format_genre AS
SELECT
    c.id_genre,
    a.id_produit,
    a.id_format,
    SUM(a.quantite) AS nombre
FROM
    achats a
JOIN
    client c
ON
    a.id_client = c.id_client
GROUP BY
    c.id_genre,
    a.id_produit,
    a.id_format;
CREATE VIEW v_stat_initiale AS
SELECT
    vi.id_produit,
    vi.id_format,
    vi.id_genre,
    vi.nombre 
FROM    
    v_initial vi
UNION ALL
SELECT
    vspfg.id_produit,
    vspfg.id_format,
    vspfg.id_genre,
    vspfg.nombre 
FROM
    v_stat_produit_format_genre vspfg;