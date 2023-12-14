CREATE TABLE Meteo(
   id_meteo VARCHAR(50) ,
   date_meteo DATE NOT NULL,
   debut TIME NOT NULL,
   fin TIME,
   luminosite INTEGER NOT NULL,
   PRIMARY KEY(id_meteo)
);

CREATE TABLE Panneau(
   id_panneau VARCHAR(50) ,
   nom VARCHAR(100)  NOT NULL,
   capacite DOUBLE PRECISION NOT NULL,
   puissance DOUBLE PRECISION NOT NULL,
   PRIMARY KEY(id_panneau)
);

CREATE TABLE Secteur(
   id_secteur VARCHAR(50) ,
   nom VARCHAR(100)  NOT NULL,
   id_panneau VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_secteur),
   FOREIGN KEY(id_panneau) REFERENCES Panneau(id_panneau)
);

CREATE TABLE Salle(
   id_salle VARCHAR(50) ,
   nom VARCHAR(100)  NOT NULL,
   id_secteur VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_salle),
   FOREIGN KEY(id_secteur) REFERENCES Secteur(id_secteur)
);

CREATE TABLE Coupure(
   id_coupure VARCHAR(50) ,
   heure TIME,
   date_coupure DATE,
   id_secteur VARCHAR(50)  NOT NULL,
   consommation DOUBLE PRECISION NOT NULL 0,
   PRIMARY KEY(id_coupure),
   FOREIGN KEY(id_secteur) REFERENCES Secteur(id_secteur)
);

CREATE TABLE Pointage(
   id_pointage VARCHAR(50) ,
   date_pointage DATE,
   debut TIME,
   fin TIME,
   nombre INTEGER NOT NULL,
   id_salle VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_pointage),
   FOREIGN KEY(id_salle) REFERENCES Salle(id_salle)
);