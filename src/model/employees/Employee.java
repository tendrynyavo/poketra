package model.employees;

import connection.BddObject;

public class Employee extends BddObject{
    String nom;
    Categorie categories;

    public Employee() throws Exception{
        super();
        this.setTable("Employees");
        this.setPrimaryKeyName("id_employee");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('empl_seq')");
        this.setCountPK(5);
        this.setPrefix("EMP");
    }
    
    public String getNom() {
        return nom;
    }
    public Categorie getCategories() {
        return categories;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setCategories(Categorie categories) {
        this.categories = categories;
    }

    
}
