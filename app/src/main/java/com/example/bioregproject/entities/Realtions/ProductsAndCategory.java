package com.example.bioregproject.entities.Realtions;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.Products;
import java.util.List;

public class ProductsAndCategory {

        @Embedded public Category category;
        @Relation(
                parentColumn = "id",
                entityColumn = "categoryId"
        )
        public List<Products> products;
}
