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
    private float filtrageValeur;

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

    public float getFiltrageValeur() {
        return filtrageValeur;
    }

    public void setFiltrageValeur(float filtrageValeur) {
        this.filtrageValeur = filtrageValeur;
    }


    public SettingOil(Boolean verif, float filtrageValeur) {
        this.verif = verif;
        this.filtrageValeur = filtrageValeur;
    }

    public SettingOil() {
    }
}
