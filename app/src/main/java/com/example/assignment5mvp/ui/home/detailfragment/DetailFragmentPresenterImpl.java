package com.example.assignment5mvp.ui.home.detailfragment;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.constant.AppConstants;
import com.example.assignment5mvp.data.db.DataManager;
import com.example.assignment5mvp.data.model.Student;

public class DetailFragmentPresenterImpl implements DetailFragmentPresenter {

    private DetailFragmentView detailFragmentView;
    private DataManager dataManager;

    public DetailFragmentPresenterImpl(DetailFragmentView detailFragmentView, final DataManager dataManager) {
        this.detailFragmentView = detailFragmentView;
        this.dataManager = dataManager;
    }


    @Override
    public boolean validateText(String name, String rollno, String cls, String actionType) {
        if (name.length() == 0) {
//
            detailFragmentView.onFailure(AppConstants.ErrorCode.CORRECT_NAME);
            return false;
        } else if (rollno.length() == 0) {
            detailFragmentView.onFailure(AppConstants.ErrorCode.CORRECT_ROLLNO);
            return false;
        } else if (cls.length() == 0) {
            detailFragmentView.onFailure(AppConstants.ErrorCode.CORRECT_CLASS);
            return false;
        }
        return true;
    }


    @Override
    public void insertStudent(final Student student) {

        dataManager.insertStudent(student, new DataManager.CallBack() {
            @Override
            public void onSuccess(int statusCode) {
                detailFragmentView.onSuccess(statusCode);


            }

            @Override
            public void onFailure(int statusCode) {
                detailFragmentView.onFailure(statusCode);

            }
        });

    }


}
