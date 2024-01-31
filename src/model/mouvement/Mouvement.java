package model.mouvement;

import java.sql.Date;

import connection.BddObject;
import connection.annotation.ColumnName;
import model.matiere.Matiere;

public class Mouvement extends BddObject {

    Matiere matiere;
    Date date;
    @ColumnName("entree")
    Double  quantite;
            Double prix;
    
    public Mouvement() throws Exception {
        super();
        this.setTable("mouvement");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('seq_mouvement')");
        this.setPrefix("M");
        this.setCountPK(5);
        this.setPrimaryKeyName("id_mouvement");
    }

    public Matiere getMatiere() {
        return this.matiere;
    }

    public Double getQuantite() {
        return this.quantite;
    }

    public Double getPrix() {
        return this.prix;
    }

    public Date getDate() {
        return this.date;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
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

    public void setPrix(String prix) throws NumberFormatException, Exception {
        if (prix.isEmpty()) {
            throw new Exception("La valeur insérée est nulle");
        }
        setPrix(Double.valueOf(prix));
    }

    public void setPrix(Double prix) throws Exception {
        if (prix < 0) {
            throw new Exception("La valeur insérée est négative");
        }
        this.prix = prix;
    }

}
