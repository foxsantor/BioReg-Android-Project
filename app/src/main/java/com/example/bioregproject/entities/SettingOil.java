package com.example.bioregproject.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bioregproject.Utils.DateConverter;

@Entity(tableName = "settingOil_table")
@TypeConverters(DateConverter.class)
public class SettingOil {


    @PrimaryKey(autoGenerate = true)
    private long id;
    private Boolean verif ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getVerif() {
        return verif;
    }

    public void setVerif(Boolean verif) {
        this.verif = verif;
    }




    public SettingOil(Boolean verif) {
        this.verif = verif;
    }

    public SettingOil() {
    }
}
