package com.example.projet;

import java.util.Date;

public class VenteSearchModel {

    Integer Code_Vente;
    String Date_Vente;
    Integer Prix_Total;
    Integer id_Caissier;

    public VenteSearchModel(Integer code_Vente, String date_Vente, Integer prix_Total, Integer id_Caissier) {
        Code_Vente = code_Vente;
        Date_Vente = date_Vente;
        Prix_Total = prix_Total;
        this.id_Caissier = id_Caissier;
    }

    public Integer getCode_Vente() {
        return Code_Vente;
    }

    public String getDate_Vente() {
        return Date_Vente;
    }

    public Integer getPrix_Total() {
        return Prix_Total;
    }

    public Integer getId_Caissier() {
        return id_Caissier;
    }

    public void setCode_Vente(Integer code_Vente) {
        Code_Vente = code_Vente;
    }

    public void setDate_Vente(String date_Vente) {
        Date_Vente = date_Vente;
    }

    public void setPrix_Total(Integer prix_Total) {
        Prix_Total = prix_Total;
    }

    public void setId_Caissier(Integer id_Caissier) {
        this.id_Caissier = id_Caissier;
    }
}
