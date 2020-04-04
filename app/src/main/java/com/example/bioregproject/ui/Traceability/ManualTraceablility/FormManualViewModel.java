package com.example.bioregproject.ui.Traceability.ManualTraceablility;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.Repositories.CategoryRepository;
import com.example.bioregproject.Repositories.ProductsRepository;
import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.Products;

import java.util.List;

public class FormManualViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private ProductsRepository repository;
    private CategoryRepository repositoryCat;
    private LiveData<List<Category>> allCategories;
    public FormManualViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductsRepository(application);
        repositoryCat = new CategoryRepository(application);
        allCategories = repositoryCat.getAllCategories();
    }



    public void insert(Products product)
    {
        repository.insert(product);
    }
    public void insert(Category category)
    {
        repositoryCat.insert(category);
    }
    public LiveData<List<Category>> getAllCategories()
    {
        return allCategories;
    }

}
