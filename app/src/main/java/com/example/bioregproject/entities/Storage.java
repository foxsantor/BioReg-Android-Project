package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "storage_table")
@TypeConverters(DateConverter.class)
public class Storage {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String produit;
    private String categorie;
    private int quantite;
    private Date creation;
    private Date updatedAT;
    private String fournisseur;
    private float temperature;
    private Date dateReception;
    private String natureProduit;
    private Boolean status;

    public Storage(String produit, String categorie, int quantite, Date creation, Date updatedAT, String fournisseur, float temperature, Date dateReception, String natureProduit, Boolean status) {
        this.produit = produit;
        this.categorie = categorie;
        this.quantite = quantite;
        this.creation = creation;
        this.updatedAT = updatedAT;
        this.fournisseur = fournisseur;
        this.temperature = temperature;
        this.dateReception = dateReception;
        this.natureProduit = natureProduit;
        this.status = status;
    }

    public String getNatureProduit() {
        return natureProduit;
    }

    public void setNatureProduit(String natureProduit) {
        this.natureProduit = natureProduit;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Date getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(Date updatedAT) {
        this.updatedAT = updatedAT;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Storage() {
        super();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

}
