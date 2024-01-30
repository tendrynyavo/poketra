package model.affichage;

import connection.BddObject;
import model.insert.Format;
import model.insert.Product;

public class FiltreGenre extends BddObject {

    Product produit;
    Format format;
    
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

    public FiltreGenre() throws Exception {
        super();
        this.setConnection("PostgreSQL");
    }
    
}