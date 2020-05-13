package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "produit_table")
@TypeConverters(DateConverter.class)
public class Produit {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String categorie;
    private int quantite;
    private Date creation;
    private Date updatedAT;
    private String nature;

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
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

    public Produit() {
        super();
    }

    @Ignore
    public Produit(String name, String categorie ,Date creation, Date updatedAT,int quantite,String nature) {
        this.name = name;
        this.categorie=categorie;
        this.creation = creation;
        this.updatedAT=updatedAT;
        this.quantite=quantite;
        this.nature=nature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
