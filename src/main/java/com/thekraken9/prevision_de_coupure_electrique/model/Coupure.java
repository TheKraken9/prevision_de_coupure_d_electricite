package com.thekraken9.prevision_de_coupure_electrique.model;

import java.sql.Time;

public class Coupure {
    private int id;
    private int id_historique;
    private int id_secteur;
    private Time heure;

    public Coupure() {
    }

    public Coupure(int id, int id_historique, int id_secteur, Time heure) {
        this.id = id;
        this.id_historique = id_historique;
        this.id_secteur = id_secteur;
        this.heure = heure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_historique() {
        return id_historique;
    }

    public void setId_historique(int id_historique) {
        this.id_historique = id_historique;
    }

    public int getId_secteur() {
        return id_secteur;
    }

    public void setId_secteur(int id_secteur) {
        this.id_secteur = id_secteur;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }
}
