package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.AccountDAO;
import com.example.bioregproject.DAOs.ProductLogsDAO;
import com.example.bioregproject.DataBases.AccountDB;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.ProductLogs;
import com.example.bioregproject.entities.Products;

import java.util.List;

public class ProductLogsRepository {
    private ProductLogsDAO productLogsDAO;
    private LiveData<List<ProductLogs>> allProdductsLogs;

    public ProductLogsRepository(Application application)
    {
        AccountDB database = AccountDB.getInstance(application);
        productLogsDAO = database.productLogsDAO();
        allProdductsLogs = productLogsDAO.getAllProductLogs();
    }

    public void insert(ProductLogs productLogs){

        new InsertProductLogsAsynTask(productLogsDAO).execute(productLogs);

    }
    public void delete(ProductLogs productLogs){

        new DeleteProductLogsAsynTask(productLogsDAO).execute(productLogs);
    }
    public void update(ProductLogs productLogs){

        new UpdateProductLogsAsynTask(productLogsDAO).execute(productLogs);
    }
    public void deleteAllProducts(){

        new DeleteAllProductLogsAsynTask(productLogsDAO).execute();
    }
    public LiveData<List<ProductLogs>> getAllProdductsLogs(){

        return allProdductsLogs;
    }
    public LiveData<List<ProductLogs>> getProductLogs(long id)
    {
        return productLogsDAO.loadProductLogsById(id);
    }


    private static class InsertProductLogsAsynTask extends AsyncTask<ProductLogs,Void,Void>
    {
        private ProductLogsDAO productLogsDAO;
        private InsertProductLogsAsynTask(ProductLogsDAO productLogsDAO)
        {
            this.productLogsDAO=productLogsDAO;
        }
        @Override
        protected Void doInBackground(ProductLogs... productLogs) {
            productLogsDAO.insert(productLogs[0]);
            return null;
        }
    }
    private static class DeleteProductLogsAsynTask extends AsyncTask<ProductLogs,Void,Void>
    {
        private ProductLogsDAO productLogsDAO;
        private DeleteProductLogsAsynTask(ProductLogsDAO productLogsDAO)
        {
            this.productLogsDAO=productLogsDAO;
        }
        @Override
        protected Void doInBackground(ProductLogs... productLogs) {
            productLogsDAO.delete(productLogs[0]);
            return null;
        }
    }
    private static class UpdateProductLogsAsynTask extends AsyncTask<ProductLogs,Void,Void>
    {
        private ProductLogsDAO productLogsDAO ;
        private UpdateProductLogsAsynTask(ProductLogsDAO productLogsDAO)
        {
            this.productLogsDAO=productLogsDAO;
        }
        @Override
        protected Void doInBackground(ProductLogs... productLogs) {
            productLogsDAO.update(productLogs[0]);
            return null;
        }
    }
    private static class DeleteAllProductLogsAsynTask extends AsyncTask<Void,Void,Void>
    {
        private ProductLogsDAO productLogs;
        private DeleteAllProductLogsAsynTask(ProductLogsDAO productLogs)
        {
            this.productLogs=productLogs;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            productLogs.deleteAllProductLogs();
            return null;
        }
    }


}
