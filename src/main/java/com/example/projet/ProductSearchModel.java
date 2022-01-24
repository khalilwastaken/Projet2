package com.example.projet;

public class ProductSearchModel {

    Integer Code_produit ;
    String Nom_Produit ;
    String Categorie ;
    Integer Prix_achat ;
    Integer Prix_vente ;
    Integer Quantite ;
    String Fournisseur ;

    public ProductSearchModel(Integer code_produit, String nom_Produit, String categorie, Integer prix_achat, Integer prix_vente, Integer quantite, String fournisseur) {
        Code_produit = code_produit;
        Nom_Produit = nom_Produit;
        Categorie = categorie;
        Prix_achat = prix_achat;
        Prix_vente = prix_vente;
        Quantite = quantite;
        Fournisseur = fournisseur;
    }

    public Integer getCode_produit() {
        return Code_produit;
    }

    public String getNom_Produit() {
        return Nom_Produit;
    }

    public String getCategorie() {
        return Categorie;
    }

    public Integer getPrix_achat() {
        return Prix_achat;
    }

    public Integer getPrix_vente() {
        return Prix_vente;
    }

    public Integer getQuantite() {
        return Quantite;
    }

    public String getFournisseur() {
        return Fournisseur;
    }


}
