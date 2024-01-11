package model.insert;

import connection.BddObject;

public class Product extends BddObject {

    String nom;
    
    public Product() throws Exception{
        this.setTable("Produit");
        this.setPrimaryKeyName("id_produit");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('seq_produit')");
        this.setCountPK(5);
        this.setPrefix("PRO");
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return this.getNom();
    }

}
