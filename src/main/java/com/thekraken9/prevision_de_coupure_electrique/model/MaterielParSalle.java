package com.thekraken9.prevision_de_coupure_electrique.model;

public class MaterielParSalle {
    private int id;
    private int id_salle;
    private int id_materiel;
    private int nombre;

    public MaterielParSalle() {
    }

    public MaterielParSalle(int id, int id_salle, int id_materiel, int nombre) {
        this.id = id;
        this.id_salle = id_salle;
        this.id_materiel = id_materiel;
        this.nombre = nombre;
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

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
