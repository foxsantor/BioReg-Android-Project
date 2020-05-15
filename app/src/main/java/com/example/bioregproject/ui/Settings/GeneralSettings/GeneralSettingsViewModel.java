package com.example.bioregproject.ui.Settings.GeneralSettings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.Repositories.CategorieRepository;
import com.example.bioregproject.Repositories.SurfaceRepository;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;
import com.example.bioregproject.entities.Surface;

import java.util.List;

public class GeneralSettingsViewModel extends AndroidViewModel {
    private CategorieRepository repository;
    private SurfaceRepository repositorySurface;
    private LiveData<List<CategorieTache>> allCategorie;
    private LiveData<List<CategorywithSurfaces>>allSurface;
    private LiveData<List<CategorywithSurfaces>>categories;

    public GeneralSettingsViewModel(@NonNull Application application) {
        super(application);
        repository=new CategorieRepository(application);
        allCategorie=repository.getAllCategories();
        categories=repository.getCategoriesWithSurfaces();
        repositorySurface = new SurfaceRepository(application);
        allSurface=repository.getCategoriesWithSurfaces();
    }

    public void insert(CategorieTache cat){repository.insertOne(cat);}
    public void update(CategorieTache cat){repository.update(cat);}
    public void delete(CategorieTache cat){repository.delete(cat);}
    public LiveData<List<CategorieTache>>getAllCategories(){return allCategorie;}
    public void insert(Surface surface){repositorySurface.insertOne(surface);}
    public void update(Surface surface){repositorySurface.update(surface);}
    public void delete(Surface surface){repositorySurface.delete(surface);}
    public LiveData<List<CategorywithSurfaces>>getAllSurface(){return allSurface;}
    public LiveData<List<CategorywithSurfaces>>getCategories(){return categories;}




}
