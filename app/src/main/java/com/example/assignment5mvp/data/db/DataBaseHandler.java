package com.example.assignment5mvp.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.assignment5mvp.data.model.Student;

import java.util.List;

@Database(entities={Student.class},version=1,exportSchema=false)
public abstract class DataBaseHandler extends RoomDatabase {

    public abstract StudentDao studentdao();
}





