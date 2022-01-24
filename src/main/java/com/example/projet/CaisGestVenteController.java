package com.example.projet;

import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaisGestVenteController implements Initializable {

    private Stage stage;
    private Scene scene ;
    private Parent root ;

    @FXML
    private TableView<ProductSearchModel> ProductTableView ;
    @FXML
    private TableColumn<ProductSearchModel, Integer > CodeProdColumn;
    @FXML
    private TableColumn<ProductSearchModel, String > NomProdColumn;
    @FXML
    private TableColumn<ProductSearchModel, String  > CategorieProdColumn;
    @FXML
    private TableColumn<ProductSearchModel, Integer > PrixAchatProdColumn;
    @FXML
    private TableColumn<ProductSearchModel, Integer > PrixVenteProdColumn;
    @FXML
    private TableColumn<ProductSearchModel, Integer > QuantiteProdColumn;
    @FXML
    private TableColumn<ProductSearchModel, String > FournisseurProdColumn;
    @FXML
    private TextField ProduitsKeywordsTextField;

    ObservableList<VenteSearchModel> VenteSearchModelObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<VenteSearchModel> VenteTableView ;
    @FXML
    private TableColumn<ProductSearchModel, Integer > CodeVenteColumn;
    @FXML
    private TableColumn<ProductSearchModel, String > DateVenteColumn;
    @FXML
    private TableColumn<ProductSearchModel, Integer  > PrixTotalVenteColumn;
    @FXML
    private TableColumn<ProductSearchModel, Integer > idCaissierVenteColumn;
    @FXML
    private TextField VentesKeywordsTextField;

    ObservableList<ProductSearchModel> ProductSearchModelObservableList = FXCollections.observableArrayList();

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

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String ProductViewQuery = "SELECT * FROM Test.Produit;";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ProductViewQuery);

            while (queryOutput.next()){

                Integer queryCode_Produit = queryOutput.getInt("Code_Produit");
                String queryNom_Produit = queryOutput.getString("Nom_Produit");
                String queryCategorie_Produit = queryOutput.getString("Categorie");
                Integer queryPrix_achat = queryOutput.getInt("Prix_achat");
                Integer queryPrix_vente = queryOutput.getInt("Prix_vente");
                Integer queryQuantite_Produit= queryOutput.getInt("Quantite");
                String queryFournisseur_Produit = queryOutput.getString("Fournisseur");

                //Populate the Observable list of products
                ProductSearchModelObservableList.add(new ProductSearchModel(queryCode_Produit, queryNom_Produit, queryCategorie_Produit, queryPrix_achat, queryPrix_vente, queryQuantite_Produit, queryFournisseur_Produit));

            }

            // Correspending each Column in interface with column in database
            CodeProdColumn.setCellValueFactory(new PropertyValueFactory<>("Code_Produit"));
            NomProdColumn.setCellValueFactory(new PropertyValueFactory<>("Nom_Produit"));
            CategorieProdColumn.setCellValueFactory(new PropertyValueFactory<>("Categorie"));
            PrixAchatProdColumn.setCellValueFactory(new PropertyValueFactory<>("Prix_achat"));
            PrixVenteProdColumn.setCellValueFactory(new PropertyValueFactory<>("Prix_vente"));
            QuantiteProdColumn.setCellValueFactory(new PropertyValueFactory<>("Quantite"));
            FournisseurProdColumn.setCellValueFactory(new PropertyValueFactory<>("Fournisseur"));

            // insert Values oF ProductSearchModelObservableList in the ProductTableView
            ProductTableView.setItems(ProductSearchModelObservableList);


            // initial filtered list
            FilteredList<ProductSearchModel> filteredData = new FilteredList<>(ProductSearchModelObservableList, b -> true);

            ProduitsKeywordsTextField.textProperty().addListener((observable ,oldValue, newValue)-> {
                filteredData.setPredicate(productSearchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (productSearchModel.getNom_Produit().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (productSearchModel.getCategorie().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getFournisseur().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (productSearchModel.getCode_produit().toString().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else {
                        return false;
                    }
                });
            });

            SortedList<ProductSearchModel> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(ProductTableView.comparatorProperty());

            ProductTableView.setItems(sortedData);

        }catch (SQLException e){
            Logger.getLogger(CaisGestVenteController.class.getName()).log(Level.SEVERE , null , e);
            e.printStackTrace();
        }


        String VenteViewQuery = "SELECT * FROM Test.Ventes;";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(VenteViewQuery);

            while (queryOutput.next()){

                Integer queryCode_Vente = queryOutput.getInt("Code_Vente");
                String queryDate_Vente = queryOutput.getString("Date_Vente");
                Integer queryPrix_Total = queryOutput.getInt("Prix_Total");
                Integer queryidCaissier = queryOutput.getInt("id_Caissier");

                //Populate the Observable list of products
                VenteSearchModelObservableList.add(new VenteSearchModel(queryCode_Vente,queryDate_Vente,queryPrix_Total,queryidCaissier));

            }

            // Correspending each Column in interface with column in database
            CodeVenteColumn.setCellValueFactory(new PropertyValueFactory<>("Code_Vente"));
            DateVenteColumn.setCellValueFactory(new PropertyValueFactory<>("Date_Vente"));
            PrixTotalVenteColumn.setCellValueFactory(new PropertyValueFactory<>("Prix_Total"));
            idCaissierVenteColumn.setCellValueFactory(new PropertyValueFactory<>("id_Caissier"));

            // insert Values oF ProductSearchModelObservableList in the ProductTableView
            VenteTableView.setItems(VenteSearchModelObservableList);


            // initial filtered list
            FilteredList<VenteSearchModel> filteredData = new FilteredList<>(VenteSearchModelObservableList, b -> true);

            VentesKeywordsTextField.textProperty().addListener((observable ,oldValue, newValue)-> {
                filteredData.setPredicate(productSearchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (productSearchModel.getCode_Vente().toString().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (productSearchModel.getDate_Vente().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getId_Caissier().toString().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<VenteSearchModel> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(VenteTableView.comparatorProperty());

            VenteTableView.setItems(sortedData);

        }catch (SQLException e){
            Logger.getLogger(CaisGestVenteController.class.getName()).log(Level.SEVERE , null , e);
            e.printStackTrace();
        }


    }

}


/*

 String VentesViewQuery = "SELECT * FROM Test.Ventes;";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(VentesViewQuery);

            while (queryOutput.next()){

                Integer queryCode_Vente = queryOutput.getInt("Code_Vente");
                String queryDate_Vente = queryOutput.getString("Date_Vente");
                Integer queryPrix_Total = queryOutput.getInt("Prix_Total");
                Integer queryid_Caissier = queryOutput.getInt("id_Caissier");

                //Populate the Observable list of products
                VenteSearchModelObservableList.add(new VenteSearchModel(queryCode_Vente, queryDate_Vente, queryPrix_Total, queryid_Caissier));

            }

            // Correspending each Column in interface with column in database
            CodeVenteColumn.setCellValueFactory(new PropertyValueFactory<>("Code_Vente"));
            DateVenteColumn.setCellValueFactory(new PropertyValueFactory<>("Date_Vente"));
            PrixTotalVenteColumn.setCellValueFactory(new PropertyValueFactory<>("Prix_Total"));
            idCaissierVenteColumn.setCellValueFactory(new PropertyValueFactory<>("id_Caissier"));


            // insert Values oF ProductSearchModelObservableList in the ProductTableView
            VenteTableView.setItems(VenteSearchModelObservableList);

            // initial filtered list
            FilteredList<VenteSearchModel> filteredData = new FilteredList<>(VenteSearchModelObservableList, b -> true);

            VentesKeywordsTextField.textProperty().addListener((observable ,oldValue, newValue)-> {
                filteredData.setPredicate(VenteSearchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (VenteSearchModel.getCode_Vente().toString().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (VenteSearchModel.getId_Caissier().toString().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (VenteSearchModel.getDate_Vente().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<VenteSearchModel> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(VenteTableView.comparatorProperty());

            VenteTableView.setItems(sortedData);

        }catch (SQLException e){
            Logger.getLogger(CaisGestProdController.class.getName()).log(Level.SEVERE , null , e);
            e.printStackTrace();
        }

 */


