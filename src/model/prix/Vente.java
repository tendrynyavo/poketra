package model.prix;

import connection.BddObject;
import connection.annotation.ColumnName;
import model.insert.Format;
import model.insert.Product;

public class Vente extends BddObject {

    Product produit;
    Format format;
    @ColumnName("prix_de_vente")
    Double prix;

    public Vente() throws Exception {
        super();
        this.setTable("produit_format_prix");
        this.setConnection("PostgreSQL");
        this.setSerial(false);
        this.setPrimaryKeyName("");
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
        if (prix < 0)
            throw new IllegalArgumentException("Prix est négatif");
        this.prix = prix;
    }

    public void setPrix(String prix) {
        if (prix.isEmpty())
            throw new IllegalArgumentException("Prix est vide");
        this.setPrix(Double.parseDouble(prix));
    }

    public void setFormat(String format) throws Exception {
        Format f = new Format();
        f.setId(format);
        this.setFormat(f);
    }

    public void setProduit(String format) throws Exception {
        Product f = new Product();
        f.setId(format);
        this.setProduit(f);
    }

}