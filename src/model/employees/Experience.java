package model.employees;

import connection.BddObject;

public class Experience extends BddObject {

    String id_experience,designation;
    Double debut, fin, augmentation;

    public Experience() throws Exception {
        this.setTable("experience");
        this.setPrimaryKeyName("id_experience");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('exp_seq')");
        this.setCountPK(5);
        this.setPrefix("EXP");
    }

    public String getId_experience() {
        return id_experience;
    }

    public void setId_experience(String id_experience) {
        this.id_experience = id_experience;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getDebut() {
        return debut;
    }

    public void setDebut(Double debut) {
        this.debut = debut;
    }

    public void setDebut(String debut) {
        this.setDebut(Double.parseDouble(debut));
    }

    public Double getFin() {
        return fin;
    }

    public void setFin(Double fin) {
        this.fin = fin;
    }

    public void setFin(String fin) {
        this.setFin(Double.parseDouble(fin));
    }

    public Double getAugmentation() {
        return augmentation;
    }

    public void setAugmentation(Double augmentation) {
        this.augmentation = augmentation;
    }

    public void setAugmentation(String augmentation) {
        this.setAugmentation(Double.parseDouble(augmentation));
    }
}
