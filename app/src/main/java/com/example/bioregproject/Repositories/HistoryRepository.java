package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.HistoryDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.History;

import java.util.List;

public class HistoryRepository {

    private HistoryDAO HistoryDAO;
    private LiveData<List<History>> allHistorys;

    public HistoryRepository(Application application)
    {
        BioRegDB database = BioRegDB.getInstance(application);
        HistoryDAO = database.historyDAO();
        allHistorys = HistoryDAO.getAllHistory();
    }

    public void insert(History History){

        new HistoryRepository.InsertHistoryAsynTask(HistoryDAO).execute(History);

    }
    public void delete(History History){

        new HistoryRepository.DeleteHistoryAsynTask(HistoryDAO).execute(History);
    }
    public void update(History History){

        new HistoryRepository.UpdateHistoryAsynTask(HistoryDAO).execute(History);
    }
    public void deleteAllHistory(){

        new HistoryRepository.DeleteAllHistoryAsynTask(HistoryDAO).execute();
    }
    public LiveData<List<History>> getAllHistory(){

        return allHistorys;
    }
    public LiveData<List<History>> getHistory(long id)
    {
        return HistoryDAO.loadHistoryById(id);
    }


    private static class InsertHistoryAsynTask extends AsyncTask<History,Void,Void>
    {
        private HistoryDAO HistoryDAO;
        private InsertHistoryAsynTask(HistoryDAO HistoryDAO)
        {
            this.HistoryDAO=HistoryDAO;
        }
        @Override
        protected Void doInBackground(History... History) {
            HistoryDAO.insert(History[0]);
            return null;
        }
    }
    private static class DeleteHistoryAsynTask extends AsyncTask<History,Void,Void>
    {
        private HistoryDAO HistoryDAO;
        private DeleteHistoryAsynTask(HistoryDAO HistoryDAO)
        {
            this.HistoryDAO=HistoryDAO;
        }
        @Override
        protected Void doInBackground(History... History) {
            HistoryDAO.delete(History[0]);
            return null;
        }
    }
    private static class UpdateHistoryAsynTask extends AsyncTask<History,Void,Void>
    {
        private HistoryDAO HistoryDAO;
        private UpdateHistoryAsynTask(HistoryDAO HistoryDAO)
        {
            this.HistoryDAO=HistoryDAO;
        }
        @Override
        protected Void doInBackground(History... History) {
            HistoryDAO.update(History[0]);
            return null;
        }
    }
    private static class DeleteAllHistoryAsynTask extends AsyncTask<Void,Void,Void>
    {
        private HistoryDAO HistoryDAO;
        private DeleteAllHistoryAsynTask(HistoryDAO HistoryDAO)
        {
            this.HistoryDAO=HistoryDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            HistoryDAO.deleteAllHistorys();
            return null;
        }
    }
}
