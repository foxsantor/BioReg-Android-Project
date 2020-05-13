package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.FournisseurDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.Fournisseur;
import com.example.bioregproject.entities.Fournisseur;

import java.util.List;

public class FournisseurRepository {
    private FournisseurDAO FournisseurDAO;
    private LiveData<List<Fournisseur>> allFrs;

    public FournisseurRepository(Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        FournisseurDAO = database.FournisseurDAO();
        allFrs = FournisseurDAO.getAllFournisseur();
    }

    public void insert(Fournisseur Fournisseur){

        new InsertFournisseurAsynTask(FournisseurDAO).execute(Fournisseur);

    }
    public void delete(Fournisseur Fournisseur){

        new DeleteFournisseurAsynTask(FournisseurDAO).execute(Fournisseur);
    }
    public void update(Fournisseur Fournisseur){

        new UpdateFournisseurAsynTask(FournisseurDAO).execute(Fournisseur);
    }
    public void deleteAllAccounts(){

        new DeleteAllFournisseurAsynTask(FournisseurDAO).execute();
    }
    public LiveData<List<Fournisseur>> getAllFrs(){

        return allFrs;
    }



    private static class InsertFournisseurAsynTask extends AsyncTask<Fournisseur,Void,Void>
    {
        private FournisseurDAO FournisseurDAO;
        private InsertFournisseurAsynTask(FournisseurDAO FournisseurDAO)
        {
            this.FournisseurDAO=FournisseurDAO;
        }
        @Override
        protected Void doInBackground(Fournisseur... fournisseurs) {
            FournisseurDAO.insert(fournisseurs[0]);
            return null;
        }
    }
    private static class DeleteFournisseurAsynTask extends AsyncTask<Fournisseur,Void,Void>
    {
        private FournisseurDAO FournisseurDAO;
        private DeleteFournisseurAsynTask(FournisseurDAO FournisseurDAO)
        {
            this.FournisseurDAO=FournisseurDAO;
        }
        @Override
        protected Void doInBackground(Fournisseur... fournisseurs) {
            FournisseurDAO.delete(fournisseurs[0]);
            return null;
        }
    }
    private static class UpdateFournisseurAsynTask extends AsyncTask<Fournisseur,Void,Void>
    {
        private FournisseurDAO FournisseurDAO;
        private UpdateFournisseurAsynTask(FournisseurDAO FournisseurDAO)
        {
            this.FournisseurDAO=FournisseurDAO;
        }
        @Override
        protected Void doInBackground(Fournisseur... fournisseurs) {
            FournisseurDAO.update(fournisseurs[0]);
            return null;
        }
    }
    private static class DeleteAllFournisseurAsynTask extends AsyncTask<Void,Void,Void>
    {
        private FournisseurDAO FournisseurDAO;
        private DeleteAllFournisseurAsynTask(FournisseurDAO FournisseurDAO)
        {
            this.FournisseurDAO=FournisseurDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            FournisseurDAO.deleteAllFournisseur();
            return null;
        }
    }


}
