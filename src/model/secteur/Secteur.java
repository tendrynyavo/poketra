package model.secteur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connection.BddObject;
import model.etat.Coupure;
import model.etat.CoupureComplet;
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

    public Secteur(String id) throws Exception {
        this();
        this.setId(id);
    }

    public double getNombreEtudiant(Date date, Time heure, Pointage pointage) {
        double somme = 0.0;
        for (Salle s : this.getSalles()) {
            pointage.setSalle(s);
            Pointage nombre = (Pointage) pointage.getIntervalle(date, heure);
            somme += nombre.getNombre();
        }
        return somme;
    }

    // * Fonction pour la simulation d'etat du panneau d'un secteur avec les meteos et les pointages
    public EtatSolaire getEtatSolaire(Date date, Meteo meteo, Pointage pointage, double consommation, int decallage) throws IllegalArgumentException {
        if (decallage < 0) 
            throw new IllegalArgumentException("Decallage invalide");
        Time hours = Time.valueOf("08:00:00");
        EtatSolaire[] etatSolaires = new EtatSolaire[9 * decallage + 1];
        EtatSolaire etat = new EtatSolaire();
        double capacite = this.getPanneau().getCapacite();
        double quotient = (1 / (double) decallage);
        double minutes = 60.0 * quotient;
        for (int i = 0; i < etatSolaires.length; i++) {
            
            // Luminosite par heure par date
            // ? Impossible dans la base de donnée
            Meteo luminosite = (Meteo) meteo.getIntervalle(hours);
            
            // Nombre total du secteur
            // ! Optimisation dans la base
            double nombre = this.getNombreEtudiant(date, hours, pointage); // TODO: Optimisation en dichotomie
            
            // Details de l'état du panneau solaire
            etatSolaires[i] = new EtatSolaire(hours.toLocalTime(), luminosite, nombre, consommation * quotient, capacite, this.getPanneau().getPuissance() * quotient, this);
            if (!etatSolaires[i].isCoupure()) capacite -= etatSolaires[i].getReste(); // Capacite restante de la batterie
            hours = Time.valueOf(hours.toLocalTime().plusMinutes((long) (minutes))); // Ajouter une heure
        }
        etat.setConsommation(consommation);
        etat.setDetails(etatSolaires);
        return etat;
    }

    public EtatSolaire getEtatSolaire(Date date, Meteo meteo, double nombre, double consommation, int decallage) throws Exception {
        Pointage pointage = new Pointage();
        pointage.setDate(date);
        Pointage[] pointages = new Pointage[this.getSalles().length];
        for (int i = 0; i < pointages.length; i++) {
            pointages[i] = new Pointage(date, Time.valueOf("8:00:00"), Time.valueOf("17:00:00"), this.getSalles()[i], nombre / this.getSalles().length);
        }
        pointage.setDetails(pointages);
        return this.getEtatSolaire(date, meteo, pointage, consommation, decallage);
    }

    public EtatSolaire getMoyenneEtatSolaire(Date date, Connection connection) throws Exception {
        double consommation = 0.0;
        double nombre = 0.0;
        double temp = 0.0;
        EtatSolaire etat = null;

        // TODO: Possible optimisation dans la base
        // ! Request
        Coupure[] coupures = this.getCoupures(connection);

        for (Coupure coupure : coupures) {
            
            // Recherche de la consommation moyenne par étudiant
            etat = coupure.getEtatSolaire(60);
            consommation += etat.getConsommation();

            if (date.compareTo(coupure.getDate()) != 0 && coupure.getDate().toLocalDate().getDayOfWeek() == date.toLocalDate().getDayOfWeek()) {
                nombre += coupure.getMoyenne();
                temp++;
            }
        }
        if (temp == 0) throw new IllegalArgumentException(String.format("Pas de donnee a ce jour %s pour le %s", date.toLocalDate().getDayOfWeek().toString(), this.getNom()));
        return new EtatSolaire((int) Math.round(nombre / temp), consommation / coupures.length);
    }

    public Coupure predir(String date, int decallage, Connection connection) throws Exception {
        Coupure c = new Coupure();
        c.setId(this.getId());
        c.setNom(this.getNom());
        c.setDate(date);
        c.setPanneau(this.getPanneau());
        c.setSalles(this.getSalles());
        
        // TODO: Possible Optimisation de nombre moyenne dans la base
        // Prendre la moyenne de consommation et la moyenne de nombre étudiant
        EtatSolaire moyenne = this.getMoyenneEtatSolaire(c.getDate(), connection);

        // Prendre la meteo à cette date
        // ! Request
        Meteo meteo = (Meteo) Intervalle.createIntervalle(c.getDate(), connection, new Meteo());
        
        // Prediction de l'etat du panneau solaire
        EtatSolaire etat = this.getEtatSolaire(c.getDate(), meteo, moyenne.getNombre(), moyenne.getConsommation(), decallage);
        EtatSolaire minute = this.getEtatSolaire(c.getDate(), meteo, moyenne.getNombre(), moyenne.getConsommation(), 60);
        c.setEtat(etat);
        c.setHeure(Time.valueOf(minute.getHeureCoupure()));
        return c;
    }

    // TODO Optimisation Base de donnée
    public Coupure[] getCoupures(Connection connection) throws Exception {
        Coupure c = new CoupureComplet();
        c.setId(this.getId());
        // ! Request
        Coupure[] coupures = (Coupure[]) c.findAll(connection, null);
        Salle salle = new Salle();
        
        for (Coupure coupure : coupures) {
            salle.setSecteur(coupure);
            // ! Request par ligne
            coupure.setSalles((Salle[]) salle.findAll(connection, null));
            // ! Request par ligne
            coupure.setMeteo((Meteo) Intervalle.createIntervalle(coupure.getDate(), connection, new Meteo()));
            // ! Request par ligne
            coupure.setPointage((Pointage) Intervalle.createIntervalle(coupure.getDate(), connection, new Pointage()));
        }
        return coupures;
    }

    // public Coupure[] getCoupures(Connection connection) throws Exception {
    //     String sql = "SELECT * FROM v_coupure_salle WHERE id_secteur = ?";
    //     String tmp = "";
    //     List<Coupure> coupures = new ArrayList<>();
    //     boolean connect = false;
    //     Connection connection = null;
    //     PreparedStatement statement = null;
    //     ResultSet result = null;
    //     try {
    //         if (connection == null) {
    //             connection = this.getConnection();
    //             connect = true;
    //         }
    //         statement = connection.prepareStatement(sql);
    //         statement.setString(1, this.getId());
    //         result = statement.executeQuery();
    //         Coupure coupure = null;
    //         List<Salle> salles = new ArrayList<>();
    //         while (result.next()) {
    //             if (!tmp.equals(result.getString(1))) {
    //                 if (coupure != null) {
    //                     coupure.setSalles(salles.toArray(new Salle[salles.size()]));
    //                     salles.clear();
    //                 }
    //                 coupure = new Coupure(result.getString(1), result.getString(2), result.getTime(3), result.getDate(4), result.getDouble(5));
    //                 coupure.setPanneau(new Panneau(result.getString(9), result.getString(10), result.getDouble(12), result.getDouble(11)));
    //                 coupures.add(coupure);
    //                 tmp = coupure.getId();
    //             }
    //             salles.add(new Salle(result.getString(6), result.getString(7), coupure));
    //         }
    //         if (coupure != null)
    //             coupure.setSalles(salles.toArray(new Salle[salles.size()]));
    //     } finally {
    //         if (result != null) {
    //             result.close();
    //         }
    //         if (statement != null) {
    //             statement.close();
    //         }
    //         if (connect) {
    //             connection.close();
    //         }
    //     }
    //     return coupures.toArray(new Coupure[coupures.size()]);
    // }

    public String getCoupurestoJson(Connection connection) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this.getCoupures(connection));
    }

    public static Coupure predir(String id, String date, String decallage) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            // ! Request
            Secteur secteur = (Secteur) new Secteur().setId(id).getById(connection);
            Salle salle = new Salle();
            salle.setSecteur(secteur);
            // ! Request
            Salle[] salles = (Salle[]) salle.findAll(connection, null);
            secteur.setSalles(salles);
            return secteur.predir(date, Integer.parseInt(decallage), connection);
        }
    }

    public static void main(String[] args) throws Exception {
        Secteur.predir("SEC002", "2023-12-04", "1");
    }

}