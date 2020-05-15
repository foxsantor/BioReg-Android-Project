package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.SettingOilDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.SettingOil;

import java.util.List;

public class SettingOilRepository {
    private SettingOilDAO SettingOilDAO;
    private LiveData<List<SettingOil>> allSettingOils;


    public SettingOilRepository(Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        SettingOilDAO = database.SettingoilDAO();
        allSettingOils = SettingOilDAO.getAllSettingOils();

    }

    public void insert(SettingOil SettingOil){

        new SettingOilRepository.InsertSettingOilAsynTask(SettingOilDAO).execute(SettingOil);

    }
    public void delete(SettingOil SettingOil){

        new SettingOilRepository.DeleteSettingOilAsynTask(SettingOilDAO).execute(SettingOil);
    }
    public void update(SettingOil SettingOil){

        new SettingOilRepository.UpdateSettingOilAsynTask(SettingOilDAO).execute(SettingOil);
    }
    public void deleteallSettingOils(){

        new SettingOilRepository.DeleteallSettingOilsAsynTask(SettingOilDAO).execute();
    }
    public LiveData<List<SettingOil>> getallSettingOils(){

        return allSettingOils;
    }




    private static class InsertSettingOilAsynTask extends AsyncTask<SettingOil,Void,Void>
    {
        private SettingOilDAO SettingOilDAO;
        private InsertSettingOilAsynTask(SettingOilDAO SettingOilDAO)
        {
            this.SettingOilDAO=SettingOilDAO;
        }
        @Override
        protected Void doInBackground(SettingOil... SettingOils) {
            SettingOilDAO.insert(SettingOils[0]);
            return null;
        }
    }
    private static class DeleteSettingOilAsynTask extends AsyncTask<SettingOil,Void,Void>
    {
        private SettingOilDAO SettingOilDAO;
        private DeleteSettingOilAsynTask(SettingOilDAO SettingOilDAO)
        {
            this.SettingOilDAO=SettingOilDAO;
        }
        @Override
        protected Void doInBackground(SettingOil... SettingOils) {
            SettingOilDAO.delete(SettingOils[0]);
            return null;
        }
    }
    private static class UpdateSettingOilAsynTask extends AsyncTask<SettingOil,Void,Void>
    {
        private SettingOilDAO SettingOilDAO;
        private UpdateSettingOilAsynTask(SettingOilDAO SettingOilDAO)
        {
            this.SettingOilDAO=SettingOilDAO;
        }
        @Override
        protected Void doInBackground(SettingOil... SettingOils) {
            SettingOilDAO.update(SettingOils[0]);
            return null;
        }
    }
    private static class DeleteallSettingOilsAsynTask extends AsyncTask<Void,Void,Void>
    {
        private SettingOilDAO SettingOilDAO;
        private DeleteallSettingOilsAsynTask(SettingOilDAO SettingOilDAO)
        {
            this.SettingOilDAO=SettingOilDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            SettingOilDAO.deleteAllSettingOil();
            return null;
        }
    }

}
