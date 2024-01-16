package model.employees;

import connection.BddObject;

public class Categories extends BddObject {
    String nom;
    double prix;

    public Categories() throws Exception{
        this.setTable("Categories");
        this.setPrimaryKeyName("id_categories");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('cat_seq')");
        this.setCountPK(5);
        this.setPrefix("CAT");
    }
    
    public String getNom() {
        return nom;
    }
    public double getPrix() {
        return prix;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    
}
