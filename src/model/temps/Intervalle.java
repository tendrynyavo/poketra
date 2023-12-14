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
        return this.between(heure) && this.getDate().compareTo(date) == 0;
    }

    public boolean between(Time heure) {
        return ((heure.after(this.getDebut()) && heure.before(this.getFin())) || heure.compareTo(this.getDebut()) == 0 || heure.compareTo(this.getFin()) == 0);
    }

    public int compareTo(Time hours) {
        return hours.compareTo(this.getDebut());
    }

    // TODO Optimisation de la somme
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

    public Intervalle getIntervalle(Time heure) {
        Intervalle[] details = this.getDetails();
        int left = 0; 
        int right = details.length - 1;
        while (left <= right) {
            int midlle = left + (right - left) / 2;
            
            if (details[midlle].between(heure))
                return details[midlle];
            
            if (details[midlle].compareTo(heure) > 0) {
                left = midlle + 1;
            } else {
                right = midlle - 1;
            }
        }
        throw new IndexOutOfBoundsException(String.format("Pas de %s a %s a la date %s", this.getClass().getSimpleName(), heure, this.getDate()));
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
        Intervalle[] details = (connection == null) ? (Intervalle[]) intervalle.findAll("debut") : (Intervalle[]) intervalle.findAll(connection, "debut");
        intervalle.setDetails(details);
        return intervalle;
    }
    
}