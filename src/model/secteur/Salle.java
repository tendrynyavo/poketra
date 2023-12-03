package model.secteur;

import connection.BddObject;

public class Salle extends BddObject {

    String nom;
    Secteur secteur;
    int nombre;

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws IllegalArgumentException {
        if (nom.isEmpty()) throw new IllegalArgumentException("Nom est vide");
        this.nom = nom;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Salle() throws Exception {
        super();
        this.setTable("salle");
        this.setPrimaryKeyName("id_salle");
        this.setConnection("PostgreSQL");
    }

    public Salle(String id) throws Exception {
        this();
        this.setId(id);
    }
    
}
