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
    public static final String DD_ROW = "datadue";
    public static final String CAT_ROW = "category";
    public static final String STATUS_ROW = "status";


    public static final String DATABASE_NAME = "db_products";
    public static final int DATABASE_VERSION = 1;
    public static Base CONN = null;
    public static ContentValues CV = null;

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
            return true;
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
        CV.put(STATUS_ROW, producto.getStatus());

        return (getWritableConnection().insert(TABLE_NAME, null, CV)!=-1) ? true: false;
    }

    public static Integer getLastID(){
        Cursor cursor = getRedeadableConnection().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = (SELECT MAX(ID)" + "FROM" + TABLE_NAME +"); ", null);

        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                return cursor.getInt(0)+1;
            }
        }
        return 1;
    }

    public static Productos searchProducto(int id){
        Cursor cursor = getRedeadableConnection().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE" + ID_ROW + "=" + id +"); ", null);

        if(cursor.moveToFirst()){
            Productos p = new Productos();
            do{
                p.setId(cursor.getInt(0));
                p.setStock(cursor.getInt(1));
                p.setName(cursor.getString(2));
                p.setPic(cursor.getString(3));
                p.setDataDue(cursor.getString(4));
                p.setCategory(cursor.getString(5));
                p.setStatus(cursor.getInt(6)>0);
            }while (cursor.moveToNext());
            return p;
        }
        return null;
    }

    public static boolean updateProductos(Productos producto){
        CV = new ContentValues();
        CV.put(ID_ROW, producto.getId());
        CV.put(NAME_ROW, producto.getName());
        CV.put(STOCK_ROW, producto.getStock());
        CV.put(PIC_ROW, producto.getPic());
        CV.put(DD_ROW, producto.getDataDue());
        CV.put(CAT_ROW, producto.getCategory());
        CV.put(STATUS_ROW, producto.getStatus());

        return (getWritableConnection().update(TABLE_NAME, CV, ID_ROW + "=" + String.valueOf(producto.getId()), null)!=-0) ? true: false;
    }

    public static ArrayList<Productos> getListActive(){
        ArrayList<Productos> list = new ArrayList<>();
        Cursor cursor = getRedeadableConnection().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + STATUS_ROW + "=" + 1, null);

        if(cursor.moveToFirst()){
            Productos p = new Productos();
            do{
                p.setId(cursor.getInt(0));
                p.setStock(cursor.getInt(1));
                p.setName(cursor.getString(2));
                p.setPic(cursor.getString(3));
                p.setDataDue(cursor.getString(4));
                p.setCategory(cursor.getString(5));
                p.setStatus(cursor.getInt(6)>0);
                list.add(p);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public static ArrayList<Productos> getListHidden(){
        ArrayList<Productos> list = new ArrayList<>();
        Cursor cursor = getRedeadableConnection().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + STATUS_ROW + "=" + 0, null);

        if(cursor.moveToFirst()){
            Productos p = new Productos();
            do{
                p.setId(cursor.getInt(0));
                p.setStock(cursor.getInt(1));
                p.setName(cursor.getString(2));
                p.setPic(cursor.getString(3));
                p.setDataDue(cursor.getString(4));
                p.setCategory(cursor.getString(5));
                p.setStatus(cursor.getInt(6)>0);
                list.add(p);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public static boolean definityDeal(int id){
        return(getWritableConnection().delete(TABLE_NAME, ID_ROW +
                "=" + id, null) > 0) ? true : false;
    }

    public static boolean logicDel(int id){
        Productos p = searchProducto(id);
        p.setStatus(false);
        return updateProductos(p);
    }

    public static boolean reactiveProducto(int id){
        Productos p = searchProducto(id);
        p.setStatus(true);
        return updateProductos(p);
    }

}
