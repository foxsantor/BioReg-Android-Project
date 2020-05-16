package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "fournisseur_table")
@TypeConverters(DateConverter.class)
public class Fournisseur {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private Date creation;
    private Date updatedAT;
    private String email;
    private String numero;
    private String adresse;
    private String categorieCommerciale;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCategorieCommerciale() {
        return categorieCommerciale;
    }

    public void setCategorieCommerciale(String categorieCommerciale) {
        this.categorieCommerciale = categorieCommerciale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Fournisseur() {
        super();
    }

    @Ignore
    public Fournisseur(String name,String email,String numero, String adresse,String categorieCommerciale,Date creation, Date updatedAT) {
        this.name = name;
        this.email = email;
        this.numero=numero;
        this.adresse=adresse;
        this.categorieCommerciale=categorieCommerciale;
        this.creation = creation;
        this.updatedAT=updatedAT;
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
