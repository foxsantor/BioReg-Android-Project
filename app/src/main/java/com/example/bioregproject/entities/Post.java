package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "post_table")
@TypeConverters(DateConverter.class)
public class Post {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private Date creation;
    private Date updatedAT;

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

    public Post() {
        super();
    }

    @Ignore
    public Post(String name, Date creation,Date updatedAT) {
        this.name = name;
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
