package model.etat;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import model.meteo.Meteo;
import model.pointage.Pointage;
import model.secteur.Salle;
import model.secteur.Secteur;

public class Coupure extends Secteur {

    Time heure;
    Date date;
    double consommation;
    Meteo meteo;
    Pointage pointage;

    public Meteo getMeteo() {
        return meteo;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }

    public Pointage getPointage() {
        return pointage;
    }

    public void setPointage(Pointage pointage) {
        this.pointage = pointage;
    }

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

    public Coupure(String id, String nom, Time heure, Date date) throws Exception {
        this();
        this.setId(id);
        this.setNom(nom);
        this.setHeure(heure);
        this.setDate(date);
    }

    public EtatSolaire getEtatSolaire(int decallage, int pas) {
        // Initialisation de la consommation
        this.setConsommation(100);

        EtatSolaire etat = this.getEtatSolaire(this.getDate(), meteo, pointage, this.getConsommation(), decallage);
        if (etat.getHeureCoupure().compareTo(this.getHeure().toLocalTime()) == 0) return etat;
        
        double increment = (1 / (double) pas);

        double p = (etat.getHeureCoupure().compareTo(this.getHeure().toLocalTime()) < 0) ? -increment : increment;
        int millis = this.getHeure().toLocalTime().toSecondOfDay() / 60;
        int coupure = etat.getHeureCoupure().toSecondOfDay() / 60;
        while (Math.abs(millis - coupure) >= 45) {
            this.setConsommation(this.getConsommation() + p);
            etat = super.getEtatSolaire(this.getDate(), this.getMeteo(), this.getPointage(), this.getConsommation(), decallage);
            coupure = etat.getHeureCoupure().toSecondOfDay() / 60;
        }
        return etat;
    }

    public double getTotalNombre() {
        int somme = 0;
        for (Salle s : this.getSalles()) {
            for (Pointage detail : (Pointage[]) this.getPointage().getDetails()) {
                if (detail.getSalle().getId().equals(s.getId())) {
                    somme += detail.getNombre();
                }
            }
        }
        return somme;
    }

    public double getNombreMoyenne() {
        int moyenne = 0;
        for (Salle s : this.getSalles()) {
            int somme = 0;
            int nombre = 0;
            for (Pointage detail : (Pointage[]) this.getPointage().getDetails()) {
                if (detail.getSalle().getId().equals(s.getId())) {
                    somme += detail.getNombre();
                    nombre++;
                }
            }
            if (nombre == 0) throw new NullPointerException(String.format("Pas de pointage à %s", this.getDate()));
            moyenne += somme / nombre;
        }
        return moyenne;
    }
    
}