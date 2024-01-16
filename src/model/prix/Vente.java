package model.prix;

import connection.BddObject;
import model.insert.Format;
import model.insert.Product;

public class Vente extends BddObject{

    Product produit;
    Format format;
    Double prix;

    public Vente() throws Exception {
        this.setTable("produit_format_prix");
        this.setSerial(false);
        this.setConnection("PostgreSQL");
    }

    public Product getProduit() {
        return produit;
    }

    public void setProduit(Product produit) {
        this.produit = produit;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    
    

}
