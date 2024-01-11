CREATE TABLE format(
    id_format VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(50)
);
CREATE SEQUENCE seq_format;

CREATE TABLE produit(
    id_produit VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(50)
);
CREATE SEQUENCE seq_produit;

CREATE TABLE quantite(
    id_produit VARCHAR(10) REFERENCES produit(id_produit),
    id_format VARCHAR(10) REFERENCES format(id_format),
    id_matiere VARCHAR(10) REFERENCES matiere(id_matiere),
    quantite int
);
CREATE SEQUENCE seq_quantite;

INSERT INTO produit VALUES('PRO1','Chic Carry');
INSERT INTO produit VALUES('PRO2','Urban Elegance');
INSERT INTO produit VALUES('PRO3','Stylish Tote');
INSERT INTO produit VALUES('PRO4','Trendy Satchel');*

INSERT INTO quantite VALUES('PRO4','1','MAT003',2);
INSERT INTO quantite VALUES('PRO4','1','MAT002',1);
INSERT INTO quantite VALUES('PRO4','2','MAT003',4);
INSERT INTO quantite VALUES('PRO4','2','MAT002',3);
