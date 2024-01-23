package model.employees;

import java.sql.Date;

import connection.BddObject;
import connection.annotation.ColumnName;

public class Employee extends BddObject{
    String nom, prenom;
    @ColumnName("taux_horaire")
    Double tauxHoraire;
    Date date;
    String poste;
    int anciennete;
    String categorie;

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

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public void setAnciennete(int anciennete) {
        this.anciennete = anciennete;
    }

    public Employee[] getAnciennete() throws Exception {
        Employee employee = new Employee();
        
        employee.setTable("v_employees_postes");
        return (Employee[])employee.findAll(null);
    }

    public static void main(String[] args) throws Exception {
        Employee employee1 = new Employee();
        for (Employee employee : employee1.getAnciennete()) {
            System.out.println(employee.getId());
            System.out.println(employee.getNom());
            System.out.println(employee.getPrenom());
            System.out.println(employee.getPoste());
            System.out.println(employee.getAnciennete());
            System.out.println(employee.getTauxHoraire());
        }
    }



    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
