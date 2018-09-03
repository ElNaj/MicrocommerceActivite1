package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
//@JsonFilter("monFiltreDynamique")
public class Product {

    @Id
    @GeneratedValue
    private int id;

    @Length(min=3, max=20, message = "Nom trop long ou trop court. Et Ouep c'est plus stylé comme sagemé !!")
    private String nom;

    //@Min(value = 0)
    private int prix;

    //information qu'on ne souhaite pas afficher
    private int prixAchat;

    public Product() {
    }

    public Product(int id, String nom, int prix, int prixAchat) {

        this.id=id;
        this.nom=nom;
        this.prix=prix;
        this.prixAchat=prixAchat;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getPrix() {
        return prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix + '}';
    }
}
