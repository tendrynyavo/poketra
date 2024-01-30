package model.fabrication;

import java.sql.Date;
import connection.BddObject;
import model.insert.Format;
import model.insert.Product;
import model.mouvement.Mouvement;

public class Fabrication extends BddObject {
    
    Product produit;
    Date date;
    double  quantite;
    Format format;
    
    public Date getDate() {
        return date;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public Fabrication() throws Exception {
        super();
        this.setConnection("PostgreSQL");
    }

    public Product getProduit() {
        return this.produit;
    }

    public void setProduit(Product produit) {
        this.produit = produit;
    }

    public Format getFormat() {
        return this.format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setDate(String date) throws Exception {
        if (date.isEmpty()) {
            throw new Exception("La valeur insérée est nulle");
        }
        setDate(Date.valueOf(date));
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setQuantite(String quantite) throws NumberFormatException, Exception {
        if (quantite.isEmpty()) {
            throw new Exception("La valeur insérée est nulle");
        }
        setQuantite(Double.valueOf(quantite));
    }

    public void setQuantite(Double quantite) throws Exception {
        if (quantite < 0) {
            throw new Exception("La valeur insérée est n&égative");
        } 
        this.quantite = quantite;
    }

    public Mouvement[] fabriquer(String idProduit, String idFormat, String date, String quantite) throws Exception {
        Product product = new Product();
        product.setId(idProduit);
        Format format = new Format();
        format.setId(idFormat);
        product.setFormat(format);
        return product.fabriquerProduit(date, quantite, null);
    }

}