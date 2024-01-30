package model.prix;

import java.sql.Connection;
import connection.BddObject;
import connection.annotation.ColumnName;
import model.insert.Format;
import model.insert.Product;

public class ListeProduitBenefice extends BddObject {

    public ListeProduitBenefice() throws Exception {
        super();
        this.setConnection("PostgreSQL");
        this.setPrimaryKeyName("");
    }

    Product product;
    Format format;
    @ColumnName("prix_de_vente")
    Double prixVente;
    Double salaire;
    Double prix;
    Double depense;
    Double benefice;

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getDepense() {
        return depense;
    }

    public void setDepense(Double depense) {
        this.depense = depense;
    }

    public Double getBenefice() {
        return benefice;
    }

    public void setBenefice(Double benefice) {
        this.benefice = benefice;
    }

    public ListeProduitBenefice[] filtre(String min, String max) throws Exception{
        String sql = "SELECT * FROM v_benefice WHERE benefice > %s AND benefice < %s;";

        ListeProduitBenefice[] listeProduitFiltre;

        try (Connection connection = BddObject.getPostgreSQL();) {
            
           listeProduitFiltre = (ListeProduitBenefice[]) new ListeProduitBenefice().getData(String.format(sql, min, max), connection);

        } 

        return listeProduitFiltre;
    }
    
}
