package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //recibe como parámetro un objeto SQLIteDatabase.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table productos(codigo Integer primary key, descripcion text, precio real)");
    }

    //recibe como parámetros un objeto SQLiteDatabase y dos enteros
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
