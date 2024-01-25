package model.employees;

import java.sql.Date;
import connection.BddObject;
import connection.annotation.ColumnName;

public class Employee extends BddObject {
    
    String nom; 
    String prenom;
    Categorie categorie;
    @ColumnName("taux_horaire")
    Double tauxHoraire;
    Date date;
    String poste;
    Double anciennete;

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setCategorie(String categorie) throws Exception {
        this.categorie = (Categorie) new Categorie().setId(categorie);
    }

    public Double getAnciennete() {
        return this.anciennete;
    }

    public void setAnciennete(Double anciennete) {
        this.anciennete = anciennete;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public void setTauxHoraire(String tauxHoraire) {
        this.setTauxHoraire(Double.parseDouble(tauxHoraire));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.setDate(Date.valueOf(date));
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getRole() {
        String poste = (this.getPoste() == null) ? "" : this.getPoste();
        return this.getCategorie().getNom() + " " + poste;
    }

    public Employee() throws Exception{
        super();
        this.setTable("Employees");
        this.setPrimaryKeyName("id_employee");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('empl_seq')");
        this.setCountPK(5);
        this.setPrefix("EMP");
    }

    public Employee[] getAncienneteEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setTable("v_employees_postes");
        return (Employee[]) employee.findAll(null);
    }
}