package com.example.assignment5mvp.constant;

public interface AppConstants {

    enum OpertaionType{
        UPDATE,
        DELETE,
        EDIT,
    }

    interface ErrorCode{
        int ALREADY_EXIST = 10;
        int USERUPDATED = 11;
        int USER_INSERTED = 12;
        int UNKNOWN_ERROR = 13;
        int CORRECT_NAME = 14;
        int CORRECT_ROLLNO = 15;
        int CORRECT_CLASS =16 ;
        int SUCCESS = 200;
    }
}
