package model.mouvement;

import connection.BddObject;
import model.article.Article;

public class Unite extends BddObject {

    String nom;
    Article article;
    Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Article getArticle() {
        return article;
    }

    public Unite setArticle(Article article) {
        this.article = article;
        return this;
    }

    public Unite() throws Exception {
        super();
        this.setTable("v_unite");
        this.setPrimaryKeyName("id");
        this.setConnection("PostgreSQL");
    }

    public Unite(String id) throws Exception {
        this();
        this.setId(id);
    }
    
}
