package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
        SQLiteDatabase bd = helper.getWritableDatabase(); //Abrir BD en modo escritura

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

            bd.insert("productos", null, registro);
            bd.close();

            //Limpiando los Datos
            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");

            Toast.makeText(this, "El registro fue exitoso!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Debes ingresar todos los datos", Toast.LENGTH_LONG).show();
        }
    }
}