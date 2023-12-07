package com.thekraken9.prevision_de_coupure_electrique.model;

import java.sql.Date;
import java.time.LocalTime;

public class DetailsConso {
    private Date date;
    private LocalTime heure;
    private double etat;
    private int id_secteur;
    private double energie_solaire_initiale;
    private double energie_batterie_initiale;
    private double consommation_totale_par_heure;
    private double a_prendre_dans_la_batterie;
    private boolean batterie_consommee;
    private double energie_solaire_finale;
    private double energie_batterie_finale;

    public DetailsConso() {
    }

    public DetailsConso(Date date, LocalTime heure, double etat, int id_secteur, double energie_solaire_initiale, double energie_batterie_initiale, double consommation_totale_par_heure, double a_prendre_dans_la_batterie, boolean batterie_consommee, double energie_solaire_finale, double energie_batterie_finale) {
        this.date = date;
        this.heure = heure;
        this.etat = etat;
        this.id_secteur = id_secteur;
        this.energie_solaire_initiale = energie_solaire_initiale;
        this.energie_batterie_initiale = energie_batterie_initiale;
        this.consommation_totale_par_heure = consommation_totale_par_heure;
        this.a_prendre_dans_la_batterie = a_prendre_dans_la_batterie;
        this.batterie_consommee = batterie_consommee;
        this.energie_solaire_finale = energie_solaire_finale;
        this.energie_batterie_finale = energie_batterie_finale;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public double getEtat() {
        return etat;
    }

    public void setEtat(double etat) {
        this.etat = etat;
    }

    public int getId_secteur() {
        return id_secteur;
    }

    public void setId_secteur(int id_secteur) {
        this.id_secteur = id_secteur;
    }

    public double getEnergie_solaire_initiale() {
        return energie_solaire_initiale;
    }

    public void setEnergie_solaire_initiale(double energie_solaire_initiale) {
        this.energie_solaire_initiale = energie_solaire_initiale;
    }

    public double getEnergie_batterie_initiale() {
        return energie_batterie_initiale;
    }

    public void setEnergie_batterie_initiale(double energie_batterie_initiale) {
        this.energie_batterie_initiale = energie_batterie_initiale;
    }

    public double getConsommation_totale_par_heure() {
        return consommation_totale_par_heure;
    }

    public void setConsommation_totale_par_heure(double consommation_totale_par_heure) {
        this.consommation_totale_par_heure = consommation_totale_par_heure;
    }

    public double getA_prendre_dans_la_batterie() {
        return a_prendre_dans_la_batterie;
    }

    public void setA_prendre_dans_la_batterie(double a_prendre_dans_la_batterie) {
        this.a_prendre_dans_la_batterie = a_prendre_dans_la_batterie;
    }

    public boolean isBatterie_consommee() {
        return batterie_consommee;
    }

    public void setBatterie_consommee(boolean batterie_consommee) {
        this.batterie_consommee = batterie_consommee;
    }

    public double getEnergie_solaire_finale() {
        return energie_solaire_finale;
    }

    public void setEnergie_solaire_finale(double energie_solaire_finale) {
        this.energie_solaire_finale = energie_solaire_finale;
    }

    public double getEnergie_batterie_finale() {
        return energie_batterie_finale;
    }

    public void setEnergie_batterie_finale(double energie_batterie_finale) {
        this.energie_batterie_finale = energie_batterie_finale;
    }
}
