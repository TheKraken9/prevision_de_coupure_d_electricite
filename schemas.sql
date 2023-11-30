create database prevision;

create table salle(
    id serial primary key,
    nom_salle varchar(100) not null,
    capacite integer not null
);

create table secteur(
    id serial primary key,
    id_salle int references salle(id) not null
);

create table materiel(
    id serial primary key,
    nom_materiel varchar(100) not null,
    puissance double precision not null
);

create table luminosite(
    id serial primary key,
    date_lum date not null,
    heure_lum time not null,
    etat smallint not null
);

create table energie_par_secteur(
    id serial primary key,
    id_secteur int references secteur(id) not null,
    id_materiel int references materiel(id) not null,
    nombre int not null
);

create table materiel_par_salle(
    id serial primary key,
    id_salle int references salle(id) not null,
    id_materiel int references materiel(id) not null,
    nombre_materiel int not null
);

create table historique(
    id serial primary key,
    date_hist date not null,
    heure_hist time not null,
    id_salle int references salle(id) not null,
    nombre_personne int not null
);

create table coupure(
    id serial primary key,
    id_hist int references historique(id) not null,
    heure_coupure time not null,
    id_secteur int references secteur(id) not null
);

create table consommation_extra(
    id serial primary key,
    id_salle int references salle(id) not null,
    id_materiel int references materiel(id) not null,
    date_cons date not null,
    heure_debut time not null,
    heure_fin time not null
);

