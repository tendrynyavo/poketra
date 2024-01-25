package model.insert;

import connection.BddObject;
import model.matiere.Matiere;

public class Quantite extends BddObject {

    Product product;
    Format format;
    Matiere matiere;
    double quantite;
    
    public Quantite() throws Exception{
        super();
        this.setTable("Quantite");
        this.setPrimaryKeyName("id_quantite");
        this.setConnection("PostgreSQL");
        this.setSerial(false);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setFormat(String format) throws Exception {
        Format f = new Format();
        f.setId(format);
        this.format = f;
    }

    public void setProduct(String format) throws Exception {
        Product f = new Product();
        f.setId(format);
        this.product = f;
    }

    public void setMatiere(String format) throws Exception {
        Matiere f = new Matiere();
        f.setId(format);
        this.matiere = f;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) throws Exception {
        if (quantite < 0) {
            throw new Exception("Quantité est négative.");
        } else {
            this.quantite = quantite;
        }
    }

    public void setQuantite(String quantite) throws Exception {
        this.setQuantite(Double.parseDouble(quantite));
    }

    public static void main(String[] args) throws Exception {
        Product product = new Product();
        product.setId("PRO1");
        Format format = new Format();
        format.setId("2");
        Matiere matiere = new Matiere();
        matiere.setId("MAT001");
        Quantite quantite = new Quantite();
        quantite.setProduct(product);
        quantite.setFormat(format);
        quantite.setMatiere(matiere);
        quantite.setQuantite("2");
        quantite.insert(null);
    }
    
}