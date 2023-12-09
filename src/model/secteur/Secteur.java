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
import model.temps.Intervalle;

public class Secteur extends BddObject {

    String nom;
    Panneau panneau;
    Salle[] salles;
    EtatSolaire etat;

    public Salle[] getSalles() {
        return salles;
    }

    public String getSallesLettre() {
        StringBuilder string = new StringBuilder();
        for (Salle salle : this.getSalles()) {
            string.append(salle.getNom() + ", ");
        }
        return string.substring(0, string.length() - 2).toString();
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
            Pointage nombre = (Pointage) pointage.getIntervalle(date, heure);
            somme += nombre.getNombre();
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
            Meteo luminosite = (Meteo) meteo.getIntervalle(hours);
            // Nombre total du secteur
            int nombre = this.getNombreEtudiant(date, hours, pointage);
            // Details de l'état du panneau solaire
            etatSolaires[i] = new EtatSolaire(hours.toLocalTime(), luminosite.getLuminosite(), nombre, consommation * quotient, capacite, this.getPanneau().getPuissance() * quotient, this);
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

    public EtatSolaire getMoyenne(Date date, int decallage, Coupure[] coupures) throws IllegalArgumentException {
        double consommation = 0;
        int nombre = 0;
        int temp = 0;
        EtatSolaire etat = null;
        for (Coupure coupure : coupures) {
            etat = coupure.getEtatSolaire(decallage, 10);
            consommation += etat.getConsommation();
            if (date.compareTo(coupure.getDate()) != 0 && coupure.getDate().toLocalDate().getDayOfWeek() == date.toLocalDate().getDayOfWeek()) {
                nombre += coupure.getNombreMoyenne();
                temp++;
            }
        }
        if (temp == 0) throw new IllegalArgumentException(String.format("Pas de donnee a ce jour %s", date.toLocalDate().getDayOfWeek().toString()));
        return new EtatSolaire(Math.round(nombre / temp), consommation / coupures.length);
    }

    public Coupure predir(String date, int decallage, Connection connection) throws Exception {
        Coupure c = new Coupure();
        c.setId(this.getId());
        
        Coupure[] coupures = (Coupure[]) c.findAll(connection, null);
        c.setNom(this.getNom());
        c.setDate(date);
        c.setPanneau(this.getPanneau());
        c.setSalles(this.getSalles());
        Salle salle = new Salle();
        
        for (Coupure coupure : coupures) {
            salle.setSecteur(coupure);
            coupure.setSalles((Salle[]) salle.findAll(connection, null));
            coupure.setMeteo((Meteo) Intervalle.createIntervalle(coupure.getDate(), connection, new Meteo()));
            coupure.setPointage((Pointage) Intervalle.createIntervalle(coupure.getDate(), connection, new Pointage()));
        }
        
        EtatSolaire moyenne = this.getMoyenne(c.getDate(), 60, coupures);
        Meteo meteo = (Meteo) Intervalle.createIntervalle(c.getDate(), connection, new Meteo());
        EtatSolaire etat = this.getEtatSolaire(c.getDate(), meteo, moyenne.getNombre(), moyenne.getConsommation(), decallage);
        c.setEtat(etat);
        return c;
    }

    public static Coupure predir(String id, String date, String decallage) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Secteur secteur = (Secteur) new Secteur().setId(id).getById(connection);
            Salle salle = new Salle();
            salle.setSecteur(secteur);
            Salle[] salles = (Salle[]) salle.findAll(connection, null);
            secteur.setSalles(salles);
            return secteur.predir(date, Integer.parseInt(decallage), connection);
        }
    }
    
}