package model.matiere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import connection.BddObject;
import connection.annotation.ColumnName;
import model.insert.Quantite;

public class Matiere extends BddObject{
    
    String nom;
    @ColumnName("prix_unitaire")
    Double prix;

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        if (prix < 0) 
            throw new IllegalArgumentException("Prix est négatif");
        this.prix = prix;
    }

    public void setPrix(String prix) {
        this.setPrix(Double.parseDouble(prix));
    }

    public Matiere() throws Exception {
        this.setTable("matiere");
        this.setPrimaryKeyName("id_matiere");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('seq_matiere')");
        this.setPrefix("MAT");
        this.setCountPK(7);
    }

    public Matiere(String id) throws Exception {
        this.setId(id);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Quantite[] getProductQuantite() throws Exception {
        Quantite quantite = new Quantite();
        quantite.setMatiere(this);
        return (Quantite[]) quantite.findAll(null);
    }

    public void insert() throws Exception {
        String sql = "INSERT INTO matiere(nom) VALUES (?);";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, this.getNom());
            
            preparedStatement.executeUpdate();

            connection.commit();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public String toString() {
        return this.getNom();
    }

    public static void main(String[] args) throws Exception {
        Matiere matiere = new Matiere();

        for (Quantite string : matiere.getProductQuantite()) {
            System.out.println(string.getQuantite());
        }
    }
}
