package model.secteur;

import connection.BddObject;
import model.etat.EtatSolaire;
import model.panneau.Panneau;

public class Secteur extends BddObject {

    String nom;
    Panneau panneau;
    Salle[] salles;
    EtatSolaire[] etats;

    public EtatSolaire[] getEtats() {
        return etats;
    }

    public void setEtats(EtatSolaire[] etats) {
        this.etats = etats;
    }

    public Salle[] getSalles() {
        return salles;
    }

    public void setSalles(Salle[] salles) throws IllegalArgumentException {
        if (salles.length <= 0) throw new IllegalArgumentException("Salles invalides"); 
        this.salles = salles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws IllegalArgumentException {
        if (nom.isEmpty()) throw new IllegalArgumentException("Nom est vide");
        this.nom = nom;
    }

    public Panneau getPanneau() {
        return panneau;
    }

    public void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    public Secteur() throws Exception {
        super();
        this.setTable("secteur");
        this.setConnection("PostgreSQL");
        this.setPrimaryKeyName("id_secteur");
    }
    
}
