package com.example.projet;

public class CaissierSearchModel {

    Integer Matricule ;
    String Nom ;
    String Prenom ;
    String CIN;
    Integer Age ;
    String Date_Embch;

    public CaissierSearchModel(Integer matricule, String nom, String prenom, String CIN, Integer age, String date_Embch) {
        Matricule = matricule;
        Nom = nom;
        Prenom = prenom;
        this.CIN = CIN;
        Age = age;
        Date_Embch = date_Embch;
    }

    public Integer getMatricule() {
        return Matricule;
    }

    public void setMatricule(Integer matricule) {
        Matricule = matricule;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getDate_Embch() {
        return Date_Embch;
    }

    public void setDate_Embch(String date_Embch) {
        Date_Embch = date_Embch;
    }
}
