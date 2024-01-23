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

SELECT
    e.id_employee,
    e.nom,
    e.prenom,
    exp.designation AS poste,
    EXTRACT(YEAR FROM AGE(NOW(),e.date)) AS anciennete,
    (e.taux_horaire * anciennete) AS taux_horaire
FROM
    employees e
JOIN
    experience exp
ON
    EXTRACT(YEAR FROM AGE(NOW(),e.date)) BETWEEN debut AND fin;