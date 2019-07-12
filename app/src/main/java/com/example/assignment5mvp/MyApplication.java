package com.example.assignment5mvp;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DataManager;
import com.facebook.stetho.Stetho;

public class MyApplication extends Application {


    private static MyApplication application;
    private DataBaseHandler database;
    private DataManager dataManager;

    public static MyApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        database = Room.databaseBuilder(this, DataBaseHandler.class, "my_db")
                .build();
        Stetho.initializeWithDefaults(this);
    }

    public DataBaseHandler getDatabase() {
        return database;
    }

    public DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager(this);
        }
        return dataManager;
    }
}







