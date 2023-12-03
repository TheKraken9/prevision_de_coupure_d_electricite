package com.thekraken9.prevision_de_coupure_electrique.model;

import com.thekraken9.prevision_de_coupure_electrique.connecting.Connecting;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class Coupure {
    private int id;
    private Date date;
    private int id_secteur;
    private LocalTime heure;

    public Coupure() {
    }

    public Coupure(int id, Date date, int id_secteur, LocalTime heure) {
        this.id = id;
        this.date = date;
        this.id_secteur = id_secteur;
        this.heure = heure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_secteur() {
        return id_secteur;
    }

    public void setId_secteur(int id_secteur) {
        this.id_secteur = id_secteur;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public ArrayList<Coupure> getCoupureByDate(Connection connection) throws Exception {
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }

        ArrayList<Coupure> coupures = new ArrayList<>();
        String query = "SELECT * FROM coupure where date_coupure = ?";
        java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
        try {
            preparedStatement.setDate(1, this.date);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Coupure coupure = new Coupure();
                coupure.setId(resultSet.getInt("id"));
                coupure.setDate(resultSet.getDate("date_coupure"));
                coupure.setId_secteur(resultSet.getInt("id_secteur"));
                coupure.setHeure(resultSet.getTime("heure_coupure").toLocalTime());
                System.out.println(coupure.getId() + " " + coupure.getDate() + " " + coupure.getId_secteur() + " " + coupure.getHeure());
                coupures.add(coupure);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(isConnectionProvided) {
                connection.close();
            }
        }
        return coupures;
    }

    public Coupure getCoupureParDateParSecteur(Connection connection) throws Exception{
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }

        Coupure coupure = new Coupure();
        String query = "SELECT * FROM coupure where date_coupure = ? and id_secteur = ?";
        java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
        try {
            preparedStatement.setDate(1, this.date);
            preparedStatement.setInt(2, this.id_secteur);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                coupure.setId(resultSet.getInt("id"));
                coupure.setDate(resultSet.getDate("date_coupure"));
                coupure.setId_secteur(resultSet.getInt("id_secteur"));
                coupure.setHeure(resultSet.getTime("heure_coupure").toLocalTime());
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(isConnectionProvided) {
                connection.close();
            }
        }
        return coupure;
    }

    public ArrayList<Secteur> deviner_la_consommation_par_etudiant_par_la_methode_dichotomie(Connection connection, Date date) throws Exception {
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }
        ArrayList<Secteur> consommation_par_etudiant_par_secteur = new ArrayList<>();
        Historique historique = new Historique();
        ArrayList<Historique> historiques = new ArrayList<>();
        historique.setDate(date);
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Secteur> secteurs = new Secteur().getAllSecteur(null);
        double consommation_bas = 0;
        double consommation_haut = 0;
        double consommation_provisoire = 0;
        int nombre_etudiant_matin = 0;
        int nombre_etudiant_midi = 0;
        int id_secteur = 0;
        LocalTime heure_coupure = LocalTime.of(0,0,0);
        LocalTime heure_a_ajuster = LocalTime.of(0,0,0);
        LocalTime heure_null = LocalTime.of(0,0,0);
        long difference_de_minutes = (long) 0;
        long difference_de_minutes_temp = (long) 0;
        LocalTime meilleur_heure = LocalTime.of(0,0,0);
        double meilleur_consommation = 0;
        try{
            dates = historique.getDatesDeMemeJour();
            for (Date date1 : dates) {
                for (Secteur secteur : secteurs) {
                    consommation_bas = 0;
                    consommation_haut = 120;
                    consommation_provisoire = 60;
                    difference_de_minutes = (long) Math.abs(heure_coupure.until(LocalTime.of(0,0,0), java.time.temporal.ChronoUnit.MINUTES));
                    meilleur_heure = heure_a_ajuster;
                    meilleur_consommation = consommation_provisoire;
                    System.out.println("secteur: " + secteur.getNom() + " date: " + date1);
                    historiques = historique.getNombreEtudiantParDate(connection, secteur.getId());
                    if(historiques.get(0).getHeure().isBefore(LocalTime.of(11,59, 0))){
                        nombre_etudiant_matin = historiques.get(0).getNombre_etudiant();
                        nombre_etudiant_midi = historiques.get(1).getNombre_etudiant();
                    }else{
                        nombre_etudiant_matin = historiques.get(1).getNombre_etudiant();
                        nombre_etudiant_midi = historiques.get(0).getNombre_etudiant();
                    }
                    System.out.println("nombre etudiant matin: " + nombre_etudiant_matin + " nombre etudiant midi: " + nombre_etudiant_midi);
                    Coupure coupure = new Coupure();
                    coupure.setDate(date1);
                    System.out.println("date: " + date1);
                    coupure.setId_secteur(secteur.getId());
                    coupure = coupure.getCoupureParDateParSecteur(connection);
                    heure_coupure = coupure.getHeure();
                    System.out.println("heure coupure: " + heure_coupure);
                    while (heure_a_ajuster != heure_coupure){
                        heure_a_ajuster  = coupure.prevoirCoupure(date1, secteur, consommation_provisoire, nombre_etudiant_matin, nombre_etudiant_midi);
                        System.out.println("heure a ajuster: " + heure_a_ajuster);
                        System.out.println("heure coupure: " + heure_coupure);
                        if(heure_a_ajuster.equals(heure_coupure)){
                            meilleur_heure = heure_a_ajuster;
                            meilleur_consommation = consommation_provisoire;
                            break;
                        }
                        if (consommation_provisoire == consommation_bas || consommation_provisoire == consommation_haut) {
                            break;
                        }
                        if(heure_a_ajuster.equals(heure_null)){
                            System.out.println("consommation provisoire2: " + consommation_provisoire);
                            consommation_bas = consommation_provisoire;
                            consommation_provisoire = (consommation_bas+consommation_haut)/2;
                            continue;
                        }else if(heure_a_ajuster.isBefore(heure_coupure)){
                            System.out.println("consommation provisoire1: " + consommation_provisoire);
                            consommation_haut = consommation_provisoire;
                            consommation_provisoire = (consommation_bas+consommation_haut)/2;
                        }else{
                            System.out.println("consommation provisoire3: " + consommation_provisoire);
                            consommation_bas = consommation_provisoire;
                            consommation_provisoire = (consommation_bas+consommation_haut)/2;
                        }
                        System.out.println("---------------------------------------------");

                        difference_de_minutes_temp = Math.abs(heure_coupure.until(heure_a_ajuster, java.time.temporal.ChronoUnit.MINUTES));
                        if(difference_de_minutes_temp < difference_de_minutes){
                            difference_de_minutes = difference_de_minutes_temp;
                            meilleur_heure = heure_a_ajuster;
                            meilleur_consommation = consommation_provisoire;
                        }
                    }
                    System.out.println("date: " + date1 + " secteur: " + secteur.getNom() + " heure coupure: " + heure_coupure);
                    System.out.println("heure a ajuster: " + meilleur_heure + " consommation par etudiant: " + meilleur_consommation);

                    Secteur secteur1 = new Secteur();
                    secteur1.setId(secteur.getId());
                    secteur1.setNom(secteur.getNom());
                    secteur1.setConsommation_par_etudiant(meilleur_consommation);
                    secteur1.setEnergieSolaire(nombre_etudiant_matin);
                    secteur1.setEnergieBatterie(nombre_etudiant_midi);
                    consommation_par_etudiant_par_secteur.add(secteur1);
                }
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(isConnectionProvided) {
                connection.close();
            }
        }
        return consommation_par_etudiant_par_secteur;
    }

    public LocalTime prevoirCoupure(Date date, Secteur secteur, double consommation_par_etudiant, int nombre_etudiant_matin, int nombre_etudiant_midi) throws Exception{
        Luminosite luminosite = new Luminosite();
        luminosite.setDate(date);
        //ArrayList<Secteur> secteurs = new Secteur().getAllSecteur(null);
        ArrayList<Luminosite> luminosites = luminosite.getLuminositeByDate(null);
        if(luminosites.isEmpty()) {
            throw new Exception("Aucune luminosité trouvée pour cette date");
        }
        if(consommation_par_etudiant == 0) consommation_par_etudiant = 60;
        if(nombre_etudiant_matin == 0) nombre_etudiant_matin = 210;
        if(nombre_etudiant_midi == 0) nombre_etudiant_midi = 185;
        double consommation_totale = 0;
        double consommation_extra = 0;
        Secteur energiesSolaires = new Secteur();
        Secteur energiesBatteries = new Secteur();
        double energie_solaire = 0;
        double energie_batterie = 0;
        double luminosite_par_heure = 0;
        double energie_solaire_disponible = 0;
        double energie_batterie_disponible = 0;
        double limite_batterie = 0;
        LocalTime heure_exacte = LocalTime.of(0,0,0);
        //for (Secteur secteur : secteurs) {
            boolean batterie_a_sec = false;
            energiesSolaires = secteur.getEnergieSolaireParSecteur(null);
            energiesBatteries = secteur.getEnergieBatterieParSecteur(null);
            energie_solaire = energiesSolaires.getNombre() * energiesSolaires.getPuissance();
            energie_batterie = energiesBatteries.getNombre() * energiesBatteries.getPuissance();
            limite_batterie = energie_batterie/2;
            energie_batterie_disponible = energie_batterie;
            for(Luminosite lum : luminosites){
                if(batterie_a_sec){
                    break;
                }
                if(lum.getHeure().isBefore(LocalTime.of(11,59,00)))
                    consommation_totale = consommation_par_etudiant * nombre_etudiant_matin + consommation_extra;
                else
                    consommation_totale = consommation_par_etudiant * nombre_etudiant_midi + consommation_extra;
                luminosite_par_heure = lum.getEtat();
                energie_solaire_disponible = energie_solaire * luminosite_par_heure / 100;
                if(energie_solaire_disponible < consommation_totale){
                    double a_prendre_dans_la_batterie = consommation_totale - energie_solaire_disponible;
                    /*System.out.println("heure: " + lum.getHeure());
                    System.out.println("luminosite: " + luminosite_par_heure);
                    System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                    System.out.println("consommation totale: " + consommation_totale);
                    System.out.println("a prendre dans la batterie" + a_prendre_dans_la_batterie);*/
                    if(a_prendre_dans_la_batterie > energie_batterie_disponible-limite_batterie){
                        double ce_qui_reste_a_prendre_dans_la_batterie = energie_batterie_disponible-limite_batterie;
                        double nombre_de_minutes_restantes_dans_la_batterie = (((ce_qui_reste_a_prendre_dans_la_batterie)*60)/consommation_totale);
                        /*System.out.println("-----------------------------------------------");
                        System.out.println("ce qui reste dans la batterie: " + ce_qui_reste_a_prendre_dans_la_batterie);*/
                        energie_batterie_disponible = limite_batterie;
                        batterie_a_sec = true;
                        //System.out.println("batterie a sec dans le secteur: " + secteur.getNom() + " dans " + nombre_de_minutes_restantes_dans_la_batterie + " minutes");
                        double ce_qui_reste_a_prendre_dans_le_solaire = energie_solaire_disponible;
                        double nombre_de_minutes_restantes = (((ce_qui_reste_a_prendre_dans_le_solaire+ce_qui_reste_a_prendre_dans_la_batterie)*60)/consommation_totale);
                        /*System.out.println("ce qui reste dans le solaire: " + ce_qui_reste_a_prendre_dans_le_solaire + " ce qui reste dans la batterie: " + ce_qui_reste_a_prendre_dans_la_batterie);
                        System.out.println("secteur a sec dans " + nombre_de_minutes_restantes + " minutes");*/
                        heure_exacte = lum.getHeure().plusMinutes((long) nombre_de_minutes_restantes);
                        //System.out.println("heure exacte: " + heure_exacte);
                    }
                    if(!batterie_a_sec) {
                        energie_batterie_disponible -= a_prendre_dans_la_batterie;
                        System.out.println("makany @batterie");
                        /*System.out.println("luminosite: " + luminosite_par_heure);
                        System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                        System.out.println("consommation totale: " + consommation_totale);
                        System.out.println("-----------------------------------------------");*/
                    }
                }else {
                    System.out.println("solaire mbola ampy");
                    /*System.out.println("luminosite: " + luminosite_par_heure);
                    System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                    System.out.println("consommation totale: " + consommation_totale);
                    System.out.println("-----------------------------------------------");*/
                }
            }
            //System.out.println(" ----------------------------->>>>>>> coupure dans le secteur: " + secteur.getNom() + " a " + heure_exacte);
            return heure_exacte;
    }


    public LocalTime prevoirCoupureTest(Date date, Secteur secteur, double consommation_par_etudiant, int nombre_etudiant_matin, int nombre_etudiant_midi) throws Exception{
        Luminosite luminosite = new Luminosite();
        luminosite.setDate(date);
        //ArrayList<Secteur> secteurs = new Secteur().getAllSecteur(null);
        ArrayList<Luminosite> luminosites = luminosite.getLuminositeByDate(null);
        if(luminosites.isEmpty()) {
            throw new Exception("Aucune luminosité trouvée pour cette date");
        }
        if(consommation_par_etudiant == 0) consommation_par_etudiant = 95.15625;
        if(nombre_etudiant_matin == 0) nombre_etudiant_matin = 205;
        if(nombre_etudiant_midi == 0) nombre_etudiant_midi = 198;
        //System.out.println("consommation par etudiant: " + consommation_par_etudiant + " nombre etudiant matin: " + nombre_etudiant_matin + " nombre etudiant midi: " + nombre_etudiant_midi);
        double consommation_totale = 0;
        double consommation_extra = 0;
        Secteur energiesSolaires = new Secteur();
        Secteur energiesBatteries = new Secteur();
        double energie_solaire = 0;
        double energie_batterie = 0;
        double luminosite_par_heure = 0;
        double energie_solaire_disponible = 0;
        double energie_batterie_disponible = 0;
        double limite_batterie = 0;
        LocalTime heure_exacte = LocalTime.of(0,0,0);
        //for (Secteur secteur : secteurs) {
        boolean batterie_a_sec = false;
        energiesSolaires = secteur.getEnergieSolaireParSecteur(null);
        energiesBatteries = secteur.getEnergieBatterieParSecteur(null);
        energie_solaire = energiesSolaires.getNombre() * energiesSolaires.getPuissance();
        energie_batterie = energiesBatteries.getNombre() * energiesBatteries.getPuissance();
        limite_batterie = energie_batterie/2;
        energie_batterie_disponible = energie_batterie;
        for(Luminosite lum : luminosites){
            if(batterie_a_sec){
                break;
            }
            if(lum.getHeure().isBefore(LocalTime.of(11,59,00)))
                consommation_totale = consommation_par_etudiant * nombre_etudiant_matin + consommation_extra;
            else
                consommation_totale = consommation_par_etudiant * nombre_etudiant_midi + consommation_extra;
            luminosite_par_heure = lum.getEtat();
            energie_solaire_disponible = energie_solaire * luminosite_par_heure / 100;
            if(energie_solaire_disponible < consommation_totale){
                double a_prendre_dans_la_batterie = consommation_totale - energie_solaire_disponible;
                    /*System.out.println("heure: " + lum.getHeure());
                    System.out.println("luminosite: " + luminosite_par_heure);
                    System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                    System.out.println("consommation totale: " + consommation_totale);
                    System.out.println("a prendre dans la batterie" + a_prendre_dans_la_batterie);*/
                if(a_prendre_dans_la_batterie > energie_batterie_disponible-limite_batterie){
                    double ce_qui_reste_a_prendre_dans_la_batterie = energie_batterie_disponible-limite_batterie;
                    double nombre_de_minutes_restantes_dans_la_batterie = (((ce_qui_reste_a_prendre_dans_la_batterie)*60)/consommation_totale);
                        /*System.out.println("-----------------------------------------------");
                        System.out.println("ce qui reste dans la batterie: " + ce_qui_reste_a_prendre_dans_la_batterie);*/
                    energie_batterie_disponible = limite_batterie;
                    batterie_a_sec = true;
                    //System.out.println("batterie a sec dans le secteur: " + secteur.getNom() + " dans " + nombre_de_minutes_restantes_dans_la_batterie + " minutes");
                    double ce_qui_reste_a_prendre_dans_le_solaire = energie_solaire_disponible;
                    double nombre_de_minutes_restantes = (((ce_qui_reste_a_prendre_dans_le_solaire+ce_qui_reste_a_prendre_dans_la_batterie)*60)/consommation_totale);
                        /*System.out.println("ce qui reste dans le solaire: " + ce_qui_reste_a_prendre_dans_le_solaire + " ce qui reste dans la batterie: " + ce_qui_reste_a_prendre_dans_la_batterie);
                        System.out.println("secteur a sec dans " + nombre_de_minutes_restantes + " minutes");*/
                    heure_exacte = lum.getHeure().plusMinutes((long) nombre_de_minutes_restantes);
                    //System.out.println("heure exacte: " + heure_exacte);
                }
                if(!batterie_a_sec) {
                    energie_batterie_disponible -= a_prendre_dans_la_batterie;
                    System.out.println("makany @batterie");
                        /*System.out.println("luminosite: " + luminosite_par_heure);
                        System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                        System.out.println("consommation totale: " + consommation_totale);
                        System.out.println("-----------------------------------------------");*/
                }
            }else {
                System.out.println("solaire mbola ampy");
                    /*System.out.println("luminosite: " + luminosite_par_heure);
                    System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                    System.out.println("consommation totale: " + consommation_totale);
                    System.out.println("-----------------------------------------------");*/
            }
        }
        //System.out.println(" ----------------------------->>>>>>> coupure dans le secteur: " + secteur.getNom() + " a " + heure_exacte);
        return heure_exacte;
    }

    public ArrayList<Secteur> moyenne_par_secteur(Connection connection, Date date) throws Exception {
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }
        ArrayList<Secteur> secteurs = new ArrayList<>();
        ArrayList<Secteur> all_secteur = new Secteur().getAllSecteur(null);
        secteurs = deviner_la_consommation_par_etudiant_par_la_methode_dichotomie(null, date);
        ArrayList<Secteur> secteurs_moyenne = new ArrayList<>();
        for (Secteur secteur : all_secteur) {
            int id_secteur = secteur.getId();
            double consommation_moyenne = 0;
            double nombre_etudiant_moyenne_matin = 0;
            double nombre_etudiant_moyenne_midi = 0;
            int i = 0;
            for (Secteur sec : secteurs) {
                if (sec.getId() == id_secteur) {
                    consommation_moyenne += sec.getConsommation_par_etudiant();
                    nombre_etudiant_moyenne_matin += sec.getEnergieSolaire();
                    nombre_etudiant_moyenne_midi += sec.getEnergieBatterie();
                    i++;
                }
            }
            Secteur s = new Secteur();
            s.setId(id_secteur);
            s.setNom(secteur.getNom());
            s.setConsommation_par_etudiant(consommation_moyenne / i);
            s.setEnergieSolaire(nombre_etudiant_moyenne_matin / i);
            s.setEnergieBatterie(nombre_etudiant_moyenne_midi / i);
            secteurs_moyenne.add(s);
            System.out.println("i = " + i);
            System.out.println("secteur: " + s.getNom() + " consommation moyenne: " + s.getConsommation_par_etudiant() + " energie solaire moyenne: " + s.getEnergieSolaire() + " energie batterie moyenne: " + s.getEnergieBatterie());
        }
        return secteurs_moyenne;
    }

    public ArrayList<Coupure> coupure_par_secteur(Connection connection, Date date) throws Exception{
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }
        ArrayList<Coupure> coupures = new ArrayList<>();
        ArrayList<Secteur> secteurs = moyenne_par_secteur(null, date);
        for (Secteur secteur : secteurs) {
            Coupure coupure = new Coupure();
            coupure.setDate(date);
            coupure.setId_secteur(secteur.getId());
            coupure.setHeure(prevoirCoupureTest(date, secteur, secteur.getConsommation_par_etudiant(), (int) secteur.getEnergieSolaire(), (int) secteur.getEnergieBatterie()));
            coupures.add(coupure);
            System.out.println("secteur: " + secteur.getNom() + " heure: " + coupure.getHeure());
        }
        return coupures;
    }
    public static void main(String[] args) throws Exception {
        Coupure coupure = new Coupure();
        LocalTime heure = LocalTime.of(0,0,0);
        coupure.setDate(Date.valueOf("2023-11-02"));
        Secteur secteur = new Secteur();
        secteur.setId(3);
        secteur.setNom("secteur3");
        coupure.getCoupureByDate(null);
        //heure = coupure.prevoirCoupureTest(Date.valueOf("2023-10-19"),secteur, 0,0,0);
        //System.out.println(heure);
        //coupure.deviner_la_consommation_par_etudiant_par_la_methode_dichotomie(null, Date.valueOf("2023-11-02"));
        //coupure.moyenne_par_secteur(null, Date.valueOf("2023-11-02"));
        coupure.coupure_par_secteur(null, Date.valueOf("2023-11-02"));
    }

}
