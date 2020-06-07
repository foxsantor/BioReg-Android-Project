package com.example.bioregproject.ui.Traceability.ImageFlow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bioregproject.Repositories.ProductsRepository;
import com.example.bioregproject.entities.Products;

import java.util.List;

public class QrinputImageFlowViewModel extends AndroidViewModel {
    private ProductsRepository repositoryPro;
    public QrinputImageFlowViewModel(@NonNull Application application) {
        super(application);
        repositoryPro = new ProductsRepository(application);
    }
    public LiveData<List<Products>> getProductByNameAndBrandAndId(String name,String brand,long id) {return repositoryPro.getProductByNameAndBrandAndId(name,brand,id);}
    public LiveData<List<Products>> getProductByNameAndBrandAndType(String name,String brand,String type) {return repositoryPro.getProductByNameAndBrandandType(name,brand,type);}

}

