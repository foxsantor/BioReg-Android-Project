package com.example.bioregproject.ui.Settings.AccountMangement;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.Repositories.AccountRepository;
import com.example.bioregproject.entities.Account;

import java.util.List;

public class AccountCreationViewModel extends AndroidViewModel {
    private AccountRepository repository;
    private LiveData<List<Account>> allAccounts;

    public AccountCreationViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountRepository(application);
        allAccounts = repository.getAllAccounts();
    }

    public void insert(Account account)
    {
        repository.insert(account);
    }
    public void update(Account account)
    {
        repository.update(account);
    }
    public void delete(Account account)
    {
        repository.delete(account);
    }
    public void deleteAll()
    {
        repository.deleteAllAccounts();
    }
    public LiveData<List<Account>> getAllAccounts()
    {
        return allAccounts;
    }
}
