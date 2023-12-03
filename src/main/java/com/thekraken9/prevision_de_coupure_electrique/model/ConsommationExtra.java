package com.thekraken9.prevision_de_coupure_electrique.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class ConsommationExtra {
    private int id;
    private int id_salle;
    private int id_materiel;
    private Date date;
    private LocalTime heure_debut;
    private LocalTime heure_fin;

    public ConsommationExtra() {
    }

    public ConsommationExtra(int id, int id_salle, int id_materiel, Date date, LocalTime heure_debut, LocalTime heure_fin) {
        this.id = id;
        this.id_salle = id_salle;
        this.id_materiel = id_materiel;
        this.date = date;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
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

    public int getId_materiel() {
        return id_materiel;
    }

    public void setId_materiel(int id_materiel) {
        this.id_materiel = id_materiel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(LocalTime heure_debut) {
        this.heure_debut = heure_debut;
    }

    public void setHeure_fin(LocalTime heure_fin) {
        this.heure_fin = heure_fin;
    }

    public LocalTime getHeure_fin() {
        return heure_fin;
    }
}
