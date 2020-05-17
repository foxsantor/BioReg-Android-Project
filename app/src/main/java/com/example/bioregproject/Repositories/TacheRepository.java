package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.example.bioregproject.DAOs.TacheDao;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.Realtions.TacheWithSurfaceAndCategoryTache;
import com.example.bioregproject.entities.Tache;

import java.util.List;

public class TacheRepository {

    private TacheDao tacheDao;
    private LiveData<List<Tache>> allTache;
    private LiveData<List<Tache>> tacheByCateg;
    private LiveData<List<Tache>> tacheStatut;

    public TacheRepository (Application application){
        BioRegDB dataBase = BioRegDB.getInstance(application);
        tacheDao = dataBase.cleanPlanificationDao();
        allTache = tacheDao.getAllTache();

    }


    public void insertOne (Tache tache){
        new InsertTacheAsyncTask(tacheDao).execute(tache);
    }


    public void delete (Tache tache){

        new DeleteTacheAsyncTask(tacheDao).execute(tache);
    }

    public void update(Tache tache){

        new UpdateTacheAsyncTask(tacheDao).execute(tache);
    }

    public void deleteAllTaches(){

        new DeleteAllTacheAsyncTask(tacheDao).execute();
    }

    public LiveData<List<Tache>> getAllTache (){
        return allTache;
    }


    public LiveData<List<Tache>> getTacheByCateg (String id) {
        return tacheByCateg = tacheDao.getTacheByCategorie(id);
    }

        public LiveData<List<Tache>> getTacheByStatut (Boolean statut){
            return tacheStatut=tacheDao.getTacheByStatus(statut);
    }


    private static class InsertTacheAsyncTask extends AsyncTask<Tache, Void , Void> {
        private TacheDao tacheDao;
        private InsertTacheAsyncTask(TacheDao tacheDao){
            this.tacheDao = tacheDao;

        }
        @Override
        protected Void doInBackground(Tache... taches) {
            tacheDao.insertOne(taches[0]);
            return null;
        }
    }


    private static class DeleteTacheAsyncTask extends AsyncTask<Tache, Void , Void> {
        private TacheDao tacheDao;
        private DeleteTacheAsyncTask(TacheDao tacheDao){
            this.tacheDao = tacheDao;

        }
        @Override
        protected Void doInBackground(Tache... taches) {
            tacheDao.delete(taches[0]);
            return null;
        }
    }



    private static class UpdateTacheAsyncTask extends AsyncTask<Tache, Void , Void> {
        private TacheDao tacheDao;
        private UpdateTacheAsyncTask(TacheDao tacheDao){
            this.tacheDao = tacheDao;

        }
        @Override
        protected Void doInBackground(Tache... taches) {
            tacheDao.update(taches[0]);
            return null;
        }
    }

    private static class DeleteAllTacheAsyncTask extends AsyncTask<Void, Void , Void> {
        private TacheDao tacheDao;
        private DeleteAllTacheAsyncTask(TacheDao tacheDao){
            this.tacheDao = tacheDao;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            tacheDao.deleteAllTache();
            return null;
        }
    }


}
