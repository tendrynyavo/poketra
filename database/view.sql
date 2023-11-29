CREATE OR REPLACE VIEW v_mouvement AS
SELECT s.date_sortie, s.id_article, m.quantite AS sortie, s.id_magasin, m.id_entree, s.id_sortie, s.date_validation, e.quantite, e.prix_unitaire, a.id_unite, a.id, s.status
FROM mouvement m
    JOIN v_mouvement_validation s ON m.id_sortie = s.id_sortie
    JOIN entree e ON e.id_entree = m.id_entree
    JOIN article_unite a ON a.id=e.id_unite;

CREATE OR REPLACE VIEW v_somme_mouvement as
SELECT id_entree, SUM(sortie) AS quantite, MAX(date_sortie) AS date_sortie, MAX(date_validation) AS date_validation
FROM v_mouvement
GROUP BY id_entree;

CREATE OR REPLACE VIEW v_etat_stock AS
SELECT e.id_entree, e.date_entree, e.quantite as quantite, COALESCE(m.quantite, 0) AS sortie, e.prix_unitaire, e.id_magasin, e.id_article, m.date_sortie, a.code, m.date_validation
FROM v_somme_mouvement m
    RIGHT JOIN entree e ON m.id_entree = e.id_entree
    JOIN article a ON a.id_article = e.id_article
WHERE (e.quantite - COALESCE(m.quantite, 0)) > 0;

SELECT id_entree, SUM(sortie) as sortie, quantite, prix_unitaire, id_article, id_magasin, MAX(date_validation) AS date_validation, MAX(date_sortie) AS date_sortie FROM v_mouvement WHERE date_sortie <= '2021-12-15' GROUP BY id_entree, quantite, prix_unitaire, id_article, id_magasin;