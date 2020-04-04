package com.example.bioregproject.ui.Traceability.ImageFlow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.bioregproject.DAOs.AccountDAO;
import com.example.bioregproject.DAOs.ProductDAO;
import com.example.bioregproject.Repositories.ProductsRepository;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Products;

import java.util.List;

public class ManageDataViewModel extends AndroidViewModel {
    private ProductsRepository repository;
    private LiveData<List<Products>> allProducts;
    public ManageDataViewModel(@NonNull Application application) {
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

    public LiveData<PagedList<Products>> teamAllList;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();
    public ProductDAO teamDao;
    public void initAllTeams(final ProductDAO teamDao) {
        this.teamDao = teamDao;
        final PagedList.Config config = (new PagedList.Config.Builder())
                .setPageSize(10)
                .build();
        teamAllList = Transformations.switchMap(filterTextAll, new Function<String, LiveData<PagedList<Products>>>() {
            @Override
            public LiveData<PagedList<Products>> apply(String input) {
                    input="ImageT";
                    System.out.println("CURRENTINPUT: " + input);
                    return new LivePagedListBuilder<>(
                            teamDao.loadAllProductbyType(input), config)
                            .build();
            }
        });

    }
}
