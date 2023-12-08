CREATE VIEW v_coupure_panneau AS
SELECT c.*, s.nom AS nom_secteur, p.*
FROM Coupure c
    JOIN Secteur s ON c.id_secteur = s.id_secteur
    JOIN Panneau p ON s.id_panneau = p.id_panneau;