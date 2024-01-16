package model.prix;

import java.sql.Connection;

import connection.BddObject;
import connexion.ConnexionToDatabase;
import model.insert.Format;
import model.insert.Product;


public class ListeProduitFiltre extends BddObject{
    
    Product produit;
    Format format;
    double prix;

    public ListeProduitFiltre()  throws Exception{
        this.setTable("v_produit_prix");
        this.setConnection("PostgreSQL");
        this.setPrimaryKeyName("");
    }

    public void setProduit(Product produit) {
        this.produit = produit;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Product getProduit() {
        return this.produit;
    }

    public Format getFormat() {
        return this.format;
    }

    public double getPrix() {
        return this.prix;
    }

    public ListeProduitFiltre[] filtre(String min, String max) throws Exception{
        String sql = "SELECT * FROM v_produit_prix WHERE prix > %s AND prix < %s;";

        ListeProduitFiltre[] listeProduitFiltre;

        try (Connection connection = ConnexionToDatabase.getConnection();) {
            
           listeProduitFiltre = (ListeProduitFiltre[]) new ListeProduitFiltre().getData(String.format(sql, min, max), connection);

        } 

        return listeProduitFiltre;
    }

    public static void main(String[] args) throws Exception {
        ListeProduitFiltre foo = new ListeProduitFiltre();
        
        for (ListeProduitFiltre listeProduitFiltre : foo.filtre("2000", "7000") ) {
            System.out.println(listeProduitFiltre.getProduit().getNom());
            System.out.println(listeProduitFiltre.getFormat().getNom());
            System.out.println(listeProduitFiltre.getPrix());
        }
    }
}
