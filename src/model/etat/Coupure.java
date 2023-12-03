package model.etat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import connection.BddObject;
import model.meteo.Meteo;
import model.pointage.Pointage;
import model.secteur.Salle;
import model.secteur.Secteur;

public class Coupure extends Secteur {

    Time heure;
    Date date;
    double consommation;

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }

    public Time getHeure() {
        return heure;
    }

    public LocalTime getHeureToLocalTime() {
        return heure.toLocalTime();
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Coupure() throws Exception {
        super();
        this.getColumns().get(1).setName("date_coupure");
        this.setTable("v_coupure");
        this.setPrimaryKeyName("id_secteur");
        this.setConnection("PostgreSQL");
    }

    public EtatSolaire getEtatSolaire(Meteo meteo, Pointage pointage, int decallage) throws Exception {
        // Initialisation de la consommation
        this.setConsommation(60);

        EtatSolaire etat = this.getEtatSolaire(this.getDate(), meteo, pointage, this.getConsommation(), decallage);
        if (etat.getHeureCoupure().compareTo(this.getHeure().toLocalTime()) == 0) return etat;
        
        double p = (etat.getHeureCoupure().compareTo(this.getHeure().toLocalTime()) < 0) ? -0.0001 : 0.0001;
        int millis = this.getHeure().toLocalTime().toSecondOfDay();
        int coupure = etat.getHeureCoupure().toSecondOfDay();
        while (Math.abs(millis - coupure) >= 6000) {
            this.setConsommation(this.getConsommation() + p);
            etat = this.getEtatSolaire(this.getDate(), meteo, pointage, this.getConsommation(), decallage);
            coupure = etat.getHeureCoupure().toSecondOfDay();
        }
        return etat;
    }

    public static void main(String... args) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Coupure[] coupures = (Coupure[]) new Coupure().findAll(connection, null);
            Salle salle = new Salle();
            salle.setSecteur(coupures[0]);
            Salle[] salles = (Salle[]) salle.findAll(connection, null);
            coupures[0].setSalles(salles);
            
            // Data sur la meteo et pointage a la date de coupure
            Meteo meteo = Meteo.createMeteo(connection);
            Pointage pointage = Pointage.createPointage(connection);
            
            EtatSolaire etat = coupures[0].getEtatSolaire(meteo, pointage, 1);
            System.out.println(etat.getHeureCoupure());
            System.out.println(etat.getConsommation());
            EtatSolaire[] details = etat.getDetails();
            for (EtatSolaire etatSolaire : details) {
                System.out.println(etatSolaire.getHeure() + " " + etatSolaire.getConsommationEtudiant() + " " + etatSolaire.getPuissanceSolaire() + " " + etatSolaire.getReste() + " " + etatSolaire.getCapacite() + " " + etatSolaire.isCoupure());
            }
        }
    }
    
}