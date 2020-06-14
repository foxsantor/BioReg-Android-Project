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
    private LiveData<List<Tache>> allTaches;
    private LiveData<List<Surface>> allSurface;
    private  LiveData<List<Tache>>tacheById;
    private  LiveData<List<Tache>>tacheByStatut;
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
    public  LiveData<List<Tache>>getTacheById(String id){
        return  tacheById=repositorytache.getTacheByCateg(id); }

    public  LiveData<List<Tache>>getTacheByIdtask(long id){
        return  tacheById=repositorytache.getTacheById(id); }
    public  LiveData<List<Tache>>getTacheByStatut(Boolean statut){
        return  tacheById=repositorytache.getTacheByStatut(statut);

    }
    public LiveData<List<CategorieTache>> getCategorieById(long id) {
        return repository.getAllCategorieById(id);
    }
    public LiveData<List<CategorieTache>> getCategorieByName(String name) {
        return repository.getAllCategorieByName(name);
    }


    public LiveData<List<Surface>> getSurfaceById(long id) {
        return surfaceRepository.getSurfaceById(id);
    }



    public LiveData<List<CategorywithSurfaces>>getCategories(){return categories;}

    public LiveData<List<Tache>> getAllTache() {
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
