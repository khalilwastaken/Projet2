package com.example.projet;

import com.mysql.cj.protocol.ResultBuilder;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.Initializable;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaisPanelController extends MainController implements Initializable {

    @FXML
    private Button GestProduitsButton ;

    @FXML
    private Label FirstNameCais;
    @FXML
    private Label LastNameCais;

    private Stage stage;
    private Scene scene ;
    private Parent root ;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        getfirstandlastname();
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

    public void RetourButtonAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CaissierPanel.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void LogoutButtonAction (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Choose.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void getfirstandlastname (){

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String ProductViewQuery = "SELECT * FROM Test.cais_account ;";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ProductViewQuery);

            while (queryOutput.next()){

                String firstname = queryOutput.getString("first_name");
                String lastname = queryOutput.getString("last_name");

                FirstNameCais.setText(firstname);
                LastNameCais.setText(lastname);

            }

        }catch (Exception e){
            Logger.getLogger(CaisGestProdController.class.getName()).log(Level.SEVERE , null , e);
            e.printStackTrace();
        }
    }


}

