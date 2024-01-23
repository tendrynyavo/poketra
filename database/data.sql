INSERT INTO produit_format_prix(id_produit,id_format,prix_de_vente)
SELECT
    'PRO' || LPAD(CAST(ROW_NUMBER() OVER () AS TEXT),1,'0') AS id_produit,
    '2',
    ROUND(CAST(RANDOM() * (30000) + 30000 AS NUMERIC)) AS prix_de_vente
FROM generate_series(1,4);
INSERT INTO categories(id_categorie,nom,salaire) VALUES('CAT2','transporteur',ROUND(CAST(RANDOM() * (300) + 900 AS NUMERIC)));
INSERT INTO effectifs(id_effectif,id_categorie,id_produit,id_format,nombre) VALUES('EFF3','CAT2','PRO1','1',ROUND(CAST(RANDOM() * (5) + 2 AS NUMERIC)));
INSERT INTO effectifs(id_effectif,id_categorie,id_produit,id_format,nombre) VALUES('EFF4','CAT2','PRO1','2',10);
UPDATE produit SET duree = ROUND(CAST(RANDOM() * (5) + 10 AS NUMERIC)) WHERE id_produit = 'PRO2';
UPDATE produit SET duree = ROUND(CAST(RANDOM() * (5) + 10 AS NUMERIC)) WHERE id_produit = 'PRO3';
UPDATE produit SET duree = ROUND(CAST(RANDOM() * (5) + 10 AS NUMERIC)) WHERE id_produit = 'PRO4';
UPDATE categories SET salaire = ROUND(CAST(RANDOM() * (20) + 5 AS NUMERIC)) WHERE id_categorie = 'CAT2';