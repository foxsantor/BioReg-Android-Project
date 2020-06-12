package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "oil_table")
@TypeConverters(DateConverter.class)
public class Oil {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private float filtrage;
    private Date creationDate;
    private Date dateUtilisation;
    private String action;
    private String post;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }


    public float getFiltrage() {
        return filtrage;
    }

    public void setFiltrage(float filtrage) {
        this.filtrage = filtrage;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDateUtilisation() {
        return dateUtilisation;
    }

    public void setDateUtilisation(Date dateUtilisation) {
        this.dateUtilisation = dateUtilisation;
    }
    @Ignore
    public Oil(  float filtrage, Date creationDate, Date dateUtilisation,String action,String post) {
        this.filtrage = filtrage;
        this.creationDate = creationDate;
        this.dateUtilisation = dateUtilisation;
        this.action = action;
        this.post = post ;
    }

    public Oil() {
    }
}
