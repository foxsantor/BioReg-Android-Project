package com.example.bioregproject.ui.Cleaning;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.bioregproject.Repositories.CategorieRepository;
import com.example.bioregproject.Repositories.SurfaceRepository;
import com.example.bioregproject.Repositories.TacheRepository;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;
import com.example.bioregproject.entities.Realtions.TacheWithSurfaceAndCategoryTache;
import com.example.bioregproject.entities.Surface;
import com.example.bioregproject.entities.Tache;

import java.util.List;

public class ListPlanningCleanViewModel extends AndroidViewModel {

    private CategorieRepository repository;
    private TacheRepository repositorytache;
    private SurfaceRepository surfaceRepository;
    private LiveData<List<CategorieTache>> allCategorie;
    private LiveData<List<TacheWithSurfaceAndCategoryTache>> allTaches;
    private LiveData<List<Surface>> allSurface;
    private  LiveData<List<TacheWithSurfaceAndCategoryTache>>tacheById;
    private  LiveData<List<TacheWithSurfaceAndCategoryTache>>tacheByStatut;
    private LiveData<List<CategorywithSurfaces>>categories;



    public ListPlanningCleanViewModel(@NonNull Application application) {
        super(application);
        repository=new CategorieRepository(application);
        allCategorie = repository.getAllCategories();
        repositorytache = new TacheRepository(application);
        allTaches = repositorytache.getAllTache();
        surfaceRepository = new SurfaceRepository(application);
        allSurface = surfaceRepository.getAllSurface();
        categories=repository.getCategoriesWithSurfaces();


    }

    public LiveData<List<CategorieTache>> getAllCategories() {
        return allCategorie;
    }
    public  LiveData<List<TacheWithSurfaceAndCategoryTache>>getTacheById(int id){
        return  tacheById=repositorytache.getTacheByCateg(id); }
    public  LiveData<List<TacheWithSurfaceAndCategoryTache>>getTacheByStatut(Boolean statut){
        return  tacheById=repositorytache.getTacheByStatut(statut);

    }
    public LiveData<List<CategorywithSurfaces>>getCategories(){return categories;}

    public LiveData<List<TacheWithSurfaceAndCategoryTache>> getAllTache() {
        return allTaches;
    }

    public LiveData<List<Surface>> getAllSurface() {
        return allSurface;
    }

    public void insert(Tache tache) {
        repositorytache.insertOne(tache);
    }

    public void update(Tache tache) {
        repositorytache.update(tache);
    }

    public void delete(Tache tache) {
        repositorytache.delete(tache);
    }


}
