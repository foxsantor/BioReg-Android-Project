package com.example.bioregproject.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "CategorieTache")
public class CategorieTache {
    @PrimaryKey(autoGenerate = true) private int idCat;
    @ColumnInfo(name = "name") private String name;
    private boolean isSelected;
    private byte[] categorieImage;

    public byte[] getCategorieImage() {
        return categorieImage;
    }

    public void setCategorieImage(byte[] categorieImage) {
        this.categorieImage = categorieImage;
    }

    @Ignore
    public CategorieTache(String name,Boolean isSelected) {

        this.name = name;
        this.isSelected=isSelected;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategorieTache)) return false;
        CategorieTache that = (CategorieTache) o;
        return idCat == that.idCat &&
                isSelected == that.isSelected &&
                name.equals(that.name);
    }


}
