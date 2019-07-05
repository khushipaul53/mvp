package com.example.assignment5mvp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.assignment5mvp.data.model.Student;

@Database(entities={Student.class},version=1,exportSchema=false)
public abstract class DataBaseHandler extends RoomDatabase {
    public abstract StudentDao studentdao();
}


