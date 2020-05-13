package com.example.bioregproject.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "Tache")
@TypeConverters(DateConverter.class)
public class Tache {

    @PrimaryKey(autoGenerate = true)
    private int idtask;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "statut")
    private boolean status;
    @ColumnInfo(name = "idCategorie")
    private int idCategorie;
    @ColumnInfo(name = "idSurface")
    private int idSurface;

    public Tache() {
        super();
    }

    @Ignore
    public Tache(Date date, boolean status, int idCategorie, int idSurface) {
        this.date = date;
        this.status = status;
        this.idCategorie = idCategorie;
        this.idSurface = idSurface;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdtask() {
        return idtask;
    }

    public void setIdtask(int idtask) {
        this.idtask = idtask;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getIdSurface() {
        return idSurface;
    }

    public void setIdSurface(int idSurface) {
        this.idSurface = idSurface;
    }
}

