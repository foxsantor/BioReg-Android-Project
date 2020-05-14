package com.example.bioregproject.entities;

import android.graphics.Color;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "CategorieTache")
public class CategorieTache {
    @PrimaryKey(autoGenerate = true) private int idCat;
    @ColumnInfo(name = "name") private String name;
    private byte[] categorieImage;
    private Color couleur;

    public byte[] getCategorieImage() {
        return categorieImage;
    }

    public void setCategorieImage(byte[] categorieImage) {
        this.categorieImage = categorieImage;
    }

    @Ignore
    public CategorieTache(String name,byte[] categorieImage ,Color couleur ) {

        this.name = name;
        this.categorieImage = categorieImage;
        this.couleur = couleur;
    }


    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public CategorieTache() {super();
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  


}
