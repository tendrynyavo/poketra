package model.insert;

import java.sql.Connection;
import connection.BddObject;
import connection.annotation.ColumnName;
import model.matiere.Matiere;
import model.mouvement.EtatStock;
import model.mouvement.Mouvement;

public class Product extends BddObject {

    String nom;
    Format format;
    int duree;
    @ColumnName("prix_de_vente")
    Double prixDeVente;
    
    public Product() throws Exception {
        super();
        this.setTable("Produit");
        this.setPrimaryKeyName("id_produit");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('seq_produit')");
        this.setCountPK(5);
        this.setPrefix("PRO");
    }

    public void setPrixDeVente(Double prixDeVente) {
        this.prixDeVente = prixDeVente;
    }

    public void setPrixDeVente(String prixDeVente) {
        this.setPrixDeVente(Double.parseDouble(prixDeVente));
    }

    public Double getPrixDeVente() {
        return prixDeVente;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setDuree(String duree) {
        this.setDuree(Integer.parseInt(duree));
    }

    public Format getFormat() {
        return this.format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setFormat(String format) throws Exception {
        Format f = new Format();
        f.setId(format);
        this.format = f;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Matiere[] getMatiere(Connection c) throws Exception {
        Matiere matiere = new Matiere();
        matiere.setTable("v_fabrication");
        matiere.setProduit(this);
        matiere.setFormat(this.getFormat());
        matiere.getColumns().get(2).setName("matiere");
        return (Matiere[]) matiere.findAll(null);
    }

    public Mouvement[] fabriquer(String date, String quantite, Connection connection) throws Exception {
        Matiere[] matieres = this.getMatiere(connection);
        Mouvement[] mouvements = new Mouvement[matieres.length];
        double quantiteParse = Double.parseDouble(quantite);
        EtatStock stock = new EtatStock().getEtatStock(connection);
        for (int i = 0; i < mouvements.length; i++) {
            mouvements[i] = new Mouvement();
            mouvements[i].getColumns().get(2).setName("entree");
            mouvements[i].setMatiere(matieres[i]);
            mouvements[i].setDate(date);
            mouvements[i].setQuantite(quantiteParse * matieres[i].getQuantite());
            if (!stock.suffisant(mouvements[i])) {
                throw new Exception(String.format("Matière %s insuffisante", matieres[i].getNom()));
            }
        }
        return mouvements;
    }

    public Mouvement[] fabriquerProduit(String date, String quantite, Connection connection) throws Exception {
        Mouvement[] mouvements = null;
        boolean connect = false;
        try {
            if (connection == null) {
                connection = this.getConnection();
                connect = true;
            }
            mouvements = this.fabriquer(date, quantite, connection);
            
            for (Mouvement mouvement : mouvements) {
                mouvement.insert(connection);
            }
            
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connect && connection != null) {
                connection.close();
            }
        }
        return mouvements;
    }

    @Override
    public String toString() {
        return this.getNom();
    }

}