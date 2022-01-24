package com.example.projet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerGestCaisController implements Initializable {

    @FXML
    private TableView<CaissierSearchModel> CaissierTableView ;
    @FXML
    private TableColumn<ProductSearchModel, Integer > MatriculeColumn;
    @FXML
    private TableColumn<ProductSearchModel, String > NomColumn;
    @FXML
    private TableColumn<ProductSearchModel, String  > PrenomColumn;
    @FXML
    private TableColumn<ProductSearchModel, String > CINColumn;
    @FXML
    private TableColumn<ProductSearchModel, Integer > AgeColumn;
    @FXML
    private TableColumn<ProductSearchModel, String > DateEmbchColumn;
    @FXML

    private TextField KeywordsTextField;

    ObservableList<CaissierSearchModel> CaissierSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {


        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String ProductViewQuery = "SELECT * FROM Test.Caissier;";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ProductViewQuery);

            while (queryOutput.next()){

                Integer queryMatricule = queryOutput.getInt("Matricule");
                String queryNom = queryOutput.getString("Nom");
                String queryPrenom = queryOutput.getString("Prenom");
                String queryCIN = queryOutput.getString("CIN");
                Integer queryAge = queryOutput.getInt("Age");
                String queryDateEmbch = queryOutput.getString("Date_Embch");


                //Populate the Observable list of products
                CaissierSearchModelObservableList.add(new CaissierSearchModel(queryMatricule,queryNom,queryPrenom,queryCIN,queryAge,queryDateEmbch));

            }

            // Correspending each Column in interface with column in database
            MatriculeColumn.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
            NomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            PrenomColumn.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
            CINColumn.setCellValueFactory(new PropertyValueFactory<>("CIN"));
            AgeColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
            DateEmbchColumn.setCellValueFactory(new PropertyValueFactory<>("Date_Embch"));


            // insert Values oF ProductSearchModelObservableList in the ProductTableView
            CaissierTableView.setItems(CaissierSearchModelObservableList);


            // initial filtered list
            FilteredList<CaissierSearchModel> filteredData = new FilteredList<>(CaissierSearchModelObservableList, b -> true);

            KeywordsTextField.textProperty().addListener((observable ,oldValue, newValue)-> {
                filteredData.setPredicate(productSearchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (productSearchModel.getNom().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (productSearchModel.getPrenom().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getCIN().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (productSearchModel.getMatricule().toString().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if (productSearchModel.getAge().toString().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else {
                        return false;
                    }
                });
            });

            SortedList<CaissierSearchModel> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(CaissierTableView.comparatorProperty());

            CaissierTableView.setItems(sortedData);


        }catch (SQLException e){
            Logger.getLogger(CaisGestProdController.class.getName()).log(Level.SEVERE , null , e);
            e.printStackTrace();
        }
    }



}
