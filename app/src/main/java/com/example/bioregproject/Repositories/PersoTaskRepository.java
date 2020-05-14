package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.PersoTaskDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.PersoTask;

import java.util.Date;
import java.util.List;

public class PersoTaskRepository {

    private PersoTaskDAO PersoTaskDAO;
    private LiveData<List<PersoTask>> allPersoTask;

    public PersoTaskRepository(Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        PersoTaskDAO = database.persoTaskDAO();
        allPersoTask = PersoTaskDAO.getAllPersoTasks();
    }

    public void insert(PersoTask PersoTask){

        new PersoTaskRepository.InsertPersoTaskAsynTask(PersoTaskDAO).execute(PersoTask);

    }
    public void delete(PersoTask PersoTask){

        new PersoTaskRepository.DeletePersoTaskAsynTask(PersoTaskDAO).execute(PersoTask);
    }
    public void update(PersoTask PersoTask){

        new PersoTaskRepository.UpdatePersoTaskAsynTask(PersoTaskDAO).execute(PersoTask);
    }
    public void deleteAllPersoTask(){

        new PersoTaskRepository.DeleteAllPersoTaskAsynTask(PersoTaskDAO).execute();
    }
    public LiveData<List<PersoTask>> getAllPersoTask(){

        return allPersoTask;
    }
    public LiveData<List<PersoTask>> getPersoTask(long id)
    {
        return PersoTaskDAO.loadPersoTasksOne(id);
    }

    public LiveData<List<PersoTask>> getPersoTaskByNameAndBrand(long id,Boolean state)
    {
        return PersoTaskDAO.loadPersoTaskByAssgineState(id,state);
    }

    public LiveData<List<PersoTask>> getPersoTaskAssgine(long id)
    {
        return PersoTaskDAO.loadPersoTaskByAssgine(id);
    }

    public LiveData<List<PersoTask>> getPersoTaskSpec(long id, String name)
    {
        return PersoTaskDAO.loadPersoTaskSpec(name,id);
    }

    public LiveData<List<PersoTask>> loadPersoTaskOne(long id)
    {
        return PersoTaskDAO.loadPersoTasksOne(id);
    }


    private static class InsertPersoTaskAsynTask extends AsyncTask<PersoTask,Void,Void>
    {
        private PersoTaskDAO PersoTaskDAO;
        private InsertPersoTaskAsynTask(PersoTaskDAO PersoTaskDAO)
        {
            this.PersoTaskDAO=PersoTaskDAO;
        }
        @Override
        protected Void doInBackground(PersoTask... PersoTask) {
            PersoTaskDAO.insert(PersoTask[0]);
            return null;
        }
    }
    private static class DeletePersoTaskAsynTask extends AsyncTask<PersoTask,Void,Void>
    {
        private PersoTaskDAO PersoTaskDAO;
        private DeletePersoTaskAsynTask(PersoTaskDAO PersoTaskDAO)
        {
            this.PersoTaskDAO=PersoTaskDAO;
        }
        @Override
        protected Void doInBackground(PersoTask... PersoTask) {
            PersoTaskDAO.delete(PersoTask[0]);
            return null;
        }
    }
    private static class UpdatePersoTaskAsynTask extends AsyncTask<PersoTask,Void,Void>
    {
        private PersoTaskDAO PersoTaskDAO;
        private UpdatePersoTaskAsynTask(PersoTaskDAO PersoTaskDAO)
        {
            this.PersoTaskDAO=PersoTaskDAO;
        }
        @Override
        protected Void doInBackground(PersoTask... PersoTask) {
            PersoTaskDAO.update(PersoTask[0]);
            return null;
        }
    }
    private static class DeleteAllPersoTaskAsynTask extends AsyncTask<Void,Void,Void>
    {
        private PersoTaskDAO PersoTaskDAO;
        private DeleteAllPersoTaskAsynTask(PersoTaskDAO PersoTaskDAO)
        {
            this.PersoTaskDAO=PersoTaskDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            PersoTaskDAO.deleteAllPersoTask();
            return null;
        }
    }
}
