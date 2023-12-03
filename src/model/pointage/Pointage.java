package model.pointage;

import java.sql.Connection;
import model.secteur.Salle;
import model.temps.Intervalle;

public class Pointage extends Intervalle {

    Salle salle;
    int nombre;

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public Pointage() throws Exception {
        super();
        this.getColumns().get(2).setName("date_pointage");
        this.setTable("pointage");
        this.setPrimaryKeyName("id_pointage");
        this.setConnection("PostgreSQL");
    }

    public static Pointage createPointage(Connection connection) throws Exception {
        return (Pointage) createIntervalle(connection, new Pointage());
    }
    
}