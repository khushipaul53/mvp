package com.example.assignment5mvp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Student")
public class Student{
    @ColumnInfo(name="Name")
    public String name;
    @ColumnInfo(name="rollno")
    public String rollno;
    @ColumnInfo(name="class")
    public String cls;
    @PrimaryKey(autoGenerate = true)
    public int id;


    public Student()
    {}
    public Student(String stuName,String stuRollno,String stuClass)
    {
        name=stuName;
        rollno=stuRollno;
        cls=stuClass;


    }

    public String getName()
    {
        return name;
    }
    public String getRollno()
    {
        return rollno;
    }
    public String getClasses()
    {
        return cls;
    }


}
