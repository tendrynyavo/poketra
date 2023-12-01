CREATE VIEW v_coupure AS
SELECT c.*, s.nom, s.id_panneau
FROM Coupure c
    JOIN Secteur s ON c.id_secteur = s.id_secteur;