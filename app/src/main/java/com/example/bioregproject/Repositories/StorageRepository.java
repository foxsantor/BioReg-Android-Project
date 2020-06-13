package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.StorageDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.Storage;

import java.util.List;

public class StorageRepository {
    private StorageDAO StorageDAO;
    private LiveData<List<Storage>> allStorage;
    private LiveData<List<Storage>> getStorageByCategorie;


    public StorageRepository(Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        StorageDAO = database.StorageDAO();
        allStorage = StorageDAO.getAllStorage();
    }

    public void insert(Storage Storage){

        new InsertStorageAsynTask(StorageDAO).execute(Storage);

    }
    public void delete(Storage Storage){

        new DeleteStorageAsynTask(StorageDAO).execute(Storage);
    }
    public void update(Storage Storage){

        new UpdateStorageAsynTask(StorageDAO).execute(Storage);
    }
    public void deleteAllAccounts(){

        new DeleteAllStorageAsynTask(StorageDAO).execute();
    }
    public LiveData<List<Storage>> getAllStorage(){

        return allStorage;
    }

    public LiveData<List<Storage>> getStorageByCategorie(String categorie){
        getStorageByCategorie = StorageDAO.getStorageByCategorie(categorie);
        return getStorageByCategorie ;
    }


    public LiveData<List<Storage>> getStorageByNameProduit(String produit){
        getStorageByCategorie = StorageDAO.getStorageByNameProduit(produit);
        return getStorageByCategorie ;
    }




    private static class InsertStorageAsynTask extends AsyncTask<Storage,Void,Void>
    {
        private StorageDAO StorageDAO;
        private InsertStorageAsynTask(StorageDAO StorageDAO)
        {
            this.StorageDAO=StorageDAO;
        }
        @Override
        protected Void doInBackground(Storage... Storages) {
            StorageDAO.insert(Storages[0]);
            return null;
        }
    }
    private static class DeleteStorageAsynTask extends AsyncTask<Storage,Void,Void>
    {
        private StorageDAO StorageDAO;
        private DeleteStorageAsynTask(StorageDAO StorageDAO)
        {
            this.StorageDAO=StorageDAO;
        }
        @Override
        protected Void doInBackground(Storage... Storages) {
            StorageDAO.delete(Storages[0]);
            return null;
        }
    }
    private static class UpdateStorageAsynTask extends AsyncTask<Storage,Void,Void>
    {
        private StorageDAO StorageDAO;
        private UpdateStorageAsynTask(StorageDAO StorageDAO)
        {
            this.StorageDAO=StorageDAO;
        }
        @Override
        protected Void doInBackground(Storage... Storages) {
            StorageDAO.update(Storages[0]);
            return null;
        }
    }
    private static class DeleteAllStorageAsynTask extends AsyncTask<Void,Void,Void>
    {
        private StorageDAO StorageDAO;
        private DeleteAllStorageAsynTask(StorageDAO StorageDAO)
        {
            this.StorageDAO=StorageDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            StorageDAO.deleteAllStorage();
            return null;
        }
    }


}
