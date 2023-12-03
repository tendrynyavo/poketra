package model.etat;

import java.io.Serializable;
import java.time.LocalTime;
import model.secteur.Secteur;

public class EtatSolaire implements Serializable {
    
    LocalTime heure;
    double luminosite;
    int nombre;
    double consommation;
    double capacite;
    Secteur secteur;
    EtatSolaire[] details;

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public double getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(double luminosite) {
        this.luminosite = luminosite;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public EtatSolaire[] getDetails() {
        return details;
    }

    public double getPuissanceSolaire() {
        return (this.getLuminosite() / 10) * secteur.getPanneau().getPuissance();
    }

    public double getConsommationEtudiant() {
        return this.getNombre() * this.getConsommation();
    }

    public double getReste() {
        double reste = this.getConsommationEtudiant() - this.getPuissanceSolaire();
        if (reste < 0) reste = 0;
        return reste;
    }

    public boolean isCoupure() {
        return this.getCapacite() <= (this.getSecteur().getPanneau().getCapacite() / 2);
    }

    public double getCapacite() {
        return capacite;
    }

    public void setCapacite(double capacite) {
        if (capacite < 0) capacite = 0;
        this.capacite = capacite;
    }

    public void setDetails(EtatSolaire[] details) {
        this.details = details;
    }

    public EtatSolaire(LocalTime heure, double luminosite, int nombre, double consommation, double capacite, Secteur secteur) {
        this.setHeure(heure);
        this.setLuminosite(luminosite);
        this.setNombre(nombre);
        this.setConsommation(consommation);
        this.setCapacite(capacite);
        this.setSecteur(secteur);
    }

    public EtatSolaire() {
        
    }

    public LocalTime getHeureCoupure() {
        for (EtatSolaire etatSolaire : this.getDetails()) {
            if (etatSolaire.isCoupure())
                return etatSolaire.getHeure();
        }
        return LocalTime.of(17, 00);
    }

}