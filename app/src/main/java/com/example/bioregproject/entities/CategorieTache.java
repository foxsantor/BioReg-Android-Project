package com.example.bioregproject.entities;

import android.graphics.Color;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "CategorieTache")
public class CategorieTache {


    @PrimaryKey(autoGenerate = true)
    private int idCat;
    @ColumnInfo(name = "name")
    private String name;
    private byte[] categorieImage;



    @Ignore
    public CategorieTache(String name, byte[] categorieImage  ) {

        this.name = name;
        this.categorieImage = categorieImage;
    }

    public byte[] getCategorieImage() {
        return categorieImage;
    }

    public void setCategorieImage(byte[] categorieImage) {
        this.categorieImage = categorieImage;
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
