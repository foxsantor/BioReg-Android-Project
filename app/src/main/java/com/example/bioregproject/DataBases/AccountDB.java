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
import com.example.bioregproject.entities.Account;


@Database(entities = Account.class,version = 1)

public abstract class AccountDB  extends RoomDatabase {

    private static AccountDB instance;
    public abstract AccountDAO accountDao();
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
        private AccountDAO bookmarkDataAccessObject;

        private PopulateDatabaseAsyncTask(AccountDB bookmarkDataAccessObject) {
            this.bookmarkDataAccessObject = bookmarkDataAccessObject.accountDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*bookmarkDataAccessObject.insert(new Parts("Rx7","Mazda",6));
            bookmarkDataAccessObject.insert(new Parts("Miata","Mazda",4));
            bookmarkDataAccessObject.insert(new Cars("Ford Gt","Ford",5));*/
            return null;
        }
    }
}
