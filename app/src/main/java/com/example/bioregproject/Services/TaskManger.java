package com.example.bioregproject.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.bioregproject.Utils.StaticUse;

public class TaskManger extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            return START_STICKY;
        }

        @Nullable
        @Override
        public IBinder
        onBind(Intent intent) {
            return null;
        }

        @Override
        public void onTaskRemoved(Intent rootIntent) {
            StaticUse.clearShared(StaticUse.SHARED_NAME_USER_LOG,getApplicationContext());
            Log.d(getClass().getName(), "App just got removed from Recents!");
        }

}
