package com.example.assignment5mvp.ui.home.listfragment;

import android.arch.lifecycle.LifecycleOwner;

import com.example.assignment5mvp.MainViewModel;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DataManager;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;

public class ListFragmentPresenterImpl implements ListFragmentPresenter {

    private ListFragmentView listFragmentView;
    private OnDataSaveListener onDataSaveListener;
    private DataManager dataManager;
    private MainViewModel myMainmodel = new MainViewModel();
    private LifecycleOwner owner;

    private DataBaseHandler dataBaseHandler;
    private DataManager.CallBackData callBackData;


    public ListFragmentPresenterImpl(ListFragmentView listFragmentView, DataManager dataManager) {
        this.listFragmentView = listFragmentView;
        this.dataManager = dataManager;
    }


    @Override
    public void deleteStudent(Student student) {
        dataManager.deleteStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        dataManager.updateStudent(student);


    }

    @Override
    public void getStudentData() {


        dataManager.getAllStudents();

    }


}


