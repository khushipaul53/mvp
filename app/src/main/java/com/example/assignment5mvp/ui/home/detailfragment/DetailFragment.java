package com.example.assignment5mvp.ui.home.detailfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment5mvp.MyApplication;
import com.example.assignment5mvp.R;
import com.example.assignment5mvp.constant.AppConstants;
import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.db.DataBaseHandler;
import com.example.assignment5mvp.data.model.Student;
import com.example.assignment5mvp.ui.home.callback.DetailFragmentClickListener;
import com.example.assignment5mvp.ui.home.callback.OnDataSaveListener;

import java.util.ArrayList;


public class DetailFragment extends Fragment implements DetailFragmentView, OnDataSaveListener {
    private static DataBaseHandler dataBaseHandler;
    DataReceiver dataReceiver = new DataReceiver();
    private EditText etName, etRollno, etCls;
    private Button btnSubmit;
    private String mName, mRollno, mCls;
    private DetailFragmentClickListener detailFragmentClickListener;
    private DetailFragmentPresenter detailFragmentPresenter;
    private String mAction;
    private Constants constants;
    private boolean flag = true;
    private int status;
    private ArrayList<Student> mStudentArrayList;
    private DetailFragmentPresenter presenter;
    private Student updateStudent;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Bundle bundle) {
        DetailFragment detailFragment = new DetailFragment();


        if (bundle != null)
            detailFragment.setArguments(bundle);
        return detailFragment;


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//
//    }
//
//    @Override
//    public void onStop() {
//
//        super.onStop();
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailFragmentPresenter = new DetailFragmentPresenterImpl(this, MyApplication.getApplication().getDataManager());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        init(view);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(constants.ACTION_KEY).equals(constants.VIEW)) {
            etName.setEnabled(false);
            etRollno.setEnabled(false);
            etCls.setEnabled(false);
            etName.setText(bundle.getString(constants.NAME_KEY));
            etRollno.setText(bundle.getString(constants.ROLLNO_KEY));
            etCls.setText(bundle.getString(constants.CLASS_KEY));
            btnSubmit.setVisibility(View.INVISIBLE);
        }
        //instantiating the detailfragmentpresenter
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mName = etName.getText().toString();
                mRollno = etRollno.getText().toString();
                mCls = etCls.getText().toString();
                if (detailFragmentPresenter.validateText(mName, mRollno, mCls, mAction)) {
                    detailFragmentPresenter.insertStudent(new Student(mName, mRollno, mCls));
                    detailFragmentClickListener.myClick(constants.ADD, new Student(mName, mRollno, mCls));


                } else if (detailFragmentPresenter.validateText(mName, mRollno, mCls, mAction) && mAction.equals(constants.EDIT)) {

                    updateStudentInfo(new Student(mName, mRollno, mCls));
                    detailFragmentClickListener.myClick(constants.ADD, new Student(mName, mRollno, mCls));

                }


            }
        });


        return view;
    }

    //initialising variables
    public void init(View view) {

        mStudentArrayList = new ArrayList<>();
        etName = view.findViewById(R.id.detail_et_name);
        etRollno = view.findViewById(R.id.detail_et_rollno);
        etCls = view.findViewById(R.id.detail_et_class);
        btnSubmit = view.findViewById(R.id.detail_btn);
        constants = new Constants();

    }

    public void instantiateListener(DetailFragmentClickListener detailFragmentClickListener) {
        this.detailFragmentClickListener = detailFragmentClickListener;
    }

    @Override
    public void onSuccess(final int statusCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (statusCode) {


                    case AppConstants.ErrorCode.USER_INSERTED:
                        Toast.makeText(getActivity(), "Insertred into Db", Toast.LENGTH_SHORT).show();

                        break;


                    case AppConstants.ErrorCode.USERUPDATED:
                        Toast.makeText(getActivity(), "Db Updated", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

    }


    public void clearEditText() {
        etName.getText().clear();
        etRollno.getText().clear();
        etCls.getText().clear();
        setBtnAction(constants.ADD);
    }

    public void setEditText(Student student) {
        etName.setText(student.getName());
        etRollno.setText(student.getRollno());
//        etRollno.setEnabled(false);
        etCls.setText(student.getClasses());
        flag = false;
        setBtnAction(constants.EDIT);
//
    }

    public void setBtnAction(String actiontype) {
        btnSubmit.setText(actiontype);
    }

    //showing error message on failure of validation
    @Override
    public void onFailure(final int errorMessage) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (errorMessage) {

                    case AppConstants.ErrorCode.UNKNOWN_ERROR:
                        Toast.makeText(getActivity(), "Problem in Db", Toast.LENGTH_SHORT).show();
                        break;

                    case AppConstants.ErrorCode.ALREADY_EXIST:
                        Toast.makeText(getActivity(), "Already Exists", Toast.LENGTH_SHORT).show();
                        break;

                    case AppConstants.ErrorCode.CORRECT_NAME:
                        Toast.makeText(getActivity(), "Please enter correct name", Toast.LENGTH_SHORT).show();
                        break;
                    case AppConstants.ErrorCode.CORRECT_ROLLNO:
                        Toast.makeText(getActivity(), "Please enter correct rollno", Toast.LENGTH_SHORT).show();
                        break;
                    case AppConstants.ErrorCode.CORRECT_CLASS:
                        Toast.makeText(getActivity(), "Please enter correct class", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }


    //performing further operations if data is successfully saved in database
    @Override
    public void onDataSavedSuccess(boolean isAddStudent, Student student) {
        detailFragmentClickListener.myClick(mAction, student);
    }

    //showing error message if data is not saved in database
    @Override
    public void onDataSavedError(boolean isAddStudent) {
        if (isAddStudent) {
            Toast.makeText(getActivity(), getResources().getString(R.string.add_data_fail), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.update_data_fail), Toast.LENGTH_SHORT).show();

        }

    }

    public void viewDetails(Student student) {
        etName.setEnabled(false);
        etRollno.setEnabled(false);
        etCls.setEnabled(false);
        etName.setText(student.getName());
        etRollno.setText(student.getRollno());
        etCls.setText(student.getClasses());
        btnSubmit.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteError() {

    }

    @Override
    public void onFetchStudentList(ArrayList<Student> studentArrayList) {

    }

    public void action(String actionType) {
        this.mAction = actionType;
    }

    public void updateStudentInfo(final Student student) {
        this.updateStudent = student;
        Log.d("tag", "khushi");
//        btnSubmit.setText("Update");
        setEditText(updateStudent);


        /*
           1 update button text
           2 populate textview with student data
           3
         */
    }

    public void Viewinfo(final Student student) {
        viewDetails(student);
    }

    //broadcast receiver
    public class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //if database operation is performed successfully
            //checking the action key and performing the further operations
            if (intent.getStringExtra(constants.IS_SUCCESS).equals(constants.TRUE)) {
                if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
                    Student student = new Student(mName, mRollno, mCls);
//                    detailFragmentClickListener.myClick(constants.ADD,student);


                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT)) {
                    Student student = new Student(mName, mRollno, mCls);
                    detailFragmentClickListener.myClick(constants.EDIT, student);

                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE)) {
                    detailFragmentClickListener.myClick(constants.DELETE, null);


                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.READ_OPERATION)) {

                    detailFragmentClickListener.fetchDBList(mStudentArrayList);


                }
            } else if (intent.getStringExtra(constants.IS_SUCCESS).equals(constants.FALSE)) {
                if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.ADD)) {
                    detailFragmentClickListener.onDBoperationError(constants.ADD);
                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.EDIT)) {
                    detailFragmentClickListener.onDBoperationError(constants.EDIT);
                } else if (intent.getStringExtra(constants.ACTION_KEY).equals(constants.DELETE)) {
                    detailFragmentClickListener.onDBoperationError(constants.DELETE);
                }
            }
        }
    }


}
