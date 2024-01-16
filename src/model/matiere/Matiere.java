package model.matiere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import connection.BddObject;
import model.insert.Format;
import model.insert.Product;
import model.insert.Quantite;

public class Matiere extends BddObject {

    Product produit;
    Format format;
    String  nom;
    double quantite;

    public Matiere() throws Exception {
        super();
        this.setTable("matiere");
        this.setPrimaryKeyName("id_matiere");
        this.setConnection("PostgreSQL");
    }

    public Format getFormat() {
        return this.format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Product getProduit() {
        return this.produit;
    }

    public void setProduit(Product produit) {
        this.produit = produit;
    }

    public Double getQuantite() {
        return this.quantite;
    }

    public void setQuantite(String quantite) throws NumberFormatException, Exception {
        if (quantite.isEmpty()) {
            throw new Exception("La valeur insérée est nulle");
        }
        setQuantite(Double.valueOf(quantite));
    }

    public void setQuantite(double quantite) throws Exception {
        if (quantite < 0) {
            throw new Exception("La valeur insérée est négative");
        } 
        this.quantite = quantite;
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

    public static void main(String[] args) throws Exception {
        Matiere matiere = new Matiere();

        for (Quantite string : matiere.getProductQuantite()) {
            System.out.println(string.getQuantite());
        }
    }
}
