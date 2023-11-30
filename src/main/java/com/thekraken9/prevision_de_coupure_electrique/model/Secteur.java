package com.thekraken9.prevision_de_coupure_electrique.model;

public class Secteur {
    private int id;
    private int id_salle;

    public Secteur() {
    }

    public Secteur(int id, int id_salle) {
        this.id = id;
        this.id_salle = id_salle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }
}
