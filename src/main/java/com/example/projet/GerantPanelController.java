package com.example.projet;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GerantPanelController  extends MainController implements Initializable {


    private Stage stage ;
    private Scene scene;
    private Parent root ;

    public void LogoutButtonAction (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Choose.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

    }

    public void GestCaissiersButtonAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GestCaissiers.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void GestFournisseursButtonAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GestFournisseurs.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void GestProduitsButtonAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CaisGestProduits.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void GestVentesButtonAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CaisGestVentes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

