create database prevision;

create table salle(
    id serial primary key,
    nom_salle varchar(100) not null,
    capacite integer not null
);
insert into salle(nom_salle,capacite) values('salle1',180);
insert into salle(nom_salle,capacite) values('salle2',150);
insert into salle(nom_salle,capacite) values('salle3',160);
insert into salle(nom_salle,capacite) values('salle4',190);
insert into salle(nom_salle,capacite) values('salle5',180);
insert into salle(nom_salle,capacite) values('salle6',350);
insert into salle(nom_salle,capacite) values('salle7',200);
insert into salle(nom_salle,capacite) values('salle8',50);

create table secteur(
    id serial primary key,
    nom varchar(100) not null
);

insert into secteur(nom) values('secteur1');
insert into secteur(nom) values('secteur2');
insert into secteur(nom) values('secteur3');
insert into secteur(nom) values('secteur4');

create table salle_par_secteur(
    id serial primary key,
    id_secteur int references secteur(id) not null,
    id_salle int references salle(id) not null
);
drop table salle_par_secteur;
select s.id_secteur,sum(nombre_personne),date_hist from historique join salle_par_secteur s on s.id_salle=historique.id_salle group by s.id_secteur, date_hist;
select * from salle_par_secteur s join secteur sec on sec.id=s.id_secteur join energie_par_secteur en on en.id_secteur=s.id_secteur;
select secteur.id as id, nom, id_salle, nom_salle,capacite from secteur join salle on secteur.id_salle=salle.id;
insert into salle_par_secteur(id_secteur,id_salle) values(1,1);
insert into salle_par_secteur(id_secteur,id_salle) values(1,2);

insert into salle_par_secteur(id_secteur,id_salle) values(2,3);
insert into salle_par_secteur(id_secteur,id_salle) values(2,4);

insert into salle_par_secteur(id_secteur,id_salle) values(3,5);
insert into salle_par_secteur(id_secteur,id_salle) values(3,6);

insert into salle_par_secteur(id_secteur,id_salle) values(4,7);
insert into salle_par_secteur(id_secteur,id_salle) values(4,8);


create table materiel(
    id serial primary key,
    nom_materiel varchar(100) not null,
    puissance double precision not null
);
insert into materiel(nom_materiel,puissance) values('panneau xl',20000);
insert into materiel(nom_materiel,puissance) values('panneau l',15000);
insert into materiel(nom_materiel,puissance) values('panneau m',10000);

insert into materiel(nom_materiel,puissance) values('batterie xl',5000);
insert into materiel(nom_materiel,puissance) values('batterie l',2500);
insert into materiel(nom_materiel,puissance) values('batterie m',1000);

insert into materiel(nom_materiel,puissance) values('led',100);
insert into materiel(nom_materiel,puissance) values('ventilateur',150);
insert into materiel(nom_materiel,puissance) values('climatiseur',200);
insert into materiel(nom_materiel,puissance) values('ordinateur',250);
insert into materiel(nom_materiel,puissance) values('imprimante',300);

create table luminosite(
    id serial primary key,
    date_lum date not null,
    heure_lum time not null,
    etat double precision not null
);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','08:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','09:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','10:00:00',7);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','11:00:00',8);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','12:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','13:00:00',10);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','14:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','15:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','16:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-05','17:00:00',4);

-------------------

insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','08:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','09:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','10:00:00',7);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','11:00:00',8);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','12:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','13:00:00',10);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','14:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','15:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','16:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-12','17:00:00',4);

------------------

insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','08:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','09:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','10:00:00',7);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','11:00:00',8);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','12:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','13:00:00',10);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','14:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','15:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','16:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-19','17:00:00',4);

-------------------

insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','08:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','09:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','10:00:00',7);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','11:00:00',8);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','12:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','13:00:00',10);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','14:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','15:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','16:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-10-26','17:00:00',4);

----------------------

insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','08:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','09:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','10:00:00',7);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','11:00:00',8);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','12:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','13:00:00',10);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','14:00:00',9);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','15:00:00',6);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','16:00:00',5);
insert into luminosite(date_lum,heure_lum,etat) values('2023-11-02','17:00:00',4);


create table energie_par_secteur(
    id serial primary key,
    id_secteur int references secteur(id) not null,
    id_materiel int references materiel(id) not null,
    nombre int not null
);
insert into energie_par_secteur(id_secteur,id_materiel,nombre) values(1,1,8);
insert into energie_par_secteur(id_secteur,id_materiel,nombre) values(1,4,10);

insert into energie_par_secteur(id_secteur,id_materiel,nombre) values(2,1,9);
insert into energie_par_secteur(id_secteur,id_materiel,nombre) values(2,4,10);

insert into energie_par_secteur(id_secteur,id_materiel,nombre) values(3,1,10);
insert into energie_par_secteur(id_secteur,id_materiel,nombre) values(3,4,12);

insert into energie_par_secteur(id_secteur,id_materiel,nombre) values(4,1,7);
insert into energie_par_secteur(id_secteur,id_materiel,nombre) values(4,4,10);

create table materiel_par_salle(
    id serial primary key,
    id_salle int references salle(id) not null,
    id_materiel int references materiel(id) not null,
    nombre_materiel int not null
);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(1,7,8);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(1,8,6);

insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(2,7,6);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(2,8,6);

insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(3,7,7);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(3,8,6);

insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(4,7,5);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(4,8,6);

insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(5,7,9);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(5,8,10);

insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(6,7,8);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(6,8,7);

insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(7,7,5);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(7,8,6);

insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(8,7,8);
insert into materiel_par_salle(id_salle,id_materiel,nombre_materiel) values(8,8,6);

create table historique(
    id serial primary key,
    date_hist date not null,
    heure_hist time not null,
    id_salle int references salle(id) not null,
    nombre_personne int not null
);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','08:00:00',1,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','14:00:00',1,90);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','08:00:00',2,110);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','14:00:00',2,95);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','08:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','14:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','08:00:00',4,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','14:00:00',4,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','08:00:00',5,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','14:00:00',5,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','08:00:00',6,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','14:00:00',6,98);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','08:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','14:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','08:00:00',8,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-05','14:00:00',8,100);

insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','08:00:00',1,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','14:00:00',1,90);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','08:00:00',2,110);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','14:00:00',2,95);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','08:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','14:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','08:00:00',4,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','14:00:00',4,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','08:00:00',5,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','14:00:00',5,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','08:00:00',6,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','14:00:00',6,98);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','08:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','14:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','08:00:00',8,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-12','14:00:00',8,100);

insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','08:00:00',1,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','14:00:00',1,90);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','08:00:00',2,110);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','14:00:00',2,95);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','08:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','14:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','08:00:00',4,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','14:00:00',4,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','08:00:00',5,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','14:00:00',5,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','08:00:00',6,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','14:00:00',6,98);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','08:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','14:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','08:00:00',8,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-19','14:00:00',8,100);

insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','08:00:00',1,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','14:00:00',1,90);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','08:00:00',2,110);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','14:00:00',2,95);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','08:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','14:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','08:00:00',4,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','14:00:00',4,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','08:00:00',5,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','14:00:00',5,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','08:00:00',6,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','14:00:00',6,98);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','08:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','14:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','08:00:00',8,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-10-26','14:00:00',8,100);

insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','08:00:00',1,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','14:00:00',1,90);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','08:00:00',2,110);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','14:00:00',2,95);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','08:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','14:00:00',3,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','08:00:00',4,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','14:00:00',4,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','08:00:00',5,105);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','14:00:00',5,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','08:00:00',6,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','14:00:00',6,98);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','08:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','14:00:00',7,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','08:00:00',8,100);
insert into historique(date_hist,heure_hist,id_salle,nombre_personne) values('2023-11-02','14:00:00',8,100);

