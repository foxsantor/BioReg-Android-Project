package com.example.bioregproject.ui.Traceability.ImageFlow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.Repositories.ProductsRepository;
import com.example.bioregproject.entities.Products;

import java.util.List;

public class ImageFlowMainHallViewModel extends AndroidViewModel {
    private ProductsRepository repository;
    private LiveData<List<Products>> allProducts;



    public ImageFlowMainHallViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductsRepository(application);
        allProducts = repository.getAllProducts();
    }

    public void insert(Products product)
    {
        repository.insert(product);
    }
    public LiveData<List<Products>> getProduct(Long id) {return repository.getProduct(id);}
    public void update(Products product)
    {
        repository.update(product);
    }
    public void delete(Products product)
    {
        repository.delete(product);
    }
    public void deleteAll()
    {
        repository.deleteAllProducts();
    }
    public LiveData<List<Products>> getAllProducts()
    {
        return allProducts;
    }
    public LiveData<List<Products>> getAllProductsbyType()
    {
        return repository.getProductByType("ImageT");
    }
}
