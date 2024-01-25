package model.mouvement;

import java.sql.Connection;

import connection.BddObject;
import model.prix.ListeProduitBenefice;
import personne.Genre;

public class Statistique extends BddObject{
    Genre genre;
    int nombre;

    public Statistique() throws Exception{
        this.setTable("achats");
        this.setPrimaryKeyName("id_achat");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('achat_seq')");
        this.setCountPK(5);
        this.setPrefix("ACH");
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public Statistique[] filtre(String produit, String genre) throws Exception{
        String sql = "SELECT vsi.id_genre, MAX(vsi.nombre) AS nombre FROM v_stat_initiale vsi WHERE vsi.id_produit LIKE '%s' AND vsi.id_format LIKE '%s' GROUP BY vsi.id_genre;";

        Statistique[] statistiques;

        try (Connection connection = BddObject.getPostgreSQL();) {
            
           statistiques = (Statistique[]) new Statistique().getData(String.format(sql, produit, genre), connection);

        } 

        return statistiques;
    }
}
