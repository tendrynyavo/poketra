package model.employe;

import java.sql.Date;
import connection.BddObject;
import connection.annotation.ColumnName;

public class Employe extends BddObject {

    String nom;
    String prenom;
    String telephone;
    String genre;
    String password;
    @ColumnName("date_naissance")
    Date dateNaissance;
    String email;

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getGenre() {
        return genre;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomPrenom() {
        return this.getNom() + " " + this.getPrenom();
    }
    
    public Employe() throws Exception {
        super();
        this.setTable("employe");
        this.setFunctionPK("nextval('s_employe')");
        this.setCountPK(6);
        this.setPrefix("EMP");
        this.setConnection("PostgreSQL");
        this.setPrimaryKeyName("id_employe");
    }

    public Employe(String id) throws Exception {
        this();
        this.setId(id);
    }
    
}