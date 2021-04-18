package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlite.MainActivity;

public class dbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ejemplo.db";

    private static final String SQL_CREAR  = "create table productos(codigo int primary key autoincrement, description text not null, precio real)";


    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //recibe como parámetro un objeto SQLIteDatabase.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR);
    }

    //recibe como parámetros un objeto SQLiteDatabase y dos enteros
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

/*
    public void agregar(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NOMBRE, nombre);

        db.insert(TABLA_NOMBRES, null,values);
        db.close();

    }

    public void obtener(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ID, COLUMNA_NOMBRE};

        Cursor cursor =
                db.query(TABLA_NOMBRES,
                        projection,
                        " _id = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);


        if (cursor != null)
            cursor.moveToFirst();

        System.out.println("El nombre es " +  cursor.getString(1) );
        db.close();
    }

    public void actualizar (String nombre, int id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre",nombre);

        int i = db.update(TABLA_NOMBRES,
                values,
                " _id = ?",
                new String[] { String.valueOf( id ) });
        db.close();
    }

    public boolean eliminar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLA_NOMBRES,
                    " _id = ?",
                    new String[] { String.valueOf (id ) });
            db.close();
            return true;

        }catch(Exception ex){
            return false;
        }
    }
 */


}
