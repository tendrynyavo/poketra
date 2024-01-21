package model.employees;

import connection.BddObject;

public class Categorie extends BddObject {
    
    String nom;
    Double salaire;

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        if (salaire < 0) {
            throw new IllegalArgumentException("Salaire est négatif");
        }
        this.salaire = salaire;
    }

    public void setSalaire(String salaire) {
        if (salaire.isEmpty())
            throw new IllegalArgumentException("Salaire est vide");
        this.setSalaire(Double.parseDouble(salaire));
    }

    public Categorie() throws Exception{
        this.setTable("Categories");
        this.setPrimaryKeyName("id_categorie");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('cat_seq')");
        this.setCountPK(5);
        this.setPrefix("CAT");
    }
    
    public String getNom() {
        return nom;
    }
    public double getPrix() {
        return salaire;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return this.getNom();
    }
    
}
