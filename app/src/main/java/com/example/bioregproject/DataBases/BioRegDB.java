package com.example.bioregproject.DataBases;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Ignore;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.bioregproject.DAOs.AccountDAO;
import com.example.bioregproject.DAOs.CategorieDao;
import com.example.bioregproject.DAOs.CategoryDAO;
import com.example.bioregproject.DAOs.FournisseurDAO;
import com.example.bioregproject.DAOs.HistoryDAO;
import com.example.bioregproject.DAOs.NotificationDAO;
import com.example.bioregproject.DAOs.OilDAO;
import com.example.bioregproject.DAOs.PersoTaskDAO;
import com.example.bioregproject.DAOs.PostDAO;
import com.example.bioregproject.DAOs.ProductDAO;
import com.example.bioregproject.DAOs.ProductLogsDAO;
import com.example.bioregproject.DAOs.ProduitDAO;
import com.example.bioregproject.DAOs.SettingOilDAO;
import com.example.bioregproject.DAOs.StorageDAO;
import com.example.bioregproject.DAOs.SurfaceDao;
import com.example.bioregproject.DAOs.TacheDao;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.Fournisseur;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.Notification;
import com.example.bioregproject.entities.Oil;
import com.example.bioregproject.entities.PersoTask;
import com.example.bioregproject.entities.Post;
import com.example.bioregproject.entities.ProductLogs;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.entities.Produit;
import com.example.bioregproject.entities.SettingOil;
import com.example.bioregproject.entities.Storage;
import com.example.bioregproject.entities.Surface;
import com.example.bioregproject.entities.Tache;

import java.util.Date;

@Database(entities = {Account.class, Category.class, ProductLogs.class, Products.class, Notification.class, History.class, PersoTask.class, Oil.class, Post.class, Tache.class, Surface.class, CategorieTache.class, Fournisseur.class, Produit.class, Storage.class, SettingOil.class},version = 26)

public abstract class BioRegDB  extends RoomDatabase {

    private static BioRegDB instance;
    public abstract AccountDAO accountDao();
    public abstract CategoryDAO categoryDAO();
    public abstract ProductDAO productDAO();
    public abstract ProductLogsDAO productLogsDAO();
    public abstract NotificationDAO notificationDAO();
    public abstract HistoryDAO historyDAO();
    public abstract PersoTaskDAO persoTaskDAO();
    public abstract OilDAO oilDAO();
    public abstract PostDAO postDAO();
    public abstract SurfaceDao surfaceDao();
    public abstract CategorieDao categorieCleanDao();
    public abstract TacheDao cleanPlanificationDao();
    public abstract FournisseurDAO FournisseurDAO();
    public abstract ProduitDAO ProduitDAO();
    public abstract StorageDAO StorageDAO();
    public abstract SettingOilDAO SettingoilDAO();



    public static synchronized BioRegDB getInstance(Context context){
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),BioRegDB.class,"account_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new PopulateDatabaseAsyncTask(instance).execute();
            super.onCreate(db);
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private CategoryDAO bookmarkDataAccessObject;

        private PopulateDatabaseAsyncTask(BioRegDB bookmarkDataAccessObject) {
            this.bookmarkDataAccessObject = bookmarkDataAccessObject.categoryDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
