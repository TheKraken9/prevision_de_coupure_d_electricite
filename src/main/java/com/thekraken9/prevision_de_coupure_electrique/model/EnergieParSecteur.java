package com.thekraken9.prevision_de_coupure_electrique.model;

public class EnergieParSecteur {
    private int id;
    private int id_secteur;
    private int id_materiel;
    private int nombre;

    public EnergieParSecteur() {
    }

    public EnergieParSecteur(int id, int id_secteur, int id_materiel, int nombre) {
        this.id = id;
        this.id_secteur = id_secteur;
        this.id_materiel = id_materiel;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_secteur() {
        return id_secteur;
    }

    public void setId_secteur(int id_secteur) {
        this.id_secteur = id_secteur;
    }

    public int getId_materiel() {
        return id_materiel;
    }

    public void setId_materiel(int id_materiel) {
        this.id_materiel = id_materiel;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
