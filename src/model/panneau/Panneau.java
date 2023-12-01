package model.panneau;

import connection.BddObject;

public class Panneau extends BddObject {

    String nom;
    double puissance;
    double capacite;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom.isEmpty()) throw new IllegalArgumentException("Nom est vide");
        this.nom = nom;
    }

    public double getPuissance() {
        return puissance;
    }

    public void setPuissance(double puissance) throws IllegalArgumentException {
        if (puissance < 0) throw new IllegalArgumentException("Puissance invalide");
        this.puissance = puissance;
    }

    public double getCapacite() {
        return capacite;
    }

    public void setCapacite(double capacite) throws IllegalArgumentException {
        if (capacite < 0) capacite = 0;
        this.capacite = capacite;
    }

    public Panneau() throws Exception {
        super();
        this.setTable("panneau");
        this.setPrimaryKeyName("id_panneau");
        this.setConnection("PostgreSQL");
    }

}