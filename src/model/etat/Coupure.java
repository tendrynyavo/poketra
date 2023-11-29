package model.etat;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

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
        this.setTable("coupure");
        this.setPrimaryKeyName("id_coupure");
        this.setConnection("PostgreSQL");
    }
    
}