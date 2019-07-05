package com.example.assignment5mvp.ui.home.detailfragment;

import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;

public interface DetailFragmentPresenter {
  boolean validateText(String name, String rollno, String cls,String actionType);
  void insertStudent(Student student);


}
