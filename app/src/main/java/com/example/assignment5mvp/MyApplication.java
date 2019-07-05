package com.example.assignment5mvp;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DataManager;
import com.facebook.stetho.Stetho;

public class MyApplication extends Application {



    private DataBaseHandler database;
    private DataManager dataManager;
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        database=Room.databaseBuilder(this, DataBaseHandler.class, "my_db")
                .build();
        Stetho.initializeWithDefaults(this);
    }

    public DataBaseHandler getDatabase() {
        return database;
    }

    public static MyApplication getApplication() {
        return application;
    }

    public DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager(this);
        }
        return dataManager;
    }
}







