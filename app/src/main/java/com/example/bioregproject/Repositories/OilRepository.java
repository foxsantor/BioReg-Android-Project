package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.OilDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.Oil;

import java.util.List;

public class OilRepository { 
    private OilDAO OilDAO;
    private LiveData<List<Oil>> allOils;
    private LiveData<List<Oil>> oilsByAction;
    private LiveData<List<Oil>> oilsById;



    public OilRepository (Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        OilDAO = database.oilDAO();
        allOils = OilDAO.getAllOils();

    }

    public void insert(Oil oil){

        new OilRepository.InsertOilAsynTask(OilDAO).execute(oil);

    }
    public void delete(Oil oil){

        new OilRepository.DeleteOilAsynTask(OilDAO).execute(oil);
    }
    public void update(Oil oil){

        new OilRepository.UpdateOilAsynTask(OilDAO).execute(oil);
    }
    public void deleteallOils(){

        new OilRepository.DeleteallOilsAsynTask(OilDAO).execute();
    }
    public LiveData<List<Oil>> getallOils(){

        return allOils;
    }
    public LiveData<List<Oil>> getOilsByAction(String action){

        return oilsByAction = OilDAO.loadOilByAction(action);
    }


    public LiveData<List<Oil>> getOilsById(long id){

        return oilsById = OilDAO.loadOilById(id);
    }



    private static class InsertOilAsynTask extends AsyncTask<Oil,Void,Void>
    {
        private OilDAO OilDAO;
        private InsertOilAsynTask(OilDAO OilDAO)
        {
            this.OilDAO=OilDAO;
        }
        @Override
        protected Void doInBackground(Oil... oils) {
            OilDAO.insert(oils[0]);
            return null;
        }
    }
    private static class DeleteOilAsynTask extends AsyncTask<Oil,Void,Void>
    {
        private OilDAO OilDAO;
        private DeleteOilAsynTask(OilDAO OilDAO)
        {
            this.OilDAO=OilDAO;
        }
        @Override
        protected Void doInBackground(Oil... oils) {
            OilDAO.delete(oils[0]);
            return null;
        }
    }
    private static class UpdateOilAsynTask extends AsyncTask<Oil,Void,Void>
    {
        private OilDAO OilDAO;
        private UpdateOilAsynTask(OilDAO OilDAO)
        {
            this.OilDAO=OilDAO;
        }
        @Override
        protected Void doInBackground(Oil... oils) {
            OilDAO.update(oils[0]);
            return null;
        }
    }
    private static class DeleteallOilsAsynTask extends AsyncTask<Void,Void,Void>
    {
        private OilDAO OilDAO;
        private DeleteallOilsAsynTask(OilDAO OilDAO)
        {
            this.OilDAO=OilDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            OilDAO.deleteAllOil();
            return null;
        }
    }

}
