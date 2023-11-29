package model.magasin;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import agregation.Liste;
import connection.BddObject;
import model.article.Article;
import model.mouvement.EtatMouvement;
import model.mouvement.Mouvement;

public class Magasin extends BddObject {

    String nom;
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Magasin() throws Exception {
        super();
        this.setTable("magasin");
        this.setPrimaryKeyName("id_magasin");
        this.setConnection("PostgreSQL");
    }

    public Magasin(String id) throws Exception {
        this();
        this.setId(id);
    }

    public static Magasin exists(String idMagasin, Connection connection) throws Exception {
        Magasin magasin = new Magasin(idMagasin);
        return (connection == null) ? (Magasin) magasin.getById() : (Magasin) magasin.getById(connection);
    }

    public Mouvement getLastMouvement(Article article, Connection connection) throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.getColumns().get(0).setName("date");
        mouvement.setTable("v_last_mouvement");
        mouvement.setArticle(article);
        mouvement.setMagasin(this);
        Mouvement[] mouvements = (connection == null) ? (Mouvement[]) mouvement.findAll(null) : (Mouvement[]) mouvement.findAll(connection, null);
        return (mouvements.length == 0) ? null : mouvements[0];
    }

    public EtatMouvement[] getEtatMouvements(Article article, Date date, Connection connection) throws Exception {
        String type = (article.getType() == 1) ? "ASC" : "DESC";
        EtatMouvement[] entrees = (EtatMouvement[]) ((BddObject) new EtatMouvement().setTable(String.format("entree WHERE date_entree <= '%s' AND id_article = '%s' AND id_magasin = '%s'", date, article.getId(), this.getId()))).findAll(connection, "(date_entree, id_entree) " + type);
        Mouvement[] sorties = (Mouvement[]) new Mouvement().getData(String.format("SELECT id_entree, SUM(sortie) as quantite FROM v_mouvement WHERE date_validation <= '%s' AND id_article = '%s' AND id_magasin = '%s' GROUP BY id_entree", date, article.getId(), this.getId()), connection);
        List<EtatMouvement> etats = new ArrayList<>();
        for (int i = 0; i < entrees.length; i++) {
            entrees[i].setSortie(0.0);
            for (Mouvement sortie : sorties) {
                if (sortie.getIdEntree().equals(entrees[i].getIdEntree())) {
                    entrees[i].setSortie(sortie.getQuantite());
                }
            }
            if (entrees[i].getReste() > 0) {
                etats.add(entrees[i]);
            }
        }
        return etats.toArray(new EtatMouvement[0]);
    }

    public double getReste(Article article, Date date, Connection connection) throws Exception {
        EtatMouvement[] etats = this.getEtatMouvements(article, date, connection);
        return Liste.sommer(etats, "getReste");
    }

}