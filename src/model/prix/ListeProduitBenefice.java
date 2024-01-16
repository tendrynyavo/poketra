package model.prix;

import model.insert.Product;

public class ListeProduitBenefice extends Product {

    Double depence;

    public ListeProduitBenefice() throws Exception {
        super();
    }

    public Double getDepence() {
        return depence;
    }

    public void setDepence(Double depence) {
        this.depence = depence;
    }

    public Double getBenefice() {
        return getDepence() - getPrixDeVente();
    }
    
}
