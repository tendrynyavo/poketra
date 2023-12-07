package model.secteur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import connection.BddObject;
import model.etat.Coupure;
import model.etat.EtatSolaire;
import model.meteo.Meteo;
import model.panneau.Panneau;
import model.pointage.Pointage;

public class Secteur extends BddObject {

    String nom;
    Panneau panneau;
    Salle[] salles;
    EtatSolaire etat;

    public Salle[] getSalles() {
        return salles;
    }

    public void setSalles(Salle[] salles) throws IllegalArgumentException {
        if (salles.length <= 0) throw new IllegalArgumentException("Salles invalides");
        this.salles = salles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws IllegalArgumentException {
        if (nom.isEmpty()) throw new IllegalArgumentException("Nom est vide");
        this.nom = nom;
    }

    public Panneau getPanneau() {
        return panneau;
    }

    public void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    public EtatSolaire getEtat() {
        return etat;
    }

    public void setEtat(EtatSolaire etat) {
        this.etat = etat;
    }

    public Secteur() throws Exception {
        super();
        this.setTable("secteur");
        this.setConnection("PostgreSQL");
        this.setPrimaryKeyName("id_secteur");
    }

    public int getNombreEtudiant(Date date, Time heure, Pointage pointage) {
        int somme = 0;
        for (Salle s : this.getSalles()) {
            pointage.setSalle(s);
            somme += ((Pointage) pointage.getIntervalle(date, heure)).getNombre();
        }
        return somme;
    }

    public EtatSolaire getEtatSolaire(Date date, Meteo meteo, Pointage pointage, double consommation, int decallage) {
        Time hours = Time.valueOf("08:00:00");
        EtatSolaire[] etatSolaires = new EtatSolaire[9 * decallage + 1];
        EtatSolaire etat = new EtatSolaire();
        double capacite = this.getPanneau().getCapacite();
        double quotient = (1 / (double) decallage);
        double minutes = 60.0 * quotient;
        for (int i = 0; i < etatSolaires.length; i++) {
            // Luminosite par heure par date
            double luminosite = ((Meteo) meteo.getIntervalle(date, hours)).getLuminosite();
            // Nombre total du secteur
            int nombre = this.getNombreEtudiant(date, hours, pointage);
            etatSolaires[i] = new EtatSolaire(hours.toLocalTime(), luminosite, nombre, consommation * quotient, capacite, this.getPanneau().getPuissance() * quotient, this);
            capacite -= etatSolaires[i].getReste(); // Capacite restante de la batterie
            hours = Time.valueOf(hours.toLocalTime().plusMinutes((long) (minutes))); // Ajouter une heure
        }
        etat.setConsommation(consommation);
        etat.setDetails(etatSolaires);
        return etat;
    }

    public EtatSolaire getEtatSolaire(Date date, Meteo meteo, int nombre, double consommation, int decallage) throws Exception {
        Pointage pointage = new Pointage();
        pointage.setDate(date);
        Pointage[] pointages = new Pointage[this.getSalles().length];
        for (int i = 0; i < pointages.length; i++) {
            pointages[i] = new Pointage(date, Time.valueOf("8:00:00"), Time.valueOf("17:00:00"), this.getSalles()[i], nombre / this.getSalles().length);
        }
        pointage.setDetails(pointages);
        return this.getEtatSolaire(date, meteo, pointage, consommation, decallage);
    }

    public EtatSolaire getMoyenne(Date date, Meteo meteo, Pointage pointage, Coupure[] coupures) {
        double consommation = 0;
        int nombre = 0;
        int temp = 0;
        EtatSolaire etat = null;
        for (Coupure coupure : coupures) {
            etat = coupure.getEtatSolaire(meteo, pointage, 1);
            consommation += etat.getConsommation();
            if (coupure.getDate().toLocalDate().getDayOfWeek() == date.toLocalDate().getDayOfWeek()) {
                nombre += coupure.getTotalNombre(pointage) / 2;
                temp++;
            }
        }
        return new EtatSolaire(Math.round(nombre / temp), consommation / coupures.length);
    }

    public Coupure predir(Meteo meteo, Pointage pointage, String date, int decallage) throws Exception {
        Coupure coupure = new Coupure();
        coupure.setId(this.getId());
        try (Connection connection = BddObject.getPostgreSQL()) {
            Date demande = Date.valueOf(date);
            Coupure[] coupures = (Coupure[]) coupure.findAll(connection, null);
            Salle salle = new Salle();
            
            for (Coupure c : coupures) {
                salle.setSecteur(coupure);
                Salle[] salles = (Salle[]) salle.findAll(connection, null);
                c.setSalles(salles);
            }
            
            EtatSolaire moyenne = this.getMoyenne(demande, meteo, pointage, coupures);
            EtatSolaire etat = this.getEtatSolaire(demande, meteo, moyenne.getNombre(), moyenne.getConsommation(), decallage);
            coupure.setEtat(etat);
        }
        return coupure;
    }

    public static void main(String[] args) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Secteur secteur = new Secteur();
            secteur.setId("SEC001");
            Panneau panneau = new Panneau();
            panneau.setCapacite(40000);
            panneau.setPuissance(15000);
            secteur.setPanneau(panneau);
            Salle salle = new Salle();
            salle.setSecteur(secteur);
            Salle[] salles = (Salle[]) salle.findAll(connection, null);
            Date date = Date.valueOf("2023-11-27");
            // Date date2 = Date.valueOf("2023-12-04");
            // System.out.println(date.toLocalDate().getDayOfWeek() == date2.toLocalDate().getDayOfWeek());
            secteur.setSalles(salles);
            // Coupure coupure = secteur.predir("2023-12-04", 1);
            // Meteo meteo = Meteo.createMeteo(connection);
            Pointage pointage = Pointage.createPointage(connection);

            // Coupure[] coupures = (Coupure[]) new Coupure().findAll(connection, null);
            // salle = new Salle();
            
            // for (Coupure coupure : coupures) {
            //     salle.setSecteur(coupure);
            //     coupure.setSalles((Salle[]) salle.findAll(connection, null));
            // }

            // EtatSolaire e = secteur.getMoyenne(date2, meteo, pointage, coupures);
            // System.out.println(e.getConsommation());
            // System.out.println(e.getNombre());
            Meteo meteo = Meteo.createMeteo(connection);
            EtatSolaire etat = secteur.getEtatSolaire(date, meteo, 170, 60.23, 2);
            // EtatSolaire etat = coupure.getEtat();
            System.out.println(etat.getHeureCoupure());
            System.out.println(etat.getConsommation());
            EtatSolaire[] details = etat.getDetails();
            for (EtatSolaire etatSolaire : details) {
                System.out.println(etatSolaire.getHeure() + " " + etatSolaire.getConsommationEtudiant() + " " + etatSolaire.getPuissanceSolaire() + " " + etatSolaire.getReste() + " " + etatSolaire.getCapacite() + " " + etatSolaire.isCoupure());
            }
        }
    }
    
}