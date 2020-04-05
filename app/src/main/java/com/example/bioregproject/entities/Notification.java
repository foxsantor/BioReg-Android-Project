package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "notification_table")
@TypeConverters(DateConverter.class)
public class Notification {

    private String name;
    private String owner;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String description;
    private Date creation;
    private String categoryName;
    private boolean isSeen;
    private String imageBase64;
    private String objectImageBase64;

    public Notification() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getObjectImageBase64() {
        return objectImageBase64;
    }

    public void setObjectImageBase64(String objectImageBase64) {
        this.objectImageBase64 = objectImageBase64;
    }
}
