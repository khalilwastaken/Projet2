package com.example.projet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button CancelButton;
    @FXML
    private Label LoginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    public TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    // Logo
    @Override
    public void initialize (URL url , ResourceBundle ressourceBundle){
        //File brandingFile = new File("Images/Bruh.jpeg");
        //Image brandingImage = new Image(brandingFile.toString());
        //brandingImageView.setImage(brandingImage);
    }


    // Menu Choisir Caissier ou Gerant
    public void ChooseToCaissierLogin (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CaissierLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void ChooseToGerantLogin (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GerantLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Retour (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Choose.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Login Buttons Actions
    public void CaisLoginButtonAction(ActionEvent event){
        // Validate Login
        if ( usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin = "SELECT count(1) FROM Caissier WHERE Matricule_C = '" + usernameTextField.getText() + "' AND Password_C = '" + passwordTextField.getText() +"'";

            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                while(queryResult.next()){
                    if (queryResult.getInt(1) == 1) {

                        Parent root = FXMLLoader.load(getClass().getResource("CaissierPanel.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setX(60);
                        stage.setY(20);
                        scene =  new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    }else {
                        LoginMessageLabel.setText("Username Ou Password Incorrect !");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
        else {
            LoginMessageLabel.setText("Entrer Le Username et Le Password SVP");
        }
    }

    public void GerantLoginButtonAction(ActionEvent event){
        // Validate Login
        if ( usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin = "SELECT count(1) FROM gerant_account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + passwordTextField.getText() +"'";

            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                while(queryResult.next()){
                    if (queryResult.getInt(1) == 1) {

                        Parent root = FXMLLoader.load(getClass().getResource("GerantPanel.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene =  new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    }else {
                        LoginMessageLabel.setText("Username Ou Password Incorrect !");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
        else {
            LoginMessageLabel.setText("Entrer Le Username et Le Password SVP");
        }
    }

    public void CancelButtonAction (ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }


}