package com.example.bioregproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bioregproject.Repositories.AccountRepository;
import com.example.bioregproject.Repositories.NotificationRepository;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Notification;

import java.util.List;

import io.reactivex.Flowable;

public class MainActivityViewModel extends AndroidViewModel {

    private AccountRepository repository;
    private NotificationRepository notificationRepository;
    private LiveData<List<Account>> allAccounts;
    private LiveData<List<Notification>> allNotifiactions;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountRepository(application);
        allAccounts = repository.getAllAccounts();
        notificationRepository = new NotificationRepository(application);
        allNotifiactions = notificationRepository.getAllNotification();
    }

    public void insert(Notification notification)
    {
        notificationRepository.insert(notification);
    }
    public void update(Notification notification)
    {
        notificationRepository.update(notification);
    }
    public void insert(Account account)
    {
        repository.insert(account);
    }
    public void deleteAllNotif()
    {
        notificationRepository.deleteAllNotification();
    }
    public void delete(Notification notification)
    {
        notificationRepository.delete(notification);
    }
    public LiveData<List<Account>> getAccount(Long id) {return repository.getAccount(id);}
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
    public LiveData<List<Notification>> getAllNotifications()
    {
        return allNotifiactions;
    }
}
