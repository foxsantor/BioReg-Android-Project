package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.ProduitDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.Produit;

import java.util.List;

public class ProduitRepository {
    private ProduitDAO ProduitDAO;
    private LiveData<List<Produit>> allProduit;
    private LiveData<List<Produit>> getProduitByCategorie;


    public ProduitRepository(Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        ProduitDAO = database.ProduitDAO();
        allProduit = ProduitDAO.getAllProduit();
    }

    public void insert(Produit Produit){

        new InsertProduitAsynTask(ProduitDAO).execute(Produit);

    }
    public void delete(Produit Produit){

        new DeleteProduitAsynTask(ProduitDAO).execute(Produit);
    }
    public void update(Produit Produit){

        new UpdateProduitAsynTask(ProduitDAO).execute(Produit);
    }
    public void deleteAllAccounts(){

        new DeleteAllProduitAsynTask(ProduitDAO).execute();
    }
    public LiveData<List<Produit>> getAllProduit(){

        return allProduit;
    }

    public LiveData<List<Produit>> getProduitByCategorie(String categorie){
        getProduitByCategorie = ProduitDAO.getProduitByCategorie(categorie);
        return getProduitByCategorie ;
    }





    private static class InsertProduitAsynTask extends AsyncTask<Produit,Void,Void>
    {
        private ProduitDAO ProduitDAO;
        private InsertProduitAsynTask(ProduitDAO ProduitDAO)
        {
            this.ProduitDAO=ProduitDAO;
        }
        @Override
        protected Void doInBackground(Produit... produits) {
            ProduitDAO.insert(produits[0]);
            return null;
        }
    }
    private static class DeleteProduitAsynTask extends AsyncTask<Produit,Void,Void>
    {
        private ProduitDAO ProduitDAO;
        private DeleteProduitAsynTask(ProduitDAO ProduitDAO)
        {
            this.ProduitDAO=ProduitDAO;
        }
        @Override
        protected Void doInBackground(Produit... produits) {
            ProduitDAO.delete(produits[0]);
            return null;
        }
    }
    private static class UpdateProduitAsynTask extends AsyncTask<Produit,Void,Void>
    {
        private ProduitDAO ProduitDAO;
        private UpdateProduitAsynTask(ProduitDAO ProduitDAO)
        {
            this.ProduitDAO=ProduitDAO;
        }
        @Override
        protected Void doInBackground(Produit... produits) {
            ProduitDAO.update(produits[0]);
            return null;
        }
    }
    private static class DeleteAllProduitAsynTask extends AsyncTask<Void,Void,Void>
    {
        private ProduitDAO ProduitDAO;
        private DeleteAllProduitAsynTask(ProduitDAO ProduitDAO)
        {
            this.ProduitDAO=ProduitDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            ProduitDAO.deleteAllProduit();
            return null;
        }
    }


}
