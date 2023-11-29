package model.pointage;

import model.secteur.Salle;
import model.temps.Intervalle;

public class Pointage extends Intervalle {

    Salle salle;
    int nombre;
    Pointage[] pointages;

    public Pointage[] getPointages() {
        return pointages;
    }

    public void setPointages(Pointage[] pointages) {
        this.pointages = pointages;
    }

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
        this.setTable("pointage");
        this.setPrimaryKeyName("id_pointage");
        this.setConnection("PostgreSQL");
    }
    
}
