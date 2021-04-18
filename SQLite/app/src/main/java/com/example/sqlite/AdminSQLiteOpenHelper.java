package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //recibe como parámetro un objeto SQLIteDatabase.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table articulos(codigo Integer primary key, descripcion text, precio real)");
    }

    //recibe como parámetros un objeto SQLiteDatabase y dos enteros
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList llenar_listView(){
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String consulta = "Select * from articulos order by descripcion desc";
        Cursor registros = db.rawQuery(consulta, null);
        if(registros.moveToFirst()){
            do{
                lista.add(registros.getString(1));
            }while(registros.moveToNext());
        }

        return lista;
    }


}
