package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.CategorieDao;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;

import java.util.List;

public class CategorieRepository {

    private CategorieDao categorieDao;
    private LiveData<List<CategorieTache>> allCategorie;
    private LiveData<List<CategorywithSurfaces>> allcategorieSurfaces;

    public CategorieRepository  (Application application){
        BioRegDB dataBase = BioRegDB.getInstance(application);
        categorieDao = dataBase.categorieCleanDao();
        allCategorie = categorieDao.getAllCategories();
        allcategorieSurfaces = categorieDao.getCategorieWithSurfaces();
    }
    public LiveData<List<CategorieTache>> getAllCategorieById(long id){

        return  categorieDao.loadCategorieById(id);

    }
    public LiveData<List<CategorieTache>> getAllCategorieByName(String name){

        return  categorieDao.loadCategorieByName(name);

    }

    public void insertOne (CategorieTache categorie_tache){
        new InsertCategorieAsyncTask(categorieDao).execute(categorie_tache);
    }

    public void delete (CategorieTache categorie_tache){
        new DeleteCategorieAsyncTask(categorieDao).execute(categorie_tache);

    }

    public void update (CategorieTache categorie_tache){
        new UpdateCategorieAsyncTask(categorieDao).execute(categorie_tache);

    }

    public void deleteAllCategorie (){
        new DeleteAllCategorieAsyncTask(categorieDao).execute();

    }

    public LiveData<List<CategorieTache>> getAllCategories (){
        return allCategorie;
    }

    public LiveData<List<CategorywithSurfaces>> getCategoriesWithSurfaces (){
        return allcategorieSurfaces;
    }


    private static class InsertCategorieAsyncTask extends AsyncTask<CategorieTache, Void , Void>{
        private CategorieDao categorieDao;
        private InsertCategorieAsyncTask(CategorieDao categorieDao){
            this.categorieDao = categorieDao;

        }
        @Override
        protected Void doInBackground(CategorieTache... categorie_taches) {
            categorieDao.insertOne(categorie_taches[0]);
            return null;
        }
    }

    private static class DeleteCategorieAsyncTask extends AsyncTask<CategorieTache, Void , Void>{
        private CategorieDao categorieDao;
        private DeleteCategorieAsyncTask(CategorieDao categorieDao){
            this.categorieDao = categorieDao;

        }
        @Override
        protected Void doInBackground(CategorieTache... categorie_taches) {
            categorieDao.delete(categorie_taches[0]);
            return null;
        }
    }


    private static class UpdateCategorieAsyncTask extends AsyncTask<CategorieTache, Void , Void>{
        private CategorieDao categorieDao;
        private UpdateCategorieAsyncTask(CategorieDao categorieDao){
            this.categorieDao = categorieDao;

        }
        @Override
        protected Void doInBackground(CategorieTache... categorie_taches) {
            categorieDao.update(categorie_taches[0]);
            return null;
        }
    }


    private static class DeleteAllCategorieAsyncTask extends AsyncTask<Void, Void , Void>{
        private CategorieDao categorieDao;
        private DeleteAllCategorieAsyncTask(CategorieDao categorieDao){
            this.categorieDao = categorieDao;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            categorieDao.deleteAllCategorie();
            return null;
        }
    }

}
