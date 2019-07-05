package com.example.assignment5mvp.ui.home.listfragment;

import com.example.assignment5mvp.data.model.Student;

import java.util.List;

public interface ListFragmentView {
    void onSuccess(int statusCode);
    void onFailure(int errorMessage);
    void onStudentListArrived(final List<Student> list);
}
