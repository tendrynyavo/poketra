package model.temps;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import connection.BddObject;
import model.pointage.Pointage;

public class Intervalle extends BddObject {

    Date date;
    Time debut;
    Time fin;
    Intervalle[] details;

    public Intervalle[] getDetails() {
        return details;
    }

    public void setDetails(Intervalle[] details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getDebut() {
        return debut;
    }

    public LocalTime getDebutToLocalTime() {
        return debut.toLocalTime();
    }

    public void setDebut(Time debut) {
        this.debut = debut;
    }

    public Time getFin() {
        return fin;
    }

    public LocalTime getFinToLocalTime() {
        return fin.toLocalTime();
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }

    public Intervalle() throws Exception {
        super();
        this.setPrimaryKeyName("intervalle");
    }

    public boolean between(Date date, Time heure) {
        return ((heure.after(this.getDebut()) && heure.before(this.getFin())) || heure.compareTo(this.getDebut()) == 0 || heure.compareTo(this.getFin()) == 0) && this.getDate().compareTo(date) == 0;
    }

    // Optimisation recherche dichotomique
    public Intervalle getIntervalle(Date date, Time heure) {
        for (Intervalle intervalle : this.getDetails()) {
            if (intervalle.between(date, heure)) {
                if (this instanceof Pointage) {
                    Pointage pointage = (Pointage) intervalle;
                    if (pointage.getSalle().getId().equals(((Pointage) this).getSalle().getId())) {
                        return intervalle;
                    }
                } else {
                    return intervalle;
                }
            }
        }
        throw new IndexOutOfBoundsException(String.format("Pas de %s a %s à la date %s", this.getClass().getSimpleName(), heure, this.getDate()));
    }

    public static void binarySearch(int tab[], int f, int l, int val){
        int mid = (f + l)/2;
        while(f <= l){
            if (tab[mid] < val){
                f = mid + 1;   
            } else if(tab[mid] == val) {
                System.out.println("L'élément se trouve à l'index: " + mid);
                break;
            } else {
                l = mid - 1;
            }
            mid = (f + l)/2;
        }
        if (f > l){
            System.out.println("L'élément n'existe pas!");
        }
    }

    public Intervalle getIntervalle(String date, String time) {
        return this.getIntervalle(Date.valueOf(date), Time.valueOf(time));
    }

    public static Intervalle createIntervalle(Connection connection, Intervalle intervalle) throws Exception {
        Intervalle[] details = (connection == null) ? (Intervalle[]) intervalle.findAll(null) : (Intervalle[]) intervalle.findAll(connection, null);
        intervalle.setDetails(details);
        return intervalle;
    }

    public static Intervalle createIntervalle(Date date, Connection connection, Intervalle intervalle) throws Exception {
        intervalle.setDate(date);
        Intervalle[] details = (connection == null) ? (Intervalle[]) intervalle.findAll(null) : (Intervalle[]) intervalle.findAll(connection, null);
        intervalle.setDetails(details);
        return intervalle;
    }
    
}