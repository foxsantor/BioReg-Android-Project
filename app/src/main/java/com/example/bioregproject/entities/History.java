package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "history_table")
@TypeConverters(DateConverter.class)
public class History {

    private String name;
    private String ownerFirstName;
    private String ownerLastName;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String description;
    private Date creation;
    private String categoryName;
    private String subCategoryName;
    private long subjectLinking;
    private  long ownerLinking;

    public History() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
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

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public long getSubjectLinking() {
        return subjectLinking;
    }

    public void setSubjectLinking(long subjectLinking) {
        this.subjectLinking = subjectLinking;
    }

    public long getOwnerLinking() {
        return ownerLinking;
    }

    public void setOwnerLinking(long ownerLinking) {
        this.ownerLinking = ownerLinking;
    }
}
