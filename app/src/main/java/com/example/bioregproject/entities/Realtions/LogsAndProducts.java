package com.example.bioregproject.entities.Realtions;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.bioregproject.entities.ProductLogs;
import com.example.bioregproject.entities.Products;

public class LogsAndProducts {
    @Embedded
    public Products products;
    @Relation(
            parentColumn = "id",
            entityColumn = "product_id"
    )
    public ProductLogs productLogs;
}
