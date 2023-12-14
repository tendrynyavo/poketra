select *
from meteo m
	join coupure c on m.date_meteo = c.date_coupure;

create view v_coupure as
SELECT c.*, s.nom AS nom_secteur, p.*
FROM Coupure c
    JOIN Secteur s ON c.id_secteur = s.id_secteur
    JOIN Panneau p ON s.id_panneau = p.id_panneau;
    
select id_coupure, heure, date_coupure , c.id_coupure , consommation , id_salle, nom
from coupure c 
	join salle s on s.id_secteur = c.id_secteur;
	
create view v_coupure_salle as
select id_coupure, s2.nom, heure, date_coupure , consommation , id_salle, s.nom as nom_salle, c.id_secteur, s2.id_panneau , p.nom as nom_panneau, p.capacite , p.puissance 
from salle s 
	join secteur s2 on s.id_secteur = s2.id_secteur 
	join panneau p on p.id_panneau = s2.id_panneau 
	join coupure c on s.id_secteur = c.id_secteur
order by id_coupure ;

select s.id_secteur, avg(nombre)  from pointage p 
	join salle s on s.id_salle = p.id_salle  
	where extract(ISODOW from p.date_pointage) = 1
		group by s.id_secteur ;
		
select *  from pointage p 
	join salle s on s.id_salle = p.id_salle  
	where extract(ISODOW from p.date_pointage) = 1;
	

create or replace view v_moyenne_nombre_salle as
SELECT id_salle, date_pointage, AVG(nombre) as moyenne
FROM Pointage
GROUP BY id_salle, date_pointage;

SELECT *
FROM v_coupure_salle c
    JOIN v_moyenne_nombre_salle m on c.id_salle = m.id_salle and c.date_coupure = m.date_pointage ;

create or replace view v_coupure_moyenne_nombre as
SELECT id_coupure, heure, date_coupure, id_secteur, consommation, SUM(moyenne) as moyenne
FROM v_coupure_salle c
    JOIN v_moyenne_nombre_salle m on c.id_salle = m.id_salle and c.date_coupure = m.date_pointage 
GROUP BY id_coupure, heure, date_coupure, id_secteur, consommation;

SELECT *
FROM v_coupure c
    JOIN meteo m ON c.date_meteo = c.date_coupure
    JOIN pointage p ON p.date_pointage = c.date_coupure;