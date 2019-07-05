package com.example.assignment5mvp.data.db;

import com.example.assignment5mvp.MyApplication;
import com.example.assignment5mvp.constant.AppConstants;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.AppExecutor;
import com.example.assignment5mvp.data.model.Student;

import java.util.List;

public class DataManager {

    public DataManager(MyApplication myApplication) {
        this.dataBaseHandler = myApplication.getDatabase();
    }

    private DataBaseHandler dataBaseHandler;

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
                       }else {
                           callBack.onFailure(AppConstants.ErrorCode.UNKNOWN_ERROR);
                       }
                   }
               }else {
                   if (callBack != null) {
                      callBack.onFailure(AppConstants.ErrorCode.ALREADY_EXIST);
                   }
               }

            }
        });
    }

    public void   deleteStudent(final Student student) {
      AppExecutor.getExecutors().diskIO().execute(new Runnable(){


          @Override
          public void run() {
              dataBaseHandler.studentdao().DeleteAll(student);
          }
      });

    }

    public void updateStudent(final Student student)
    {
        AppExecutor.getExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dataBaseHandler.studentdao().update(student);

            }
        });
    }

    /**
     *
     * @param callBackData
     */
    public void getAllStudents(final CallBackData callBackData) {
        AppExecutor.getExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Student> data = dataBaseHandler.studentdao().getAllusers();
                if (data != null){
                    callBackData.onSuccess(AppConstants.ErrorCode.SUCCESS, data);
                }else {
                    callBackData.onFailure(AppConstants.ErrorCode.UNKNOWN_ERROR);
                }
            }
        });
    }







    public interface CallBack{
        void onSuccess(int statusCode);
        void onFailure(int StatusCode);
    }

    public interface CallBackData{
        void onSuccess(int statusCode, final List<Student>list);
        void onFailure(int StatusCode);
    }

}
