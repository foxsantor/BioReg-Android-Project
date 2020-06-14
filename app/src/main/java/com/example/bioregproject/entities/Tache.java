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
    private Long idtask;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "statut")
    private boolean status;
    @ColumnInfo(name = "idCategorie")
    private String idCategorie;
    @ColumnInfo(name = "idSurface")
    private String idSurface;
    private String user;
private Date createdAt;
    private byte[] imageCateg;
    private Date due;
    private String ownerName;
    private Date validationDate;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Tache() {
        super();
    }

    @Ignore
    public Tache(Date date, boolean status, String idCategorie, String idSurface,String user,Date createdAt,Date due,Date validationDate,String ownerName) {
        this.date = date;
        this.status = status;
        this.idCategorie = idCategorie;
        this.idSurface = idSurface;
        this.user=user;
        this.createdAt=createdAt;
        this.ownerName=ownerName;
        this.due=due;
        this.validationDate=validationDate;
    }

    public byte[] getImageCateg() {
        return imageCateg;
    }

    public void setImageCateg(byte[] imageCateg) {
        this.imageCateg = imageCateg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Long getIdtask() {
        return idtask;
    }

    public void setIdtask(Long idtask) {
        this.idtask = idtask;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIdSurface() {
        return idSurface;
    }

    public void setIdSurface(String idSurface) {
        this.idSurface = idSurface;
    }
}

