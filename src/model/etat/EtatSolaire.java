package model.etat;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalTime;
import model.meteo.Meteo;
import model.secteur.Secteur;

public class EtatSolaire implements Serializable {
    
    LocalTime heure;
    Meteo meteo;
    Meteo luminosite;
    double nombre;
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

    public String getConsommationFormat() {
        return format(this.getConsommation(), "#.##");
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

    public Meteo getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(Meteo luminosite) {
        this.luminosite = luminosite;
    }

    public double getNombre() {
        return nombre;
    }

    public String getNombreFormat() {
        return format(this.getNombre(), "#");
    }

    public void setNombre(double nombre) {
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
        return (this.getLuminosite().getLuminosite() / 10) * this.getPuissance();
    }

    public String getPuissanceSolaireFormat() {
        return format(this.getPuissanceSolaire(), "#.##");
    }

    public double getConsommationEtudiant() {
        return this.getNombre() * this.getConsommation();
    }

    public String getConsommationEtudiantFormat() {
        return format(this.getConsommationEtudiant(), "#.##");
    }

    public double getReste() {
        return this.getPuissanceSolaire() - this.getConsommationEtudiant();
    }

    public boolean isCoupure() {
        return this.getCapacite() <= (this.getSecteur().getPanneau().getCapacite() / 2);
    }

    public String getIcon() {
        return (this.isCoupure()) ? "off" : "mode";
    }

    public double getCapacite() {
        return capacite;
    }

    public String getCapaciteFormat() {
        return format(this.getCapacite(), "#.##");
    }

    public void setCapacite(double capacite) {
        if (capacite < 0) capacite = 0;
        if (capacite > this.getSecteur().getPanneau().getPuissance()) capacite = this.getSecteur().getPanneau().getPuissance();
        this.capacite = capacite;
    }

    public void setDetails(EtatSolaire[] details) {
        this.details = details;
    }

    public EtatSolaire(LocalTime heure, Meteo luminosite, double nombre, double consommation, double capacite, double puissance, Secteur secteur) {
        this.setHeure(heure);
        this.setLuminosite(luminosite);
        this.setNombre(nombre);
        this.setConsommation(consommation);
        this.setSecteur(secteur);
        this.setCapacite(capacite);
        this.setPuissance(puissance);
    }

    public EtatSolaire(int nombre, double consommation) {
        this.setNombre(nombre);
        this.setConsommation(consommation);
    }

    public EtatSolaire() {
        
    }

    // ! Optimisation dichotomie
    public LocalTime getHeureCoupure() {
        for (EtatSolaire etatSolaire : this.getDetails()) {
            if (etatSolaire.isCoupure()) return etatSolaire.getHeure();
        }
        return LocalTime.of(18, 00);
    }

    public static String format(double number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(number);
    }

}