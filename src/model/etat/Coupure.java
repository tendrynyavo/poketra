package model.etat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import connection.BddObject;
import connection.annotation.ColumnName;
import exception.LimitException;
import model.meteo.Meteo;
import model.pointage.Pointage;
import model.secteur.Salle;
import model.secteur.Secteur;
import model.temps.Intervalle;

public class Coupure extends Secteur {

    @ColumnName("id_coupure")
    String idCoupure;
    Time heure;
    Date date;
    Meteo meteo;
    Pointage pointage;
    double consommation;

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) throws LimitException {
        if (consommation < 0 || consommation > 400) throw new LimitException("Consommation est négative");
        this.consommation = consommation;
    }

    public String getIdCoupure() {
        return idCoupure;
    }

    public void setIdCoupure(String idCoupure) {
        this.idCoupure = idCoupure;
    }

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

    public Time getHeure() {
        return heure;
    }

    public LocalTime getHeureToLocalTime() {
        return heure.toLocalTime();
    }

    public void setHeure(Time heure) throws IllegalArgumentException {
        LocalTime hours = heure.toLocalTime();
        if (hours.isBefore(Time.valueOf("08:00:00").toLocalTime()) || hours.isAfter(Time.valueOf("17:00:00").toLocalTime()))
            throw new IllegalArgumentException("Heure n'est pas comprise entre 08:00:00 et 17:00:00");
        this.heure = heure;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) throws IllegalArgumentException {
        if (date.isEmpty()) throw new IllegalArgumentException("Date est invalide");
        this.setDate(Date.valueOf(date));
    }

    public String getDays() {
        return this.getDate().toLocalDate().getDayOfWeek().toString();
    }

    public Coupure() throws Exception {
        super();
        this.getColumn("date").setName("date_coupure");
        this.setTable("coupure");
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

    public Coupure(String string, String string2, Time time, Date date2, double double1) throws Exception {
        this(string, string2, time, date2);
        this.setConsommation(double1);
    }

    // ! Optimisation du code
    public EtatSolaire getEtatSolaire(int decallage) {
        // Initialisation de la consommation
        double initiale = this.getConsommation();

        EtatSolaire etat = this.getEtatSolaire(this.getDate(), this.getMeteo(), this.getPointage(), this.getConsommation(), decallage);
        if (etat.getHeureCoupure().compareTo(this.getHeure().toLocalTime()) == 0) return etat;
        
        double increment = (1 / (double) 100);

        double p = (etat.getHeureCoupure().compareTo(this.getHeure().toLocalTime()) < 0) ? -increment : increment;
        int millis = this.getHeure().toLocalTime().toSecondOfDay() / 60;
        int coupure = etat.getHeureCoupure().toSecondOfDay() / 60;
        try {
            while (Math.abs(millis - coupure) >= 1) {
                this.setConsommation(this.getConsommation() + p);
                etat = super.getEtatSolaire(this.getDate(), this.getMeteo(), this.getPointage(), this.getConsommation(), decallage);
                coupure = etat.getHeureCoupure().toSecondOfDay() / 60;
            }
        } catch (LimitException e) {
            etat = this.getEtatSolaire(this.getDate(), this.getMeteo(), this.getPointage(), initiale, decallage);
        }
        return etat;
    }
    
    // ! Optimisation a la base de donnée
    public double getMoyenne() {
        double moyenne = 0.0;
        for (Salle s : this.getSalles()) {
            double somme = 0.0;
            double nombre = 0.0;
            for (Pointage detail : (Pointage[]) this.getPointage().getDetails()) {
                if (detail.getSalle().getId().equals(s.getId())) {
                    somme += detail.getNombre();
                    nombre++;
                }
            }
            if (nombre == 0.0) throw new NullPointerException(String.format("Pas de pointage à %s a cette salle %s a %s", this.getDate(), s.getNom(), this.getDate()));
            moyenne += somme / nombre;
        }
        return moyenne;
    }

    public static Coupure detail(String id, String decallage) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Coupure coupure = new Coupure();
            coupure.setIdCoupure(id);
            coupure = (Coupure) coupure.getById(connection);
            Salle salle = new Salle();
            salle.setSecteur(coupure);
            coupure.setSalles((Salle[]) salle.findAll(connection, null));
            coupure.setMeteo((Meteo) Intervalle.createIntervalle(coupure.getDate(), connection, new Meteo()));
            coupure.setPointage((Pointage) Intervalle.createIntervalle(coupure.getDate(), connection, new Pointage()));
            coupure.setEtat(coupure.getEtatSolaire(Integer.parseInt(decallage)));
            return coupure;
        }
    }
    
}