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
import com.example.bioregproject.DAOs.CategoryDAO;
import com.example.bioregproject.DAOs.NotificationDAO;
import com.example.bioregproject.DAOs.ProductDAO;
import com.example.bioregproject.DAOs.ProductLogsDAO;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.Notification;
import com.example.bioregproject.entities.ProductLogs;
import com.example.bioregproject.entities.Products;

import java.util.Date;


@Database(entities = {Account.class, Category.class, ProductLogs.class, Products.class, Notification.class},version = 5)

public abstract class AccountDB  extends RoomDatabase {

    private static AccountDB instance;
    public abstract AccountDAO accountDao();
    public abstract CategoryDAO categoryDAO();
    public abstract ProductDAO productDAO();
    public abstract ProductLogsDAO productLogsDAO();
    public abstract NotificationDAO notificationDAO();

    public static synchronized AccountDB getInstance(Context context){
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),AccountDB.class,"account_database")
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

        private PopulateDatabaseAsyncTask(AccountDB bookmarkDataAccessObject) {
            this.bookmarkDataAccessObject = bookmarkDataAccessObject.categoryDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
