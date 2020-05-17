package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.example.bioregproject.DAOs.SurfaceDao;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.entities.Surface;

import java.util.List;

public class SurfaceRepository {
    private SurfaceDao surfaceDao;
    private LiveData<List<Surface>> allSurface ;

    public SurfaceRepository  (Application application){
        BioRegDB dataBase = BioRegDB.getInstance(application);
        surfaceDao = dataBase.surfaceDao();
        allSurface = surfaceDao.getAllSurface();
    }


    public LiveData<List<Surface>> getSurfaceById(long id){

        return  surfaceDao.loadSurfaceById(id);

    }

    public void insertOne (Surface surface){
        new SurfaceRepository.InsertSurfaceAsyncTask(surfaceDao).execute(surface);
    }

    public void delete (Surface surface ){
        new SurfaceRepository.DeleteSurfaceAsyncTask(surfaceDao).execute(surface);

    }

    public void update ( Surface surface){
        new SurfaceRepository.UpdateSurfaceAsyncTask(surfaceDao).execute(surface);

    }

    public void deleteAllSurface (){
        new SurfaceRepository.DeleteAllSurfaceAsyncTask(surfaceDao).execute();

    }

    public LiveData<List<Surface>> getAllSurface (){
        return allSurface;
    }

    private static class InsertSurfaceAsyncTask extends AsyncTask<Surface, Void , Void> {
        private SurfaceDao surfaceDao ;
        private InsertSurfaceAsyncTask(SurfaceDao surfaceDao ){
            this.surfaceDao=surfaceDao;

        }
        @Override
        protected Void doInBackground(Surface... surfaces) {
            surfaceDao.insertOne(surfaces[0]);
            return null;
        }
    }

    private static class DeleteSurfaceAsyncTask extends AsyncTask<Surface, Void , Void>{
        private SurfaceDao surfaceDao ;
        private DeleteSurfaceAsyncTask(SurfaceDao surfaceDao){
            this.surfaceDao=surfaceDao;

        }
        @Override
        protected Void doInBackground(Surface... surfaces) {
            surfaceDao.delete(surfaces[0]);
            return null;
        }
    }


    private static class UpdateSurfaceAsyncTask extends AsyncTask<Surface, Void , Void>{
        private SurfaceDao surfaceDao ;
        private UpdateSurfaceAsyncTask(SurfaceDao surfaceDao){
            this.surfaceDao=surfaceDao;

        }
        @Override
        protected Void doInBackground(Surface... surfaces) {
            surfaceDao.update(surfaces[0]);
            return null;
        }
    }


    private static class DeleteAllSurfaceAsyncTask extends AsyncTask<Void, Void , Void>{
        private SurfaceDao surfaceDao ;
        private DeleteAllSurfaceAsyncTask(SurfaceDao surfaceDao){
            this.surfaceDao=surfaceDao;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            surfaceDao.deleteAllSurface();
            return null;
        }
    }


}
