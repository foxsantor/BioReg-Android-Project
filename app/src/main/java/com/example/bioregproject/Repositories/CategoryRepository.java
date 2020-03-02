package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.example.bioregproject.DAOs.CategoryDAO;
import com.example.bioregproject.DataBases.AccountDB;
import com.example.bioregproject.entities.Category;

import java.util.List;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application)
    {
        AccountDB database = AccountDB.getInstance(application);
        categoryDAO = database.categoryDAO();
        allCategories = categoryDAO.getAllAccounts();
    }

    public void insert(Category category){

        new InsertCategoryAsynTask(categoryDAO).execute(category);

    }
    public void delete(Category category){

        new DeleteCategoryAsynTask(categoryDAO).execute(category);
    }
    public void update(Category category){

        new UpdateCategoryAsynTask(categoryDAO).execute(category);
    }
    public void deleteAllAccounts(){

        new DeleteAllCategoryAsynTask(categoryDAO).execute();
    }
    public LiveData<List<Category>> getAllCategories(){

        return allCategories;
    }
    public LiveData<List<Category>> getCategory(long id)
    {
        return categoryDAO.loadCategoryById(id);
    }


    private static class InsertCategoryAsynTask extends AsyncTask<Category,Void,Void>
    {
        private CategoryDAO categoryDAO;
        private InsertCategoryAsynTask(CategoryDAO categoryDAO)
        {
            this.categoryDAO=categoryDAO;
        }
        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.insert(categories[0]);
            return null;
        }
    }
    private static class DeleteCategoryAsynTask extends AsyncTask<Category,Void,Void>
    {
        private CategoryDAO categoryDAO;
        private DeleteCategoryAsynTask(CategoryDAO categoryDAO)
        {
            this.categoryDAO=categoryDAO;
        }
        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.delete(categories[0]);
            return null;
        }
    }
    private static class UpdateCategoryAsynTask extends AsyncTask<Category,Void,Void>
    {
        private CategoryDAO categoryDAO;
        private UpdateCategoryAsynTask(CategoryDAO categoryDAO)
        {
            this.categoryDAO=categoryDAO;
        }
        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.update(categories[0]);
            return null;
        }
    }
    private static class DeleteAllCategoryAsynTask extends AsyncTask<Void,Void,Void>
    {
        private CategoryDAO categoryDAO;
        private DeleteAllCategoryAsynTask(CategoryDAO categoryDAO)
        {
            this.categoryDAO=categoryDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            categoryDAO.deleteAllCategory();
            return null;
        }
    }


}
