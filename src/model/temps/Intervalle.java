package model.temps;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import connection.BddObject;

public class Intervalle extends BddObject {

    Date date;
    Time debut;
    Time fin;

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
    }
    
}
