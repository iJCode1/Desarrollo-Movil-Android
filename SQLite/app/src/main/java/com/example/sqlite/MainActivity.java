package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etCodigo, etDescripcion, etPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = (EditText)findViewById(R.id.txtCodigo);
        etDescripcion = (EditText)findViewById(R.id.txtDescripcion);
        etPrecio = (EditText)findViewById(R.id.txtPrecio);
    }

    //Método para dar de alta a los productos.
    public void Registrar(View view){
        dbHelper helper = new dbHelper(this, "productos", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase(); //Abrir BD en modo lectura y escritura

        //Obteniendo los valores que ingreso el usuario
        String sCodigo = etCodigo.getText().toString();
        String sDescripcion = etDescripcion.getText().toString();
        String sPrecio = etPrecio.getText().toString();

        //Validando si los datos fueron ingresados o no
        if(!sCodigo.trim().isEmpty() && !sDescripcion.trim().isEmpty() && !sPrecio.trim().isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", sCodigo);
            registro.put("descripcion", sDescripcion);
            registro.put("precio", sPrecio);

            db.insert("productos", null, registro);
            db.close();

            //Limpiando los Datos
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");

            Toast.makeText(this, "El registro fue exitoso!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Debes ingresar todos los datos", Toast.LENGTH_LONG).show();
        }
    }

    //Método para consultar un producto
    public void Buscar(View view){
        dbHelper helper = new dbHelper(this, "productos", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase(); //Abrir BD en modo lectura y escritura

        //Obteniendo el código de producto que ingreso el usuario
        String sCodigo = etCodigo.getText().toString();

        //Validando si el código fue o no ingresado
        if(!sCodigo.trim().isEmpty()){
            //Haciendo la busqueda del código
            Cursor fila = db.rawQuery("select descripcion, precio from productos where codigo = "+sCodigo, null);

            //SI la consulta devolvio valores (Si existe el código)
            if(fila.moveToFirst()){
                etDescripcion.setText(fila.getString(0));
                etPrecio.setText(fila.getString(1));
                db.close();
            }else{
                Toast.makeText(this, "No se encontro el producto del código ingresado", Toast.LENGTH_LONG).show();
                db.close();
            }
        }else {
            Toast.makeText(this, "Debes ingresar el código del producto", Toast.LENGTH_LONG).show();
        }
    }

    //Método para eliminar un producto
    public void Eliminar(View view){
        dbHelper helper = new dbHelper(this, "productos", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase(); //Abrir BD en modo lectura y escritura

        //Obteniendo el código de producto que ingreso el usuario
        String sCodigo = etCodigo.getText().toString();

        //Validando si el código fue o no ingresado
        if(!sCodigo.trim().isEmpty()){
            //Haciendo la busqueda del código
            int cantidad = db.delete("productos", "codigo=" + sCodigo, null);
            db.close();

            //Limpiando los Datos
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Producto eliminado satisfactoriamente!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "Debes ingresar el código del producto", Toast.LENGTH_LONG).show();
        }
    }

    //Método para modificar los datos de un producto
    public void Modificar(View view){
        dbHelper helper = new dbHelper(this, "productos", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase(); //Abrir BD en modo lectura y escritura

        //Obteniendo los valores que ingreso el usuario
        String sCodigo = etCodigo.getText().toString();
        String sDescripcion = etDescripcion.getText().toString();
        String sPrecio = etPrecio.getText().toString();

        if(!sCodigo.trim().isEmpty() && !sDescripcion.trim().isEmpty() && !sPrecio.trim().isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", sCodigo);
            registro.put("descripcion", sDescripcion);
            registro.put("precio", sPrecio);
            //Haciendo la busqueda del código
            int cantidad = db.update("productos", registro, "codigo="+ sCodigo, null);
            db.close();

            if(cantidad == 1){
                Toast.makeText(this, "Producto modificado satisfactoriamente!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "Debes ingresar todos los datos", Toast.LENGTH_LONG).show();
        }

    }
}