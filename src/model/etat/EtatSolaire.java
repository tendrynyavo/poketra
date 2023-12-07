package model.etat;

import java.io.Serializable;
import java.time.LocalTime;
import model.meteo.Meteo;
import model.secteur.Secteur;

public class EtatSolaire implements Serializable {
    
    LocalTime heure;
    Meteo meteo;
    double luminosite;
    int nombre;
    double consommation;
    double capacite;
    double puissance;
    Secteur secteur;
    EtatSolaire[] details;
    

    public Meteo getMeteo() {
        return meteo;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }

    public double getPuissance() {
        return puissance;
    }

    public void setPuissance(double puissance) {
        this.puissance = puissance;
    }

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
        return (this.getLuminosite() / 10) * this.getPuissance();
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

    public EtatSolaire(LocalTime heure, double luminosite, int nombre, double consommation, double capacite, double puissance, Secteur secteur) {
        this.setHeure(heure);
        this.setLuminosite(luminosite);
        this.setNombre(nombre);
        this.setConsommation(consommation);
        this.setCapacite(capacite);
        this.setPuissance(puissance);
        this.setSecteur(secteur);
    }

    public EtatSolaire(int nombre, double consommation) {
        this.setNombre(nombre);
        this.setConsommation(consommation);
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