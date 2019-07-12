package com.example.assignment5mvp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.hardware.Camera;
import android.support.annotation.NonNull;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DataManager;
import com.example.assignment5mvp.data.model.Student;

import java.util.List;
import java.util.Objects;

public class MainViewModel extends ViewModel {
    private DataBaseHandler appDataBase;


    public LiveData<List<Student>> getCurrentData() {
        if (appDataBase == null) {
            appDataBase = MyApplication.getApplication().getDatabase();
        }
        return appDataBase.studentdao().getAllusers();
    }
}
