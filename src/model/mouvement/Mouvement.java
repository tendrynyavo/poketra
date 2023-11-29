package model.mouvement;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import agregation.Liste;
import connection.BddObject;
import connection.annotation.ColumnName;
import model.article.Article;
import model.employe.Employe;
import model.magasin.Magasin;

public class Mouvement extends BddObject {

    @ColumnName("date_entree")
    Date date;
    @ColumnName("prix_unitaire")
    Double prixUnitaire;
    Double quantite;
    Article article;
    Magasin magasin;
    Integer status;
    @ColumnName("id_unite")
    Unite unite;
    @ColumnName("id_entree")
    String idEntree;

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public String getIdEntree() {
        return idEntree;
    }

    public void setIdEntree(String idEntree) {
        this.idEntree = idEntree;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) throws IllegalArgumentException {
        try {
            this.setDate(Date.valueOf(date));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Date invalide");
        }
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) throws IllegalArgumentException {
        if (prixUnitaire < 0) throw new IllegalArgumentException("Prix Unitaire invalide");
        this.prixUnitaire = prixUnitaire;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) throws IllegalArgumentException {
        if (quantite % 10 != 0 || quantite < 0) throw new IllegalArgumentException("Quantite invalide");
        this.quantite = quantite;
    }

    public void setQuantite(String quantite) throws IllegalArgumentException {
        if (quantite == null || quantite.isEmpty()) throw new IllegalArgumentException("Quantite est vide");
        this.setQuantite(Double.parseDouble(quantite));
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public String getStatusLettre() {
        String status;
        switch (this.getStatus()) {
            case 10:
                status = "Validé(e)";
                break;

            case 0:
                status = "En attente";
                break;
        
            default:
                status = "";
                break;
        }
        return status;
    }

    public double getQuantiteReel() {
        return this.getQuantite() / this.getUnite().getValue();
    }

    public double getPrixUnitaireReel() {
        return this.getPrixUnitaire() * this.getUnite().getValue();
    }

    public double getMontant() {
        return this.getPrixUnitaire() * this.getQuantite();
    }

    public Mouvement() throws Exception {
        super();
        this.setTable("mouvement");
        this.setPrimaryKeyName("id_sortie");
        this.setFunctionPK("nextval('seq_sortie')");
        this.setPrefix("S");
        this.setCountPK(5);
        this.setConnection("PostgreSQL");
    }

    public static Mouvement[] getEntrees() throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.setTable("entree");
        mouvement.setPrimaryKeyName("id_entree");
        return (Mouvement[]) mouvement.findAll("date_entree");
    }

    public static Mouvement[] getSorties() throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.setTable("v_mouvement");
        mouvement.setPrimaryKeyName("id_sortie");
        mouvement.getColumns().get(0).setName("date_sortie");
        mouvement.getColumns().get(2).setName("sortie");
        mouvement.getColumns().get(6).setName("id");
        return (Mouvement[]) mouvement.findAll("date_sortie");
    }

    public Mouvement(String quantite, String date) throws Exception {
        this();
        this.setQuantite(quantite);
        this.setDate(date);
    }

    public Mouvement(String id, double quantite) throws Exception {
        this();
        this.setId(id);
        this.setQuantite(quantite);
    }

    public static Mouvement[] getSortiesNonValide() throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.getColumns().get(0).setName("date_sortie");
        mouvement.setTable("sortie WHERE status = 0");
        return (Mouvement[]) mouvement.findAll(null);
    }

    public static Mouvement getMouvement(String id, Connection connection) throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.setId(id);
        mouvement.getColumns().get(0).setName("date_sortie");
        mouvement.setTable("sortie");
        return (connection == null) ? (Mouvement) mouvement.getById() : (Mouvement) mouvement.getById(connection);
    }

    public Mouvement[] decomposer(Connection connection) throws Exception {
        // Donnée de l'etat de stock actuelle par rapport article, magasin
        EtatMouvement[] etats = this.getMagasin().getEtatMouvements(this.getArticle(), this.getDate(), connection);
        double somme = this.getQuantite(); // Quantite initial de la sortie
        double reste = Liste.sommer(etats, "getReste"); // Reste en stock de l'article
        // Vérification de la suffisance de l'article
        if (somme > reste)
            throw new IllegalArgumentException(String.format("Stock de %s insuffisant avec reste : %s %s", this.getArticle().getNom(), reste, this.getArticle().getUnite()));
        List<Mouvement> mouvements = new ArrayList<>();
        for (EtatMouvement etat : etats) {
            somme -= etat.getReste();
            if (somme <= 0) {
                mouvements.add(new EtatMouvement(etat.getIdEntree(), this.getId(), somme + etat.getReste()));
                return mouvements.toArray(new Mouvement[0]);
            }
            mouvements.add(new EtatMouvement(etat.getIdEntree(), this.getId(), etat.getReste()));
        }
        return mouvements.toArray(new Mouvement[0]);
    }

    public static void valider(String id, String date) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Mouvement mouvement = Mouvement.getMouvement(id, connection);
            mouvement.valider(Date.valueOf(date), connection);
            connection.commit();
        }
    }

    public void valider(Date date, Connection connection) throws Exception {
        boolean connect = false;
        try {
            if (connection == null) { connection = BddObject.getPostgreSQL(); connect = true; }
            if (this.getDate().after(date)) throw new IllegalArgumentException("Date invalide");
            // Contrôle du mouvement
            this.getArticle().check(this.getMagasin(), date, this.getQuantite(), connection);
            // Décomposition du mouvement
            for (Mouvement mouvement : this.decomposer(connection)) {
                mouvement.insert(connection);
            }
            // Inserer la validation
            new Validation(new Employe("EMP001"), date, this).insert(connection);
            // Changement de la status
            this.setStatus(10);
            this.setTable("sortie");
            this.getColumns().get(0).setName("date_sortie");
            this.update(connection);
            if (connect) { connection.commit(); }
        } catch (Exception e) {
            if (connect) connection.rollback();
            throw e;
        } finally {
            if (connect) connection.close();
        }
    }

    public static void update(String sortie, String date, String code, String quantite, String codeMagasin) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Mouvement mouvement = new Mouvement();
            mouvement.setId(sortie);
            mouvement.updateSortie(date, code, quantite, codeMagasin, connection);
            connection.commit();
        }
    }

    public void updateSortie(String date, String code, String quantite, String codeMagasin, Connection connection) throws Exception {
        this.setTable("sortie");
        this.getColumns().get(0).setName("date_sortie");
        this.setDate(date);
        this.setQuantite(quantite);
        
        Article article = Article.checkCodeExists(code, connection);
        if (article == null) throw new IllegalArgumentException("Article n'existe pas");
        this.setArticle(article);
        
        Magasin magasin = Magasin.exists(codeMagasin, connection); // Prendre le magasin
        if (magasin == null) throw new IllegalArgumentException("Magasin n'existe pas");
        
        article.check(magasin, this.getDate(), this.getQuantite(), connection);
        
        this.setMagasin(magasin);
        this.update(connection);
    }
    
}