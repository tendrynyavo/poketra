package model.employees;

import java.sql.Connection;

import connection.BddObject;
import connection.Column;
import model.insert.Format;
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
    Format format;
    int nombre;
    
    public Format getFormat() {
        return format;
    }
    public void setFormat(Format format) {
        this.format = format;
    }

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
    public int getNombre() {
        return nombre;
    }
    public void setNombre(int nom) {
        this.nombre = nom;
    }

    public void insert(Connection connection, Column... columns) throws Exception{
        if (format.getId().equals("2")) {
            setNombre(nombre * 2);
        }

        super.insert(connection, columns);
    }
}
