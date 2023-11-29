package model.mouvement;

import java.sql.Date;
import connection.annotation.ColumnName;

public class EtatMouvement extends Mouvement {

    Double sortie;
    @ColumnName("date_sortie")
    Date dateLastSortie;
    @ColumnName("date_validation")
    Date dateValidation;
    @ColumnName("id_sortie")
    String idSortie;

    public String getIdSortie() {
        return idSortie;
    }

    public void setIdSortie(String idSortie) {
        this.idSortie = idSortie;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Double getSortie() {
        return sortie;
    }

    public void setSortie(Double sortie) {
        this.sortie = sortie;
    }

    public Date getDateLastSortie() {
        return dateLastSortie;
    }

    public void setDateLastSortie(Date dateLastSortie) {
        this.dateLastSortie = dateLastSortie;
    }

    public double getReste() {
        return this.getQuantite() - this.getSortie();
    }

    public double getQuantiteLastUnite() {
        return this.getReste() * this.getUnite().getValue();
    }

    @Override
    public double getMontant() {
        return this.getReste() * this.getPrixUnitaire();
    }

    public EtatMouvement() throws Exception {
        super();
        this.setTable("v_etat_stock");
        this.setPrimaryKeyName("id_entree");
    }

    public EtatMouvement(String id, String sortie, double quantite) throws Exception {
        this();
        this.setTable("mouvement");
        this.setSerial(false);
        this.setId(id);
        this.setIdSortie(sortie);
        this.setQuantite(quantite);
    }
    
}