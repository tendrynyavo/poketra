CREATE TABLE employees(
    id_employees VARCHAR(10) PRIMARY KEY,
    poste VARCHAR(255),
    salaire DOUBLE PRECISION
);

CREATE SEQUENCE empl_seq;

SELECT id_produit, id_format, prix, (prix * 1.5) AS prix_final, ((prix * 1.5) - prix ) AS benefice FROM v_produit_prix;
SELECT * FROM produit;

CREATE OR REPLACE FUNCTION calculate_benefice()