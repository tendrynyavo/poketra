package model.mouvement;

import java.sql.Date;

import connection.BddObject;
import model.employe.Employe;

public class Validation extends BddObject {

    Employe employe;
    Date date;
    Mouvement mouvement;

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mouvement getMouvement() {
        return mouvement;
    }

    public void setMouvement(Mouvement mouvement) {
        this.mouvement = mouvement;
    }

    public Validation() throws Exception {
        super();
        this.setTable("validation_mouvement");
        this.setPrefix("V");
        this.setCountPK(5);
        this.setPrimaryKeyName("id_validation");
        this.setFunctionPK("nextval('seq_validation')");
        this.setConnection("PostgreSQL");
    }

    public Validation(Employe employe, Date date, Mouvement mouvement) throws Exception {
        this();
        this.setEmploye(employe);
        this.setDate(date);
        this.setMouvement(mouvement);
    }
    
}