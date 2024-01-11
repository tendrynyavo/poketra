CREATE TABLE look (
   id_look SERIAL PRIMARY KEY,
   nom VARCHAR(50) NOT NULL
);

CREATE TABLE matiere (
   id_matiere VARCHAR(10) PRIMARY KEY,
   nom VARCHAR(50) NOT NULL
);

CREATE TABLE look_matiere (
   id SERIAL PRIMARY KEY,
   id_look INT REFERENCES look(id_look),
   id_matiere INT REFERENCES matiere(id_matiere)
);

INSERT INTO look(nom) VALUES 
('Debraille');

INSERT INTO matiere(id_matiere,nom) VALUES
('MAT001','Cuir'),
('MAT002','Coton'),
('MAT003','Soga'),
('MAT004','Lin');

INSERT INTO look_matiere(id_look, id_matiere) VALUES
(1,1),
(1,2),
(2,2),
(3,4);

CREATE VIEW v_look_matiere AS 
SELECT id,look.id_look,look.nom as look,matiere.id_matiere,matiere.nom as matiere
FROM look_matiere 
JOIN matiere ON matiere.id_matiere = look_matiere.id_matiere
JOIN look ON look.id_look = look_matiere.id_look;

ALTER TABLE matiere ADD COLUMN prix_unitaire DOUBLE PRECISION DEFAULT 0;

UPDATE matiere SET prix_unitaire = 1000;

INSERT INTO format VALUES(1,'PM');
INSERT INTO format VALUES(2,'GM');