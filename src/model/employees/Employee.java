package model.employees;

import java.sql.Date;

import connection.BddObject;
import connection.annotation.ColumnName;

public class Employee extends BddObject{
    String nom, prenom;
    @ColumnName("taux_horaire")
    Double tauxHoraire;
    Date date;

    public Employee() throws Exception{
        super();
        this.setTable("Employees");
        this.setPrimaryKeyName("id_employee");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('empl_seq')");
        this.setCountPK(5);
        this.setPrefix("EMP");
    }

    public Double getTauxHoraire() {
        return tauxHoraire;
    }
    public void setTauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
