package com.example.bioregproject.entities.Realtions;

import androidx.room.Embedded;
import androidx.room.Relation;


import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Surface;

import java.util.List;

public class CategorywithSurfaces {

    @Embedded
    public CategorieTache categorieTache;
    @Relation(
            parentColumn = "idCat",
            entityColumn = "idCategorie"
    )

    public List<Surface> surfaces;
}
