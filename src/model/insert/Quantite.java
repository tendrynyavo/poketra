package model.insert;

import connection.BddObject;
import model.matiere.Matiere;

public class Quantite extends BddObject {
    Product product;
    Format format;
    Matiere matiere;
    double quantite;
    
    public Quantite() throws Exception{
        this.setTable("Quantite");
        this.setPrimaryKeyName("id_quantite");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('seq_quantite')");
        this.setCountPK(5);
        this.setPrefix("Qua");
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
}
