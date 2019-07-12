package com.example.assignment5mvp.data.db;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.assignment5mvp.MainViewModel;
import com.example.assignment5mvp.MyApplication;
import com.example.assignment5mvp.constant.AppConstants;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.AppExecutor;
import com.example.assignment5mvp.data.model.Student;

import java.util.List;

import static com.example.assignment5mvp.MyApplication.*;

public class DataManager {
    private CallBack callBack;


    private DataBaseHandler dataBaseHandler;
  private MainViewModel myMainmodel=new MainViewModel();
    public DataManager(MyApplication myApplication) {
        this.dataBaseHandler = myApplication.getDatabase();
    }

    public void insertStudent(final Student student, final CallBack callBack) {
        AppExecutor.getExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long isExist = dataBaseHandler.studentdao().checkUserExist(student.rollno);
                if (isExist != 1) {
                    long row = dataBaseHandler.studentdao().insertAll(student);
                    if (callBack != null) {
                        if (row > 0) {
                            callBack.onSuccess(AppConstants.ErrorCode.USER_INSERTED);
                        } else {
                            callBack.onFailure(AppConstants.ErrorCode.UNKNOWN_ERROR);
                        }
                    }
                } else {
                    if (callBack != null) {
                        callBack.onFailure(AppConstants.ErrorCode.ALREADY_EXIST);
                    }
                }

            }
        });
    }

    public void deleteStudent(final Student student) {
        AppExecutor.getExecutors().diskIO().execute(new Runnable() {


            @Override
            public void run() {
                dataBaseHandler.studentdao().DeleteAll(student);
            }
        });

    }

    public void updateStudent(final Student student) {
        AppExecutor.getExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dataBaseHandler.studentdao().update(student);

            }
        });
    }





    public LiveData<List<Student>>getAllStudents() {


        return myMainmodel.getCurrentData();
//        dataBaseHandler.studentdao().getAllusers().observeForever(new Observer<List<Student>>() {
//            @Override
//            public void onChanged(@Nullable List<Student>list) {
//
//                if(list == null){
//                    callBack.onFailure(AppConstants.ErrorCode.UNKNOWN_ERROR);
//
//                }else{
//                    callBack.onSuccess(AppConstants.ErrorCode.SUCCESS);
//
//
//                }
//            }
//        });

//        lists.addSource(students,new Observer<List<Student>>() {
//            @Override
//            public void onChanged(@Nullable List<Student> list) {
//                if (list == null || list.isEmpty()) {
//                    callBack.onFailure(AppConstants.ErrorCode.UNKNOWN_ERROR);
//
//
//           } else {
//                    callBack.onSuccess(AppConstants.ErrorCode.SUCCESS);
//
//
//                }
//
//            }
//        });
//        return lists;
    }


    public interface CallBack {
        void onSuccess(int statusCode);

        void onFailure(int StatusCode);
    }

    public interface CallBackData {
        void onSuccess(int statusCode, final List<Student> list);

        void onFailure(int StatusCode);
    }

}