create table coupure(
    id serial primary key,
    date_coupure date not null,
    heure_coupure time not null,
    id_secteur int references secteur(id) not null
);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-05','15:30:00',1);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-05','15:30:00',2);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-05','15:30:00',3);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-05','15:30:00',4);

insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-12','16:00:00',1);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-12','16:00:00',2);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-12','16:00:00',3);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-12','16:00:00',4);

insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-19','15:45:00',1);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-19','15:45:00',2);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-19','15:45:00',3);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-19','15:45:00',4);

insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-26','15:50:00',1);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-26','15:50:00',2);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-26','15:50:00',3);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-10-26','15:50:00',4);

insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-11-02','16:00:00',1);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-11-02','16:00:00',2);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-11-02','16:00:00',3);
insert into coupure(date_coupure,heure_coupure,id_secteur) values('2023-11-02','16:00:00',4);

create table consommation_extra(
    id serial primary key,
    id_salle int references salle(id) not null,
    id_materiel int references materiel(id) not null,
    date_cons date not null,
    heure_debut time not null,
    heure_fin time not null
);
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,7,'2023-10-05','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,8,'2023-10-05','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,7,'2023-10-05','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,8,'2023-10-05','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,7,'2023-10-05','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,8,'2023-10-05','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,7,'2023-10-05','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,8,'2023-10-05','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,7,'2023-10-05','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,8,'2023-10-05','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,7,'2023-10-05','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,8,'2023-10-05','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,7,'2023-10-05','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,8,'2023-10-05','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,7,'2023-10-05','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,8,'2023-10-05','08:00:00','09:00:00');

--------------

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,7,'2023-10-12','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,8,'2023-10-12','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,7,'2023-10-12','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,8,'2023-10-12','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,7,'2023-10-12','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,8,'2023-10-12','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,7,'2023-10-12','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,8,'2023-10-12','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,7,'2023-10-12','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,8,'2023-10-12','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,7,'2023-10-12','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,8,'2023-10-12','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,7,'2023-10-12','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,8,'2023-10-12','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,7,'2023-10-12','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,8,'2023-10-12','08:00:00','09:00:00');

--------------

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,7,'2023-10-19','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,8,'2023-10-19','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,7,'2023-10-19','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,8,'2023-10-19','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,7,'2023-10-19','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,8,'2023-10-19','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,7,'2023-10-19','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,8,'2023-10-19','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,7,'2023-10-19','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,8,'2023-10-19','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,7,'2023-10-19','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,8,'2023-10-19','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,7,'2023-10-19','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,8,'2023-10-19','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,7,'2023-10-19','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,8,'2023-10-19','08:00:00','09:00:00');

---------------

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,7,'2023-10-26','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,8,'2023-10-26','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,7,'2023-10-26','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,8,'2023-10-26','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,7,'2023-10-26','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,8,'2023-10-26','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,7,'2023-10-26','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,8,'2023-10-26','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,7,'2023-10-26','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,8,'2023-10-26','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,7,'2023-10-26','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,8,'2023-10-26','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,7,'2023-10-26','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,8,'2023-10-26','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,7,'2023-10-26','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,8,'2023-10-26','08:00:00','09:00:00');

--------------

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,7,'2023-11-02','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(1,8,'2023-11-02','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,7,'2023-11-02','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(2,8,'2023-11-02','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,7,'2023-11-02','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(3,8,'2023-11-02','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,7,'2023-11-02','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(4,8,'2023-11-02','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,7,'2023-11-02','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(5,8,'2023-11-02','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,7,'2023-11-02','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(6,8,'2023-11-02','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,7,'2023-11-02','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(7,8,'2023-11-02','08:00:00','09:00:00');

insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,7,'2023-11-02','08:00:00','09:00:00');
insert into consommation_extra(id_salle,id_materiel,date_cons,heure_debut,heure_fin) values(8,8,'2023-11-02','08:00:00','09:00:00');


select e.id as id, e.id_secteur as id_secteur, e.nombre as nombre,m.nom_materiel, m.puissance from energie_par_secteur e join materiel m on m.id=e.id_materiel;