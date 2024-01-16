package model.employees;

import connection.BddObject;

public class Categorie extends BddObject {
    String nom;
    double salaire;

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
    public void setPrix(double prix) {
        this.salaire = prix;
    }

    
}
