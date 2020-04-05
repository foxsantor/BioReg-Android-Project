package com.example.bioregproject.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bioregproject.DAOs.NotificationDAO;
import com.example.bioregproject.DataBases.AccountDB;
import com.example.bioregproject.entities.Notification;

import java.util.List;

public class NotificationRepository {

    private NotificationDAO notificationDAO;
    private LiveData<List<Notification>> allNotifications;

    public NotificationRepository(Application application)
    {
        AccountDB database = AccountDB.getInstance(application);
        notificationDAO = database.notificationDAO();
        allNotifications = notificationDAO.getAllNotification();
    }

    public void insert(Notification Notification){

        new NotificationRepository.InsertNotificationAsynTask(notificationDAO).execute(Notification);

    }
    public void delete(Notification Notification){

        new NotificationRepository.DeleteNotificationAsynTask(notificationDAO).execute(Notification);
    }
    public void update(Notification Notification){

        new NotificationRepository.UpdateNotificationAsynTask(notificationDAO).execute(Notification);
    }
    public void deleteAllNotification(){

        new NotificationRepository.DeleteAllNotificationAsynTask(notificationDAO).execute();
    }
    public LiveData<List<Notification>> getAllNotification(){

        return allNotifications;
    }
    public LiveData<List<Notification>> getNotification(long id)
    {
        return notificationDAO.loadNotificationById(id);
    }


    private static class InsertNotificationAsynTask extends AsyncTask<Notification,Void,Void>
    {
        private NotificationDAO notificationDAO;
        private InsertNotificationAsynTask(NotificationDAO notificationDAO)
        {
            this.notificationDAO=notificationDAO;
        }
        @Override
        protected Void doInBackground(Notification... Notification) {
            notificationDAO.insert(Notification[0]);
            return null;
        }
    }
    private static class DeleteNotificationAsynTask extends AsyncTask<Notification,Void,Void>
    {
        private NotificationDAO notificationDAO;
        private DeleteNotificationAsynTask(NotificationDAO notificationDAO)
        {
            this.notificationDAO=notificationDAO;
        }
        @Override
        protected Void doInBackground(Notification... Notification) {
            notificationDAO.delete(Notification[0]);
            return null;
        }
    }
    private static class UpdateNotificationAsynTask extends AsyncTask<Notification,Void,Void>
    {
        private NotificationDAO notificationDAO;
        private UpdateNotificationAsynTask(NotificationDAO notificationDAO)
        {
            this.notificationDAO=notificationDAO;
        }
        @Override
        protected Void doInBackground(Notification... Notification) {
            notificationDAO.update(Notification[0]);
            return null;
        }
    }
    private static class DeleteAllNotificationAsynTask extends AsyncTask<Void,Void,Void>
    {
        private NotificationDAO notificationDAO;
        private DeleteAllNotificationAsynTask(NotificationDAO notificationDAO)
        {
            this.notificationDAO=notificationDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            notificationDAO.deleteAllNotifications();
            return null;
        }
    }
}
