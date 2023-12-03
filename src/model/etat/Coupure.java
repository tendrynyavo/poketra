package model.etat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import connection.BddObject;
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

    public EtatSolaire getEtatSolaire(int decallage, Connection connection) throws Exception {
        this.setConsommation(60);
        EtatSolaire etat = this.getEtatSolaire(this.getDate(), decallage, this.getConsommation(), connection);
        double p = (etat.getHeureCoupure().compareTo(this.getHeure().toLocalTime()) < 0) ? -0.01 : 0.01;
        int millis = this.getHeure().toLocalTime().toSecondOfDay();
        int coupure = etat.getHeureCoupure().toSecondOfDay();
        while (Math.abs(millis - coupure) >= 6000) {
            this.setConsommation(this.getConsommation() + p);
            etat = this.getEtatSolaire(this.getDate(), decallage, this.getConsommation(), connection);
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
            EtatSolaire etat = coupures[0].getEtatSolaire(1, connection);
            System.out.println(etat.getHeureCoupure());
            System.out.println(etat.getConsommation());
            EtatSolaire[] details = etat.getDetails();
            for (EtatSolaire etatSolaire : details) {
                System.out.println(etatSolaire.getHeure() + " " + etatSolaire.getConsommationEtudiant() + " " + etatSolaire.getPuissanceSolaire() + " " + etatSolaire.getReste() + " " + etatSolaire.getCapacite() + " " + etatSolaire.isCoupure());
            }
        }
    }
    
}