package com.example.assignment5mvp.ui.home.listfragment;

import com.example.assignment5mvp.constant.AppConstants;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.db.DataManager;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;

import java.util.List;

public class ListFragmentPresenterImpl implements ListFragmentPresenter {

    private ListFragmentView listFragmentView;
    private OnDataSaveListener onDataSaveListener;
    private DataManager dataManager;
    private DataBaseHandler dataBaseHandler;

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
        dataManager.getAllStudents(new DataManager.CallBackData() {
            @Override
            public void onSuccess(int statusCode, List<Student> list) {
                if (statusCode == AppConstants.ErrorCode.SUCCESS) {
                    listFragmentView.onStudentListArrived(list);
                }
            }

            @Override
            public void onFailure(int StatusCode) {
                listFragmentView.onFailure(StatusCode);
            }
        });
    }


}


