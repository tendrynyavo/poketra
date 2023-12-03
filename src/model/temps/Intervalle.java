package model.temps;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import connection.BddObject;

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

    public boolean between(Time heure) {
        return (heure.after(this.getDebut()) && heure.before(this.getFin())) || heure.compareTo(this.getDebut()) == 0 || heure.compareTo(this.getFin()) == 0;
    }

    public Intervalle setDetails(String date, Connection connection) throws Exception {
        this.setDate(Date.valueOf(date));
        Intervalle[] details = (connection == null) ? (Intervalle[]) this.findAll(null) : (Intervalle[]) this.findAll(connection, null);
        this.setDetails(details);
        return this;
    }

    public Intervalle getIntervalle(Time heure) throws IndexOutOfBoundsException {
        for (Intervalle intervalle : this.getDetails()) {
            if (intervalle.between(heure)) {
                return intervalle;
            }
        }
        throw new IndexOutOfBoundsException(String.format("Pas de %s a %s à la date %s", this.getClass().getSimpleName(), heure, this.getDate()));
    }

    public Intervalle getIntervalle(String time) {
        return this.getIntervalle(Time.valueOf(time));
    }
    
}