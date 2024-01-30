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
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Categorie() throws Exception {
        super();
        this.setTable("Categories");
        this.setPrimaryKeyName("id_categorie");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('cat_seq')");
        this.setCountPK(5);
        this.setPrefix("CAT");
    }

    @Override
    public String toString() {
        return this.getNom();
    }
    
}
