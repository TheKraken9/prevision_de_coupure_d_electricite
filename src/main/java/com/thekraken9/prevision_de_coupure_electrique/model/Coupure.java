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
    private double consommation_par_etudiant;
    private double nombre_etudiant_matin;
    private double nombre_etudiant_midi;

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

    public double getConsommation_par_etudiant() {
        return consommation_par_etudiant;
    }

    public void setConsommation_par_etudiant(double consommation_par_etudiant) {
        this.consommation_par_etudiant = consommation_par_etudiant;
    }

    public double getNombre_etudiant_matin() {
        return nombre_etudiant_matin;
    }

    public void setNombre_etudiant_matin(double nombre_etudiant_matin) {
        this.nombre_etudiant_matin = nombre_etudiant_matin;
    }

    public double getNombre_etudiant_midi() {
        return nombre_etudiant_midi;
    }

    public void setNombre_etudiant_midi(double nombre_etudiant_midi) {
        this.nombre_etudiant_midi = nombre_etudiant_midi;
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
                //System.out.println(coupure.getId() + " " + coupure.getDate() + " " + coupure.getId_secteur() + " " + coupure.getHeure());
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

    public ArrayList<Secteur> devinerLaConsommationParEtudiantParLaMethodeDichotomie(Connection connection, Date date) throws Exception {
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
        ArrayList<Secteur> secteurs = new Secteur().getAllSecteur(connection);
        double consommation_bas = 0;
        double consommation_haut = 0;
        double consommation_provisoire = 0;
        double conso_rollback = 0;
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
            dates = historique.getAllDates(connection);
            for (Date date1 : dates) {
                for (Secteur secteur : secteurs) {

                    Coupure coupure = new Coupure();
                    coupure.setDate(date1);
                    historique.setDate(date1);
                    //System.out.println("date: " + date1);
                    coupure.setId_secteur(secteur.getId());
                    coupure = coupure.getCoupureParDateParSecteur(connection);
                    heure_coupure = coupure.getHeure();

                    consommation_bas = 0;
                    consommation_haut = 120;
                    consommation_provisoire = 60;
                    conso_rollback = 0;
                    difference_de_minutes = (long) Math.abs(heure_coupure.until(LocalTime.of(0,0,0), java.time.temporal.ChronoUnit.MINUTES));
                    System.out.println("difference de minutes: " + difference_de_minutes);
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

                    //System.out.println("heure coupure: " + heure_coupure);
                    while (heure_a_ajuster != heure_coupure){
                        consommation_provisoire = (consommation_bas+consommation_haut)/2;
                        heure_a_ajuster  = coupure.prevoirCoupure(connection, date1, secteur, consommation_provisoire, nombre_etudiant_matin, nombre_etudiant_midi);
                        System.out.println("consommation = " + consommation_provisoire);
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
                            //consommation_provisoire = (consommation_bas+consommation_haut)/2;
                            continue;
                        }else if(heure_a_ajuster.isBefore(heure_coupure)){
                            System.out.println("consommation provisoire1: " + consommation_provisoire);
                            System.out.println("consommation provisoire1: " + consommation_provisoire);
                            consommation_haut = consommation_provisoire;
                            //conso_rollback = consommation_provisoire;
                            //consommation_provisoire = (consommation_bas+consommation_haut)/2;
                        }else{
                            System.out.println("consommation provisoire3: " + consommation_provisoire);
                            consommation_bas = consommation_provisoire;
                            //conso_rollback = consommation_provisoire;
                            //consommation_provisoire = (consommation_bas+consommation_haut)/2;
                        }
                        System.out.println("---------------------------------------------");

                        difference_de_minutes_temp = Math.abs(heure_coupure.until(heure_a_ajuster, java.time.temporal.ChronoUnit.MINUTES));
                        if(meilleur_heure == heure_a_ajuster){
                            continue;
                        }
                        if(difference_de_minutes_temp < difference_de_minutes){
                            difference_de_minutes = difference_de_minutes_temp;
                            meilleur_heure = heure_a_ajuster;
                            meilleur_consommation = (consommation_provisoire);
                        }
                    }
                    System.out.println("date: " + date1 + " secteur: " + secteur.getNom() + " heure coupure: " + heure_coupure);
                    System.out.println("heure le plus proche: " + meilleur_heure + " consommation par etudiant: " + meilleur_consommation);

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

    /*public ArrayList<Secteur> devinerLaConsommationParEtudiantParLaMethodeIteration(Connection connection, Date date) throws Exception {
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
        ArrayList<Secteur> secteurs = new Secteur().getAllSecteur(connection);
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
            dates = historique.getAllDates(connection);
            for (Date date1 : dates) {
                for (Secteur secteur : secteurs) {
                    System.out.println("secteur: " + secteur.getNom() + " date: " + date1);
                    consommation_provisoire = 478;
                    difference_de_minutes = (long) Math.abs(heure_coupure.until(LocalTime.of(0, 0, 0), java.time.temporal.ChronoUnit.MINUTES));
                    meilleur_heure = heure_a_ajuster;
                    meilleur_consommation = consommation_provisoire;
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
                    //System.out.println("date: " + date1);
                    coupure.setId_secteur(secteur.getId());
                    coupure = coupure.getCoupureParDateParSecteur(connection);
                    heure_coupure = coupure.getHeure();
                    System.out.println("heure coupure: " + heure_coupure);
                    while (heure_a_ajuster != heure_coupure){
                        heure_a_ajuster  = coupure.prevoirCoupure(connection, date1, secteur, consommation_provisoire, nombre_etudiant_matin, nombre_etudiant_midi);
                        System.out.println("heure a ajuster: " + heure_a_ajuster);
                        if(heure_a_ajuster.equals(heure_coupure)){
                            meilleur_heure = heure_a_ajuster;
                            meilleur_consommation = consommation_provisoire;
                            break;
                        }
                        if(heure_a_ajuster.equals(heure_null)){
                            consommation_provisoire += 1;
                            continue;
                        }
                        if(heure_a_ajuster.isBefore(heure_coupure)){
                            consommation_provisoire -= 1;
                        }else{
                            System.out.println("ato");
                            consommation_provisoire += 1;
                        }
                        difference_de_minutes_temp = Math.abs(heure_coupure.until(heure_a_ajuster, java.time.temporal.ChronoUnit.MINUTES));
                        if(difference_de_minutes_temp < difference_de_minutes){
                            difference_de_minutes = difference_de_minutes_temp;
                            meilleur_heure = heure_a_ajuster;
                            meilleur_consommation = consommation_provisoire;
                        }
                        System.out.println("consommation provisoire: " + consommation_provisoire);
                        System.out.println("heure coupure: " + heure_coupure);
                        //System.out.println("heure a ajuster: " + heure_a_ajuster);
                    }
                    System.out.println("date: " + date1 + " secteur: " + secteur.getNom() + " heure coupure: " + heure_coupure);
                    System.out.println("heure le plus proche: " + meilleur_heure + " consommation par etudiant: " + meilleur_consommation);

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
    }*/

    public ArrayList<Secteur> nombreEtudiantParSecteur(Connection connection, Date date) throws Exception {
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }
        ArrayList<Secteur> secteurs = new Secteur().getAllSecteur(connection);
        ArrayList<Secteur> secteurs1 = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        Historique historique = new Historique();
        historique.setDate(date);
        dates = historique.getDatesDeMemeJour();
        //System.out.println("date taille ----------------------" + dates.size());
        int nombre_etudiant_matin = 0;
        int nombre_etudiant_midi = 0;
        for (Date date1 : dates) {
            //System.out.println("datebeeeeee :" + date1);
            for (Secteur secteur : secteurs) {
                ArrayList<Historique> historiques = new ArrayList<>();
                historique.setDate(date1);
                historiques = historique.getNombreEtudiantParDate(connection, secteur.getId());
                if (historiques.get(0).getHeure().isBefore(LocalTime.of(11, 59, 0))) {
                    nombre_etudiant_matin = historiques.get(0).getNombre_etudiant();
                    nombre_etudiant_midi = historiques.get(1).getNombre_etudiant();
                } else {
                    nombre_etudiant_matin = historiques.get(1).getNombre_etudiant();
                    nombre_etudiant_midi = historiques.get(0).getNombre_etudiant();
                }
                Secteur sect = new Secteur();
                sect.setId(secteur.getId());
                sect.setNom(secteur.getNom());
                sect.setEnergieSolaire(nombre_etudiant_matin);
                sect.setEnergieBatterie(nombre_etudiant_midi);
                //System.out.println("Dateuuuuhhhhhhhh : " + date1 + " secteur: " + secteur.getNom() + " nombre etudiant matin: " + nombre_etudiant_matin + " nombre etudiant midi: " + nombre_etudiant_midi);
                secteurs1.add(sect);
            }
        }
        return secteurs1;
    }

    public LocalTime prevoirCoupure(Connection connection, Date date, Secteur secteur, double consommation_par_etudiant, int nombre_etudiant_matin, int nombre_etudiant_midi) throws Exception{
        Luminosite luminosite = new Luminosite();
        luminosite.setDate(date);
        ArrayList<Luminosite> luminosites = luminosite.getLuminositeByDate(connection);
        if(luminosites.isEmpty()) {
            throw new Exception("Aucune luminosité trouvée pour cette date");
        }
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

        boolean batterie_a_sec = false;
        energiesSolaires = secteur.getEnergieSolaireParSecteur(connection);
        energiesBatteries = secteur.getEnergieBatterieParSecteur(connection);
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
            energie_solaire_disponible = energie_solaire * luminosite_par_heure / 10;
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
                    //System.out.println("makany @batterie");
                    /*System.out.println("luminosite: " + luminosite_par_heure);
                    System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                    System.out.println("consommation totale: " + consommation_totale);
                    System.out.println("-----------------------------------------------");*/
                }
            }else {
                double qu_on_n_utilise_pas_dans_le_solaire = energie_solaire_disponible - consommation_totale;
                energie_batterie_disponible += qu_on_n_utilise_pas_dans_le_solaire;
                if(energie_batterie_disponible > energie_batterie)
                    energie_batterie_disponible = energie_batterie;
                //System.out.println("solaire mbola ampy");
                /*System.out.println("luminosite: " + luminosite_par_heure);
                System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                System.out.println("consommation totale: " + consommation_totale);
                System.out.println("-----------------------------------------------");*/
            }
        }
        //System.out.println(" ----------------------------->>>>>>> coupure dans le secteur: " + secteur.getNom() + " a " + heure_exacte);
        return heure_exacte;
    }


    public ArrayList<DetailsConso> detailsCoupure(Connection connection, Date date, Secteur secteur, double consommation_par_etudiant, int nombre_etudiant_matin, int nombre_etudiant_midi) throws Exception{
        Luminosite luminosite = new Luminosite();
        luminosite.setDate(date);
        ArrayList<Luminosite> luminosites = luminosite.getLuminositeByDate(connection);
        if(luminosites.isEmpty()) {
            throw new Exception("Aucune luminosité trouvée pour cette date");
        }
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
        ArrayList<DetailsConso> detailsConsos = new ArrayList<>();

        boolean batterie_a_sec = false;
        energiesSolaires = secteur.getEnergieSolaireParSecteur(connection);
        energiesBatteries = secteur.getEnergieBatterieParSecteur(connection);
        energie_solaire = energiesSolaires.getNombre() * energiesSolaires.getPuissance();
        energie_batterie = energiesBatteries.getNombre() * energiesBatteries.getPuissance();
        limite_batterie = energie_batterie/2;
        energie_batterie_disponible = energie_batterie;
        System.out.println("date" + date + " Energie solaire: " + energie_solaire + " Energie batterie: " + energie_batterie);
        detailsConsos.add(new DetailsConso(date, LocalTime.of(0,0,0), 10, secteur.getId(), energie_solaire, energie_batterie, 0, 0, false, energie_solaire, energie_batterie));

        for(Luminosite lum : luminosites){
            DetailsConso detailsConso = new DetailsConso();
            if(batterie_a_sec){
                break;
            }
            if(lum.getHeure().isBefore(LocalTime.of(11,59,00)))
                consommation_totale = consommation_par_etudiant * nombre_etudiant_matin + consommation_extra;
            else
                consommation_totale = consommation_par_etudiant * nombre_etudiant_midi + consommation_extra;
            luminosite_par_heure = lum.getEtat();
            energie_solaire_disponible = energie_solaire * luminosite_par_heure / 10;
                //System.out.println("date" + date + " heure :" + lum.getHeure() + " luminosite: " + luminosite_par_heure + " secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible + " consommation totale: " + consommation_totale);
            if(energie_solaire_disponible < consommation_totale){
                double a_prendre_dans_la_batterie = consommation_totale - energie_solaire_disponible;
                //detailsConsos.add(new DetailsConso(date, lum.getHeure(), luminosite_par_heure, secteur.getId(), energie_solaire_disponible, energie_batterie_disponible, consommation_totale, a_prendre_dans_la_batterie, false, energie_solaire_disponible, energie_batterie_disponible));
                //System.out.println("depart");
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
                    System.out.println("date" + date + " heure :" + lum.getHeure() + " luminosite: " + luminosite_par_heure + " secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible + " consommation totale: " + consommation_totale + " a prendre dans la batterie" + a_prendre_dans_la_batterie);
                    System.out.println("heure exacte: " + heure_exacte);
                    detailsConsos.add(new DetailsConso(date, lum.getHeure(), luminosite_par_heure, secteur.getId(), energie_solaire_disponible, energie_batterie_disponible, consommation_totale, a_prendre_dans_la_batterie, true, energie_solaire_disponible, energie_batterie_disponible));

                }
                if(!batterie_a_sec) {
                    energie_batterie_disponible -= a_prendre_dans_la_batterie;
                    System.out.println("date" + date + " heure :" + lum.getHeure() + " luminosite: " + luminosite_par_heure + " secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible + " consommation totale: " + consommation_totale + " a prendre dans la batterie" + a_prendre_dans_la_batterie);
                    detailsConsos.add(new DetailsConso(date, lum.getHeure(), luminosite_par_heure, secteur.getId(), energie_solaire_disponible, energie_batterie_disponible, consommation_totale, a_prendre_dans_la_batterie, true, energie_solaire_disponible, energie_batterie_disponible));

                    System.out.println("makany @batterie");
                    /*System.out.println("luminosite: " + luminosite_par_heure);
                    System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                    System.out.println("consommation totale: " + consommation_totale);
                    System.out.println("-----------------------------------------------");*/
                }
            }else {
                double qu_on_n_utilise_pas_dans_le_solaire = energie_solaire_disponible - consommation_totale;
                energie_batterie_disponible += qu_on_n_utilise_pas_dans_le_solaire;
                if(energie_batterie_disponible > energie_batterie)
                    energie_batterie_disponible = energie_batterie;

                System.out.println("date" + date + " heure :" + lum.getHeure() + " luminosite: " + luminosite_par_heure + " secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible + " consommation totale: " + consommation_totale + " a prendre dans la batterie" );
                detailsConsos.add(new DetailsConso(date, lum.getHeure(), luminosite_par_heure, secteur.getId(), energie_solaire_disponible, energie_batterie_disponible, consommation_totale, 0.0, false, energie_solaire_disponible, energie_batterie_disponible));

                System.out.println("solaire mbola ampy");
                /*System.out.println("luminosite: " + luminosite_par_heure);
                System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                System.out.println("consommation totale: " + consommation_totale);
                System.out.println("-----------------------------------------------");*/
            }
        }
        //System.out.println(" ----------------------------->>>>>>> coupure dans le secteur: " + secteur.getNom() + " a " + heure_exacte);
        return detailsConsos;
    }



    /*public LocalTime prevoirCoupureTest(Date date, Secteur secteur, double consommation_par_etudiant, int nombre_etudiant_matin, int nombre_etudiant_midi) throws Exception{
        Luminosite luminosite = new Luminosite();
        luminosite.setDate(date);
        //ArrayList<Secteur> secteurs = new Secteur().getAllSecteur(null);
        ArrayList<Luminosite> luminosites = luminosite.getLuminositeByDate(null);
        if(luminosites.isEmpty()) {
            throw new Exception("Aucune luminosité trouvée pour cette date");
        }
        if(consommation_par_etudiant == 0) consommation_par_etudiant = 478.5;
        if(nombre_etudiant_matin == 0) nombre_etudiant_matin = 210;
        if(nombre_etudiant_midi == 0) nombre_etudiant_midi = 185;
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
            energie_solaire_disponible = energie_solaire * luminosite_par_heure / 10;
            if(energie_solaire_disponible < consommation_totale){
                double a_prendre_dans_la_batterie = consommation_totale - energie_solaire_disponible;
                System.out.println("heure: " + lum.getHeure());
                System.out.println("luminosite: " + luminosite_par_heure);
                System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                System.out.println("consommation totale: " + consommation_totale);
                System.out.println("a prendre dans la batterie: " + a_prendre_dans_la_batterie);
                if(a_prendre_dans_la_batterie > energie_batterie_disponible-limite_batterie){
                    double ce_qui_reste_a_prendre_dans_la_batterie = energie_batterie_disponible-limite_batterie;
                    double nombre_de_minutes_restantes_dans_la_batterie = (((ce_qui_reste_a_prendre_dans_la_batterie)*60)/consommation_totale);
                        //System.out.println("-----------------------------------------------");
                    System.out.println("ce qui reste dans la batterie: " + ce_qui_reste_a_prendre_dans_la_batterie);
                    energie_batterie_disponible = limite_batterie;
                    batterie_a_sec = true;
                    //System.out.println("batterie a sec dans le secteur: " + secteur.getNom() + " dans " + nombre_de_minutes_restantes_dans_la_batterie + " minutes");
                    double ce_qui_reste_a_prendre_dans_le_solaire = energie_solaire_disponible;
                    double nombre_de_minutes_restantes = (((ce_qui_reste_a_prendre_dans_le_solaire+ce_qui_reste_a_prendre_dans_la_batterie)*60)/consommation_totale);
                        //System.out.println("ce qui reste dans le solaire: " + ce_qui_reste_a_prendre_dans_le_solaire + " ce qui reste dans la batterie: " + ce_qui_reste_a_prendre_dans_la_batterie);
                    System.out.println("secteur a sec dans " + nombre_de_minutes_restantes + " minutes");
                    heure_exacte = lum.getHeure().plusMinutes((long) nombre_de_minutes_restantes);
                    //System.out.println("heure exacte: " + heure_exacte);
                }
                if(!batterie_a_sec) {
                    energie_batterie_disponible -= a_prendre_dans_la_batterie;
                    System.out.println("makany @batterie");
                    System.out.println("luminosite: " + luminosite_par_heure);
                    System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                    System.out.println("consommation totale: " + consommation_totale);
                    System.out.println("-----------------------------------------------");
                }
            }else {
                double qu_on_n_utilise_pas_dans_le_solaire = energie_solaire_disponible - consommation_totale;
                energie_batterie_disponible += qu_on_n_utilise_pas_dans_le_solaire;
                if(energie_batterie_disponible > energie_batterie)
                    energie_batterie_disponible = energie_batterie;
                System.out.println("heure: " + lum.getHeure());
                System.out.println("luminosite: " + luminosite_par_heure);
                System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                System.out.println("consommation totale: " + consommation_totale);
                System.out.println("solaire mbola ampy");
                System.out.println("luminosite: " + luminosite_par_heure);
                System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energie_solaire_disponible + " Energie batterie: " + energie_batterie_disponible);
                System.out.println("consommation totale: " + consommation_totale);
                System.out.println("-----------------------------------------------");
            }
            //inserer ici le code de la creation de la classe
        }
        //System.out.println(" ----------------------------->>>>>>> coupure dans le secteur: " + secteur.getNom() + " a " + heure_exacte);
        return heure_exacte;
    }*/

    public ArrayList<Secteur> moyenneParSecteur(Connection connection, Date date) throws Exception {
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }
        ArrayList<Secteur> secteurs = new ArrayList<>();
        ArrayList<Secteur> secteurs1 = new ArrayList<>();
        ArrayList<Secteur> all_secteur = new Secteur().getAllSecteur(connection);
        secteurs = devinerLaConsommationParEtudiantParLaMethodeDichotomie(connection, date);
        secteurs1 = nombreEtudiantParSecteur(connection, date);
        //System.out.println("secteurs----------------------------------------------: " + secteurs.size() + " secteurs1: " + secteurs1.size());
        ArrayList<Secteur> secteurs_moyenne = new ArrayList<>();
        for (Secteur secteur : all_secteur) {
            int id_secteur = secteur.getId();
            double consommation_moyenne = 0;
            double nombre_etudiant_moyenne_matin = 0;
            double nombre_etudiant_moyenne_midi = 0;
            int i = 0;
            int j = 0;
            for (Secteur sec : secteurs) {
                if (sec.getId() == id_secteur) {
                    consommation_moyenne += sec.getConsommation_par_etudiant();
                    i++;
                }
            }
            for (Secteur sec1 : secteurs1) {
                if (sec1.getId() == id_secteur) {
                    nombre_etudiant_moyenne_matin += sec1.getEnergieSolaire();
                    nombre_etudiant_moyenne_midi += sec1.getEnergieBatterie();
                    j++;
                }
            }
            Secteur s = new Secteur();
            s.setId(id_secteur);
            s.setNom(secteur.getNom());
            s.setConsommation_par_etudiant(consommation_moyenne / i);
            //s.setEnergieSolaire((int)Math.round(nombre_etudiant_moyenne_matin / j));
            s.setEnergieSolaire((nombre_etudiant_moyenne_matin / j));
            //s.setEnergieBatterie((int)Math.round(nombre_etudiant_moyenne_midi / j));
            s.setEnergieBatterie((nombre_etudiant_moyenne_midi / j));
            secteurs_moyenne.add(s);
            //System.out.println("i = " + i);
            //System.out.println("secteureeeeee: " + s.getNom() + " consommation moyenne: " + s.getConsommation_par_etudiant() + " nombre etudiant matin moyen: " + s.getEnergieSolaire() + " nombre etudiant midi moyen: " + s.getEnergieBatterie());
        }
        return secteurs_moyenne;
    }


    public ArrayList<Coupure> coupureParSecteur(Connection connection, Date date) throws Exception{
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }
        ArrayList<Coupure> coupures = new ArrayList<>();
        ArrayList<Secteur> secteurs = moyenneParSecteur(connection, date);
        for (Secteur secteur : secteurs) {
            //System.out.println("secteur: " + secteur.getNom() + " consommation moyenne: " + secteur.getConsommation_par_etudiant() + " nombre etudiant matin moyen: " + secteur.getEnergieSolaire() + " nombre etudiant midi moyen: " + secteur.getEnergieBatterie());
            Coupure coupure = new Coupure();
            coupure.setDate(date);
            coupure.setId_secteur(secteur.getId());
            coupure.setHeure(prevoirCoupure(connection, date, secteur, secteur.getConsommation_par_etudiant(), (int) secteur.getEnergieSolaire(), (int) secteur.getEnergieBatterie()));
            coupure.setConsommation_par_etudiant(secteur.getConsommation_par_etudiant());
            coupure.setNombre_etudiant_matin(secteur.getEnergieSolaire());
            coupure.setNombre_etudiant_midi(secteur.getEnergieBatterie());
            coupures.add(coupure);
            System.out.println("secteur: " + secteur.getNom() + " prévu coupé à: " + coupure.getHeure());
        }
        return coupures;
    }

    public static void main(String[] args) throws Exception {
        Coupure coupure = new Coupure();
        LocalTime heure = LocalTime.of(0,0,0);
        //coupure.setDate(Date.valueOf("2023-11-02"));
        Secteur secteur = new Secteur();
        secteur.setId(1);
        secteur.setNom("secteur1");
        //coupure.getCoupureByDate(null);
            heure = coupure.prevoirCoupure(null, Date.valueOf("2023-11-14"),secteur, 75,300,290);
            System.out.println(heure);
        //coupure.devinerLaConsommationParEtudiantParLaMethodeDichotomie(null, Date.valueOf("2023-12-07"));
        //coupure.devinerLaConsommationParEtudiantParLaMethodeIteration(null, Date.valueOf("2023-11-10"));

        //coupure.moyenneParSecteur(null, Date.valueOf("2023-11-02"));

        //coupure.coupureParSecteur(null, Date.valueOf("2023-12-27"));
            //coupure.nombreEtudiantParSecteur(null, Date.valueOf("2023-11-02"));

        //coupure.detailsCoupure(null, Date.valueOf("2023-10-26"), secteur, 76.647108,210,185);
    }

}
