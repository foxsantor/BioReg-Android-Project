package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName ="productlogs_table")
@TypeConverters(DateConverter.class)
public class ProductLogs {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String description;
    private long product_id;
    private long user_id;
    private Date creation;

    public ProductLogs() {
        super();
    }

    @Ignore
    public ProductLogs(String title, String description, long product_id, long user_id, Date creation) {
        this.title = title;
        this.description = description;
        this.product_id = product_id;
        this.user_id = user_id;
        this.creation = creation;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }
}
