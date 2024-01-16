package model.employees;

import connection.BddObject;
import model.insert.Product;

public class Effectif extends BddObject{
    public Effectif() throws Exception {
        this.setTable("Effectif");
        this.setPrimaryKeyName("id_effectif");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('eff_seq')");
        this.setCountPK(5);
        this.setPrefix("EFF");
    }
   
    Categorie categorie;
    Product produit;
    int nom;

    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    public Product getProduit() {
        return produit;
    }
    public void setProduit(Product produit) {
        this.produit = produit;
    }
    public int getNom() {
        return nom;
    }
    public void setNom(int nom) {
        this.nom = nom;
    }

    
}
