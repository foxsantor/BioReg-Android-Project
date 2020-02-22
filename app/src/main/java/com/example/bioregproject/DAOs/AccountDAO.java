package com.example.bioregproject.DAOs;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bioregproject.entities.Account;


import java.util.List;

@Dao
public interface AccountDAO {

    @Insert
    void insert(Account deal);

    @Delete
    void delete(Account deal);

    @Update
    void update(Account deal);

    @Query("DELETE FROM account_table")
    void deleteAllAccounts();

    @Query("SELECT * FROM account_table ORDER BY lastLoggedIn")
    LiveData<List<Account>> getAllAccounts();


}

