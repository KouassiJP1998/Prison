/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondeprison;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JMenu;

/**
 *
 * @author Gnebehi
 */
public class Bienvenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        /*Message afficher dans le bouton au chargement de l'application*/
        btn.setText("Bienvenu dans la Gestion de Prison de Nantes");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

             try {
                    /*instanciation de la classe database et appelle de ces differentes methodes au demarrage de l'application  */
                    Data_Base db = new Data_Base();
                    db.se_connecter();
                    db.Creation();
                    /*affichage de la trace */
                    System.out.println("Base de donnée de la Prison de Nantes crée avec succès");
                    /*Instanciation de la classe d'authentification, suivie de son affichage*/
                    Authentification a = new Authentification();
                    a.setVisible(true);
                } catch (SQLException ex) {
                    /*affichage de la trace d'existance de la base qu'on tente de creer*/
                    System.out.println("Base de donée existante");
                    Authentification a = new Authentification();
                    a.setVisible(true);
                    primaryStage.close();
                } 

            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Prison de Nantes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

        
}