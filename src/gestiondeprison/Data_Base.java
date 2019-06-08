/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondeprison;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gnebehi
 */
public class Data_Base {

    /*Methode pour etablir la connexion*/
    public Connection se_connecter() {
        /* execution au moin une fois de ce bloc de try-catch au demarrage de l'application*/
        try {
            /* Chargement de l'intergiciel qui sert d'intermediaire entre, 
            l'application et la base de donnée */
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            /* Affichage de la trace du chargement de l'intergiciel*/
            System.out.print("intergiciel chargée avec succès !!! \n");
            /* le lien de creation de la base de donnée*/
            String dbURL1 = "jdbc:derby:gestionprison;create=true ";
            /*Recuperation de la connexion a la base de donnée crée*/
            Connection con = DriverManager.getConnection(dbURL1);
            /*Affichage de la trace de connexion a la base de donnée*/
            if (con != null) {

                System.out.println("1-Connexion via jdbc a la base de donnée etablie");
                return con;

            }
        } catch (SQLException ex) {
            /*Affichage de la trace de l'echec de connexion a la base de donnée*/
            System.out.println("Impossible de se connecter a la Base");
        }

        return null;
    }

    /*procedure de creation des tables dans la base de donnée*/
    public void Creation() throws SQLException {
        /* recuperation de la connexion a la base de donnée avant la creation des differentes tables dans cette base*/
        Connection con = DriverManager.getConnection("jdbc:derby:gestionprison ");
        Statement stmt;
        stmt = con.createStatement();
        /*creation de la table utilisateur dans la base*/
        stmt.execute("create table utilisateur (login varchar(20), password varchar(10))");
        /*affichage de la trace d'execution de la creation de la table utilisateur*/
        System.out.println(" 2-creation de table utilisateur reussii !!!");
        /*insertion des données dans la table utilisateur dans la base*/
        stmt.execute("insert into utilisateur values ('barbier','barbier')");
           
               
        /*affichage de la trace d'execution de l'insertion dans table utilisateur*/
        System.out.println("3-insertion de table utilisateur reussii !!!");
        /*creation de la table Detenu dans la base*/
        stmt.execute("create table Detenu(\n"
                    + "n_ecrou varchar(10),\n"
                    + "prenom varchar(30),\n"
                    + "nom varchar(30),\n"
                    + "date_naissance Date,\n"
                    + "lieu_naissance varchar(30),\n"
                    + "constraint Detenu_key primary key(n_ecrou))");
            stmt.execute("create table Affaire(\n"
                    + "n_affaire varchar(10),\n"
                    + "nom_juridiction varchar(30),\n"
                    + "date_faits Date,\n"
                    + "constraint Affaire_key primary key(n_affaire,nom_juridiction))");
            stmt.execute("create table Detenu_Affaire(\n"
                    + "n_ecrou varchar(10),\n"
                    + "n_affaire varchar(10),\n"
                    + "nom_juridiction varchar(30),\n"
                    + "constraint Detenu_Affaire_key primary key(n_ecrou,n_affaire,nom_juridiction),\n"
                    + "constraint Detenu_Affaire_foreign_key foreign key(n_ecrou) references Detenu(n_ecrou),\n"
                    + "constraint Detenu_Affaire_foreign_key2 foreign key(n_affaire,nom_juridiction) references Affaire(n_affaire,nom_juridiction))");
            stmt.execute("create table Motif(\n"
                    + "n_motif varchar(10),\n"
                    + "libelle_motif varchar(50) not null,\n"
                    + "constraint Motif_key primary key(n_motif),\n"
                    + "constraint Motif_unique unique(libelle_motif))");
            stmt.execute("create table Incarceration(\n"
                    + "n_ecrou varchar(10),\n"
                    + "n_affaire varchar(10) not null,\n"
                    + "nom_juridiction varchar(30) not null,\n"
                    + "date_incarceration Date,\n"
                    + "n_motif varchar(10) not null,\n"
                    + "constraint Incarceration_key primary key(n_ecrou),\n"
                    + "constraint Incarceration_foreign_key foreign key(n_ecrou,n_affaire,nom_juridiction) references Detenu_Affaire(n_ecrou,n_affaire,nom_juridiction),\n"
                    + "constraint Incarceration_foreign_key2 foreign key(n_motif) references Motif(n_motif))");
            stmt.execute("create table Condamnation(\n"
                    + "n_type_decision varchar(1),\n"
                    + "n_ecrou varchar(10),\n"
                    + "date_decision Date,\n"
                    + "duree Integer,\n"
                    + "constraint Condamnation_foreign_key foreign key(n_ecrou) references Detenu(n_ecrou),\n"
                    + "constraint Condamnation_key primary key(n_type_decision,n_ecrou,date_decision)");
            stmt.execute("create table Reduction_peine(\n"
                    + "n_type_decision varchar(1),\n"
                    + "n_ecrou varchar(10),\n"
                    + "date_decision Date,\n"
                    + "duree Integer,\n"
                    + "constraint Reduction_peine_foreign_key foreign key(n_ecrou) references Detenu(n_ecrou),\n"
                    + "constraint Reduction_peine_key primary key(n_type_decision,n_ecrou,date_decision)");
            stmt.execute("create table Liberation(\n"
                    + "n_type_decision varchar(1),\n"
                    + "n_ecrou varchar(10),\n"
                    + "date_decision Date,\n"
                    + "date_liberation Date,\n"
                    + "constraint Liberation_foreign_key foreign key(n_ecrou) references Detenu(n_ecrou),\n"
                    + "constraint Liberation_key primary key(n_type_decision,n_ecrou,date_decision)");            
    }

}
