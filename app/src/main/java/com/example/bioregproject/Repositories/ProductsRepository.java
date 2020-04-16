package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.AccountDAO;
import com.example.bioregproject.DAOs.ProductDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.ProductLogs;
import com.example.bioregproject.entities.Products;

import java.util.List;

public class ProductsRepository {
    private ProductDAO productDAO;
    private LiveData<List<Products>> allProducts;

    public ProductsRepository(Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        productDAO = database.productDAO();
        allProducts = productDAO.getAllAccounts();
    }

    public void insert(Products products){

        new InsertProductAsynTask(productDAO).execute(products);

    }
    public void delete(Products products){

        new DeleteProductAsynTask(productDAO).execute(products);
    }
    public void update(Products products){

        new UpdateProductAsynTask(productDAO).execute(products);
    }
    public void deleteAllProducts(){

        new DeleteAllProductsAsynTask(productDAO).execute();
    }
    public LiveData<List<Products>> getAllProducts(){

        return allProducts;
    }
    public LiveData<List<Products>> getProduct(long id)
    {
        return productDAO.loadProductById(id);
    }


    private static class InsertProductAsynTask extends AsyncTask<Products,Void,Void>
    {
        private ProductDAO productDAO;
        private InsertProductAsynTask(ProductDAO productDAO)
        {
            this.productDAO=productDAO;
        }
        @Override
        protected Void doInBackground(Products... products) {
            productDAO.insert(products[0]);
            return null;
        }
    }
    private static class DeleteProductAsynTask extends AsyncTask<Products,Void,Void>
    {
        private ProductDAO productDAO;
        private DeleteProductAsynTask(ProductDAO productDAO)
        {
            this.productDAO=productDAO;
        }
        @Override
        protected Void doInBackground(Products... products) {
            productDAO.delete(products[0]);
            return null;
        }
    }
    private static class UpdateProductAsynTask extends AsyncTask<Products,Void,Void>
    {
        private ProductDAO productDAO;
        private UpdateProductAsynTask(ProductDAO productDAO)
        {
            this.productDAO=productDAO;
        }
        @Override
        protected Void doInBackground(Products... products) {
            productDAO.update(products[0]);
            return null;
        }
    }
    private static class DeleteAllProductsAsynTask extends AsyncTask<Void,Void,Void>
    {
        private ProductDAO productDAO;
        private DeleteAllProductsAsynTask(ProductDAO productDAO)
        {
            this.productDAO=productDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            productDAO.deleteAllProducts();
            return null;
        }
    }


}
