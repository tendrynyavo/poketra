package model.employees;

import java.sql.Connection;

import connection.BddObject;
import connection.Column;
import connexion.ConnexionToDatabase;
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

    public void insertEffectif() throws Exception {
        Connection connection = null;
        try {
            connection = ConnexionToDatabase.getConnection();
            Effectif[] effectifs = new Effectif[2];
    
            effectifs[0] = new Effectif();
    
            effectifs[0].setCategorie(this.getCategorie());
            effectifs[0].setProduit(this.getProduit());
            effectifs[0].setNombre(nombre);
            Format foo0 = new Format();
            foo0.setId("1");
            effectifs[0].setFormat(foo0);
    
            effectifs[1].setCategorie(this.getCategorie());
            effectifs[1].setProduit(this.getProduit());
            effectifs[1].setNombre(nombre * 2);
            Format foo1 = new Format();
            foo1.setId("2");
            effectifs[1].setFormat(foo1);
    
            for (Effectif effectif : effectifs) {
                effectif.insert(connection);
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }
}
