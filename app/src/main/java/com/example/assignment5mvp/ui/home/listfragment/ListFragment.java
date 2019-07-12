package com.example.assignment5mvp.ui.home.listfragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.assignment5mvp.MainViewModel;
import com.example.assignment5mvp.MyApplication;
import com.example.assignment5mvp.R;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.ListFragmentClickListener;
import com.example.assignment5mvp.ui.home.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment implements ListFragmentView, OnItemClickListener {
    Student student = new Student();
    private ListFragmentClickListener listFragmentClickListener;
    private Button btnAdd;
    private MainViewModel mMyViewModel = new MainViewModel();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StudentAdapter studentAdapter;
    private List<Student> studentList = new ArrayList<>();
    private Constants constants;
    private int clickPosition;
    private ListFragmentPresenter listFragmentPresenter;

    public ListFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMyViewModel.getCurrentData().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> list) {
                studentList.clear();
                studentList.addAll(list);
                studentAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        constants = new Constants();
        studentAdapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(studentAdapter);


        btnAdd = view.findViewById(R.id.fragment_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFragmentClickListener.onClick(constants.ADD, student);
            }
        });
        listFragmentPresenter = new ListFragmentPresenterImpl(this, MyApplication.getApplication().getDataManager());
        //fetching database object from util class
        fetchAllDataFromDb();
        studentAdapter.setOnItemClickListener(this);
        return view;
    }

    public void instantiateListener(ListFragmentClickListener listFragmentClickListener) {
        this.listFragmentClickListener = listFragmentClickListener;
    }

    public void fetchAllDataFromDb() {
        listFragmentPresenter.getStudentData();
    }

    @Override
    public void onSuccess(int statusCode) {

    }

    @Override
    public void onFailure(int errorMessage) {


    }

    @Override
    public void onStudentListArrived(List<Student> list) {

    }


    @Override
    public void onItemClick(final int position, View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle(getResources().getString(R.string.choose))
                .setPositiveButton(getResources().getString(R.string.delete_option), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listFragmentPresenter.deleteStudent(studentList.get(position));
                        studentList.remove(position);
                        studentAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.edit_option), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        clickPosition = position;
                        listFragmentClickListener.onClick(constants.EDIT, studentList.get(clickPosition));
//                        listFragmentPresenter.updateStudent(studentList.get(clickPosition));
                    }
                })
                .setNeutralButton(getResources().getString(R.string.view_option), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clickPosition = position;
                        listFragmentClickListener.onClick(constants.VIEW, studentList.get(clickPosition));


                    }

                })

                .show();
    }


}





