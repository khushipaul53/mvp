package com.example.assignment5mvp.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.assignment5mvp.data.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAllusers();

    @Insert
    public long insertAll(Student student);

    @Update
    public int update(Student student);

    @Delete
    public int DeleteAll(Student student);

    @Query("SELECT COUNT(*) FROM student WHERE rollno =:rollNumber")
    long checkUserExist(final String rollNumber);

}
