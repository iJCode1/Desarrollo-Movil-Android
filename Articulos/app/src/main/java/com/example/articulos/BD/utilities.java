package com.example.articulos.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class utilities {
    public static final String TABLE_NAME = "products";
    public static final String ID_ROW = "id";
    public static final String NAME_ROW = "name";
    public static final String STOCK_ROW = "stock";
    public static final String PIC_ROW = "pic";
    public static final String DD_ROW = "datedue";
    public static final String CAT_ROW = "category";
    public static final String STATUS_ROW = "status";

    public static final String DATABASE_NAME = "db_products";
    public static final int DATABASE_VERSION = 1;
    public static Base conn = null;
    public static ContentValues CV = null;

    public static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE "+TABLE_NAME+
            "("+ID_ROW+" INTEGER,"+
            STOCK_ROW+" INTEGER,"+
            NAME_ROW+" TEXT,"+
            PIC_ROW+" TEXT,"+
            DD_ROW+" TEXT,"+
            CAT_ROW+" TEXT,"+
            STATUS_ROW+" BOOLEAN);";

    public static final String DROP_PRODUCTS_TABLE = "DROP TABLE IF EXIST "+TABLE_NAME;

    public static boolean initDataBase (Context context){
        conn = new Base(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (conn != null){
            return true;
        }
        return false;
    }

    private static SQLiteDatabase getWritableConnection(){
        return conn.getWritableDatabase();
    }

    private static SQLiteDatabase getReadableConnection(){
        return conn.getReadableDatabase();
    }

    public static void closeDataBase(){
        conn.close();
    }

    public static boolean createProduct (Products product){
        CV = new ContentValues();
        CV.put(ID_ROW,getLastID());
        CV.put(NAME_ROW,product.getName());
        CV.put(STOCK_ROW,product.getStock());
        CV.put(PIC_ROW,product.getPic());
        CV.put(DD_ROW,product.getDateDue());
        CV.put(CAT_ROW,product.getCategory());
        CV.put(STATUS_ROW,product.getStatus());

        return (getWritableConnection().insert(TABLE_NAME,null,CV) != -1) ? true: false;
    }

    public static Integer getLastID() {
        Cursor cursor = getReadableConnection().rawQuery("SELECT * FROM "+TABLE_NAME+
                " WHERE ID = (SELECT MAX(ID) FROM "+TABLE_NAME+" ); ", null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                return cursor.getInt(0)+1;
            }
        }
        return 1;
    }

    public static Products searchProducts(int id){
        Cursor cursor = getReadableConnection().rawQuery("SELECT * FROM "+TABLE_NAME+
                " WHERE " + ID_ROW + "="+id,null);
        if (cursor.moveToFirst()){
            Products p = new Products();
            do {
                p.setId(cursor.getInt(0));
                p.setStock(cursor.getInt(1));
                p.setName(cursor.getString(2));
                p.setPic(cursor.getString(3));
                p.setDateDue(cursor.getString(4));
                p.setCategory(cursor.getString(5));
                p.setStatus(cursor.getInt(6)>0);
            }while(cursor.moveToNext());
            return p;
        }
        return null;
    }

    public static boolean updateProducts(Products product){
        CV = new ContentValues();
        CV.put(ID_ROW,product.getId());
        CV.put(NAME_ROW,product.getName());
        CV.put(STOCK_ROW,product.getStock());
        CV.put(PIC_ROW,product.getPic());
        CV.put(DD_ROW,product.getDateDue());
        CV.put(CAT_ROW,product.getCategory());
        CV.put(STATUS_ROW,product.getStatus());

        return (getWritableConnection().update(TABLE_NAME,CV,ID_ROW+
                "="+String.valueOf(product.getId()), null) != 0) ? true: false;
    }

    public static ArrayList<Products> getListActive() {
        ArrayList<Products> list = new ArrayList<>();
        Cursor cursor = getReadableConnection().rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + STATUS_ROW + "=" + 1, null);
        if (cursor.moveToFirst()) {
            do {
                Products p = new Products();
                p.setId(cursor.getInt(0));
                p.setStock(cursor.getInt(1));
                p.setName(cursor.getString(2));
                p.setPic(cursor.getString(3));
                p.setDateDue(cursor.getString(4));
                p.setCategory(cursor.getString(5));
                p.setStatus(cursor.getInt(6) > 0);
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public static ArrayList<Products> getListHiden() {
        ArrayList<Products> list = new ArrayList<>();
        Cursor cursor = getReadableConnection().rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + STATUS_ROW + "=" + 1, null);
        if (cursor.moveToFirst()) {
            do {
                Products p = new Products();
                p.setId(cursor.getInt(0));
                p.setStock(cursor.getInt(1));
                p.setName(cursor.getString(2));
                p.setPic(cursor.getString(3));
                p.setDateDue(cursor.getString(4));
                p.setCategory(cursor.getString(5));
                p.setStatus(cursor.getInt(6) > 0);
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public static boolean definitiveDel(int id){
        return (getWritableConnection().delete(TABLE_NAME,ID_ROW+
                "="+id,null)>0) ? true: false;
    }

    public static boolean logicDel(int id){
        Products p = searchProducts(id);
        p.setStatus(false);
        return updateProducts(p);
    }

    public static boolean reactiveProduct(int id){
        Products p = searchProducts(id);
        p.setStatus(true);
        return updateProducts(p);
    }

}
