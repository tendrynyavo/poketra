package model.etat;

import java.io.Serializable;
import java.time.LocalTime;
import model.secteur.Secteur;

public class EtatSolaire implements Serializable {
    
    LocalTime heure;
    int luminosite;
    int nombre;
    Secteur secteur;

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public int getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(int luminosite) {
        this.luminosite = luminosite;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }
    

}
