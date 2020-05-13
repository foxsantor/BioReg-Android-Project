package com.example.bioregproject.entities.Realtions;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Surface;
import com.example.bioregproject.entities.Tache;


public class TacheWithSurfaceAndCategoryTache {
    @Embedded
    public Tache tache;
    @Relation(
            parentColumn = "idCategorie",
            entityColumn = "idCat"
    )
    public CategorieTache categorieTache;
    @Relation(
            parentColumn = "idSurface",
            entityColumn = "idSurface"
    )
    public Surface surface;
}
