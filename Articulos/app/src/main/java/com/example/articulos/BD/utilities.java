package com.example.articulos.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class utilities {
    public static final String TABLE_NAME = "products";
    public static final String ID_ROW = "id";
    public static final String NAME_ROW = "name";
    public static final String STOCK_ROW = "stock";
    public static final String PIC_ROW = "pic";
    public static final String DD_ROW = "datadue";
    public static final String CAT_ROW = "category";
    public static final String STATUS_ROW = "status";


    public static final String DATABASE_NAME = "db_products";
    public static final int DATABASE_VERSION = 1;
    public static  Base CONN = null;
    public static final ContentValues CV = null;

    public static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE" + TABLE_NAME +
            "(" + ID_ROW + "INTEGER," +
            STOCK_ROW + "INTEGER," +
            NAME_ROW + "TEXT," +
            PIC_ROW + "TEXT," +
            DD_ROW + "TEXT," +
            CAT_ROW + "TEXT," +
            STATUS_ROW + "BOOLEAN)";

    public static final String DROP_TABLE_PRODUCTS = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static boolean initDataBase(Context context){
        CONN = new Base(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(CONN !=null){
            return true
        }else{
            return false;
        }
    }

    private static SQLiteDatabase getWritableConnection(){
        return CONN.getWritableDatabase();
    }

    private static SQLiteDatabase getRedeadableConnection(){
        return CONN.getReadableDatabase();
    }

    public static void closeDatabase(){
        CONN.close();
    }

    public static boolean createProduct(Productos producto){
        CV = new ContentValues();
        CV.put(ID_ROW, getLastID());
        CV.put(NAME_ROW, producto.getName());
        CV.put(STOCK_ROW, producto.getStock());
        CV.put(PIC_ROW, producto.getPic());
        CV.put(DD_ROW, producto.getDataDue());
        CV.put(CAT_ROW, producto.getCategory());
        CV.put(STATUS_ROW, producto.isStatus());

        return (getWritableConnection().insert(TABLE_NAME, null, CV)!=-1) ? true: false;
    }

}
