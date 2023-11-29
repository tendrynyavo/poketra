package model.article;

import java.sql.Connection;
import java.sql.Date;
import connection.BddObject;
import model.magasin.Magasin;
import model.mouvement.Mouvement;
import model.mouvement.Unite;

public class Article extends BddObject {

    String code;
    String nom;
    int type;
    String unite;
    
    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public int getType() {
        return type;
    }

    public String getUnite() {
        return unite;
    }

    public Article setCode(String code) throws IllegalArgumentException {
        if (code.isEmpty()) throw new IllegalArgumentException("Code est vide");
        this.code = code;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Article() throws Exception {
        super();
        this.setTable("article");
        this.setPrimaryKeyName("id_article");
        this.setConnection("PostgreSQL");
    }

    public Article(String id) throws Exception {
        this();
        this.setId(id);
    }

    // Fonction métier
    public static void sortir(String code, String quantite, String idMagasin, String date, String unite) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Article article = Article.checkCodeExists(code, connection);
            if (article == null) throw new IllegalArgumentException("Article n'éxiste pas");
            Mouvement mouvement = article.sortir(quantite, idMagasin, date, unite, connection);
            mouvement.insert(connection);
            connection.commit();
        }
    }

    public static Article checkCodeExists(String code, Connection connection) throws Exception {
        Article article = new Article();
        article.setCode(code);
        Article[] articles = (connection == null) ? (Article[]) article.findAll(null) : (Article[]) article.findAll(connection, null);
        return (articles.length > 0) ? articles[0] : null;
    }

    public Mouvement sortir(String quantite, String idMagasin, String date, String idUnite, Connection connection) throws Exception {
        boolean connect = false;
        Mouvement mouvement = new Mouvement(quantite, date);
        try {
            if (connection == null) { connection = this.getConnection(); connect = true; }

            Magasin magasin = Magasin.exists(idMagasin, connection); // Prendre le magasin
            if (magasin == null) throw new IllegalArgumentException("Magasin n'existe pas");
            
            Unite unite = (Unite) new Unite(idUnite).getById(connection);
            if (unite == null) throw new IllegalArgumentException("Unite n'existe pas");
            mouvement.setQuantite(mouvement.getQuantite() * unite.getValue());
            
            // Contrôle complexe (Quantite, Date antérieur)
            this.check(magasin, mouvement.getDate(), mouvement.getQuantite(), connection);
            
            mouvement.setMagasin(magasin);
            mouvement.setUnite(unite);
            mouvement.setArticle(this);
            mouvement.setStatus(0);
            mouvement.getColumns().get(0).setName("date_sortie");
            mouvement.setTable("sortie");
        } finally {
            if (connect) connection.close();
        }
        return mouvement;
    }

    // Contrôle complexe (Date antérieur, Insuffisance de stock)
    public void check(Magasin magasin, Date date, double quantite, Connection connection) throws Exception {
        
        /// Contrôle de date antérieure
        Mouvement lastMouvement = magasin.getLastMouvement(this, connection); // Prendre la dernière date de validation d'une sortie
        if (lastMouvement != null && lastMouvement.getDate().after(date))
            throw new IllegalArgumentException(String.format("Date anterieure non valide car dernier mouvement le %s", lastMouvement.getDate()));
        
        /// Contrôle de la quantite de stock
        double reste = magasin.getReste(this, date, connection); // Prendre le reste en stock de cette article
        if (quantite > reste)
            throw new IllegalArgumentException(String.format("Stock de %s insuffisant avec reste : %s", this.getNom(), reste));
        
    }

    public static void entrer(String code, String quantite, String idMagasin, String date, String prix, String unite) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Article article = Article.checkCodeExists(code, connection);
            if (article == null) throw new IllegalArgumentException("Article n'éxiste pas");
            Mouvement mouvement = article.entrer(quantite, idMagasin, date, prix, unite, connection);
            mouvement.insert(connection);
            connection.commit();
        }
    }

    public static Unite[] getUnite(String code) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Article article = Article.checkCodeExists(code, connection);
            return (Unite[]) new Unite().setArticle(article).findAll(connection, null);
        }
    }

    public Mouvement entrer(String quantite, String idMagasin, String date, String prix, String idUnite, Connection connection) throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.setTable("entree");
        mouvement.setPrimaryKeyName("id_entree");
        mouvement.setFunctionPK("nextval('seq_entree')");
        mouvement.setPrefix("E");
        mouvement.setArticle(this);
        mouvement.setDate(date);

        Unite unite = (Unite) new Unite(idUnite).getById(connection);
        if (unite == null) throw new IllegalArgumentException("Unite n'existe pas");
        
        mouvement.setQuantite(Double.valueOf(quantite) * unite.getValue());
        mouvement.setUnite(unite);
        mouvement.setPrixUnitaire(Double.valueOf(prix) / unite.getValue());
        
        Magasin magasin = Magasin.exists(idMagasin, connection); // Prendre le magasin
        if (magasin == null) throw new IllegalArgumentException("Magasin n'existe pas");
        
        mouvement.setMagasin(magasin);
        return mouvement;
    }

    public Unite getLastUnite(Connection connection) throws Exception {
        Unite unite = new Unite();
        unite.setArticle(this);
        return (Unite) unite.findAll(connection, null)[0];
    }

}