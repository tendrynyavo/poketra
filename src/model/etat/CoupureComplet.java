package model.etat;

public class CoupureComplet extends Coupure {

    double moyenne;
    double matin;
    double apres;

    public double getMatin() {
        return matin;
    }

    public void setMatin(double matin) {
        this.matin = matin;
    }

    public double getApres() {
        return apres;
    }

    public void setApres(double apres) {
        this.apres = apres;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public CoupureComplet() throws Exception {
        super();
        this.setTable("v_coupure");
    }

    // @Override
    // public EtatSolaire getEtatSolaire(int decallage) {
    //     double low = 0;
    //     double high = this.getPanneau().getPuissance();
    //     double mid = 0;
    //     EtatSolaire coupureTime = null;

    //     while (Math.abs(high - low) >= 1e-6) {
    //         mid = (low + high) / 2;
    //         coupureTime = this.getEtatSolaire(this.getDate(), this.getMeteo(), this.getPointage(), mid, decallage);
    //         if (coupureTime == null || coupureTime.getHeureCoupure().isAfter(this.getHeure().toLocalTime())) {
    //             low = mid;
    //         } else {
    //             high = mid;
    //         }
    //     }
    //     return coupureTime;
    // }
    
}