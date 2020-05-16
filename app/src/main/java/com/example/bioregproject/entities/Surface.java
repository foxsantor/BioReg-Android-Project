package com.example.bioregproject.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Surface")
public class Surface {
    @PrimaryKey(autoGenerate = true) private int idSurface;
    @ColumnInfo(name = "nameSurface") private String nameSurface;
    @ColumnInfo(name = "idCategorie") private int idCategorie;

    @Ignore
    public Surface(String nameSurface, int idCategorie) {
        this.nameSurface = nameSurface;
        this.idCategorie = idCategorie;
    }
    public Surface() {super();
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdSurface() {
        return idSurface;
    }

    public void setIdSurface(int idSurface) {
        this.idSurface = idSurface;
    }

    public String getNameSurface() {
        return nameSurface;
    }

    public void setNameSurface(String nameSurface) {
        this.nameSurface = nameSurface;
    }
}
