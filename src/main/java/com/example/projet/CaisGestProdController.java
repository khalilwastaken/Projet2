package com.example.projet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaisGestProdController extends MainController implements Initializable {


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
    private TextField KeywordsTextField;

    ObservableList<ProductSearchModel> ProductSearchModelObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField AjProdNomProdTextField;
    @FXML
    private TextField AjProdCategorieTextField;
    @FXML
    private TextField AjProdPrixAchatTextField;
    @FXML
    private TextField AjProdPrixVenteTextField;
    @FXML
    private TextField AjProdQuantiteTextField;
    @FXML
    private TextField AjProdFournisseurTextField;
    @FXML
    private Button AjouterProduitButton;
    @FXML
    private Label AjProdMessage;

    @FXML
    private TextField ModProdCodeProduitTextField;
    @FXML
    private TextField ModProdNomProduitTextField;
    @FXML
    private TextField ModProdCategorieTextField;
    @FXML
    private TextField ModProdPrixAchatTextField;
    @FXML
    private TextField ModProdPrixVenteTextField;
    @FXML
    private TextField ModProdQuantiteTextField;
    @FXML
    private TextField ModProdFournisseurTextField;
    @FXML
    private Button ModifierProduitButton;
    @FXML
    private Label ModifierProduitMessage;

    @FXML
    private Button SupprimerProduitButton;
    @FXML
    private TextField SupProdCodeProduitTextField;
    @FXML
    private Label SupProdMessage;

    @FXML
    private Label FirstNameCais;
    @FXML
    private Label LastNameCais;


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

        getfirstandlastname();

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

            KeywordsTextField.textProperty().addListener((observable ,oldValue, newValue)-> {
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
            Logger.getLogger(CaisGestProdController.class.getName()).log(Level.SEVERE , null , e);
            e.printStackTrace();
        }
    }

    public void AjouterProduitButtonAction (ActionEvent event){

        DataBaseConnection connectnow = new DataBaseConnection();
        Connection connectDB = connectnow.getConnection();

        String Nom_produit = AjProdNomProdTextField.getText();
        String Categorie = AjProdCategorieTextField.getText();
        Integer Prix_achat = Integer.parseInt(AjProdPrixAchatTextField.getText());
        Integer Prix_vente = Integer.parseInt(AjProdPrixVenteTextField.getText());;
        Integer Quantite = Integer.parseInt(AjProdQuantiteTextField.getText());;
        String Fournisseur = AjProdFournisseurTextField.getText();

        String insertFields = "INSERT INTO `Test`.`Produit` (`Nom_produit`, `Categorie`, `Prix_achat`, `Prix_vente`, `Quantite`, `Fournisseur`) VALUES ('";
        String insertValues = Nom_produit +"','"+ Categorie +"','"+ Prix_achat +"','"+ Prix_vente +"','"+ Quantite +"','"+ Fournisseur +"')";
        String insertIntoRegister = insertFields + insertValues;

        try {

            if (AjProdNomProdTextField.getText().isBlank() == false || AjProdCategorieTextField.getText().isBlank() == false || AjProdPrixAchatTextField.getText().isBlank() == false || AjProdPrixVenteTextField.getText().isBlank() == false || AjProdQuantiteTextField.getText().isBlank() == false || AjProdFournisseurTextField.getText().isBlank() == false  ) {

                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertIntoRegister);

                ProductTableView.refresh();
                AjProdMessage.setText("Produit Ajouté Avec Succes !");


            }else {
                AjProdMessage.setText("Veuillez Remplir Tous Les Cases");
            }

        }catch (Exception e){

            AjProdMessage.setText("Veuillez Remplir Tous Les Cases");
            e.printStackTrace();
            e.getCause();

        }

    }

    public void ModifierProduitButtonAction (ActionEvent event){

        DataBaseConnection connectnow = new DataBaseConnection();
        Connection connectDB = connectnow.getConnection();

        Integer Code_Produit = Integer.parseInt(ModProdCodeProduitTextField.getText());
        String Nom_produit = ModProdNomProduitTextField.getText();
        String Categorie = ModProdCategorieTextField.getText();
        Integer Prix_achat = Integer.parseInt(ModProdPrixAchatTextField.getText());
        Integer Prix_vente = Integer.parseInt(ModProdPrixVenteTextField.getText());
        Integer Quantite = Integer.parseInt(ModProdQuantiteTextField.getText());
        String Fournisseur = ModProdFournisseurTextField.getText();

        String ModifyFields =" UPDATE `Test`.`Produit` SET `Nom_produit` = '"+Nom_produit+"', `Categorie` = '"+Categorie+"', `Prix_achat` = '"+Prix_achat+"', `Prix_vente` = '"+Prix_vente+"', `Quantite` = '"+Quantite+"' , `Fournisseur` = '"+Fournisseur+"' WHERE (`Code_Produit` = '"+Code_Produit+"');";
        String ModifyIntoRegister = ModifyFields ;

        try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(ModifyIntoRegister);

                ModifierProduitMessage.setText("Produit Modifié Avec Succes !");
                ModifierProduitMessage.setTextFill(Color.BLUE);
                ProductTableView.refresh();

        }catch (Exception e){

            e.printStackTrace();
            e.getCause();

        }

    }

    public void SupprimerProduitButtonAction (ActionEvent event){
        DataBaseConnection connectnow = new DataBaseConnection();
        Connection connectDB = connectnow.getConnection();

        String CodeProduit = SupProdCodeProduitTextField.getText();
        String ModifyIntoRegister = "DELETE FROM `Test`.`Produit` WHERE (`Code_Produit` = '"+CodeProduit+"');" ;

        if (SupProdCodeProduitTextField.getText().isBlank() == false || SupProdCodeProduitTextField.getText().isEmpty() == false ) {
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(ModifyIntoRegister);

                SupProdMessage.setText("Produit Supprimé Avec Succes !");
                SupProdMessage.setTextFill(Color.BLUE);
                ProductTableView.refresh();

            }catch (Exception e){

                e.printStackTrace();
                e.getCause();

            }
        }else {
            SupProdMessage.setText("Entrez Le Code Du Produit !");
            SupProdMessage.setTextFill(Color.RED);
        }

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

