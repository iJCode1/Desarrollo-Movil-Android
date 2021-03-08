package com.example.parametros_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtUsuario);
        et2 = (EditText)findViewById(R.id.txtEdad);
    }

    public void Ingreso(View view){
        String nombre = et1.getText().toString();
        String edad = et2.getText().toString();

        if(nombre.isEmpty() || edad.isEmpty()){
            Toast.makeText(this, "Debes ingresar los 2 campos \uD83E\uDDD1\u200D\uD83D\uDCBB", Toast.LENGTH_SHORT).show();
        }else{
            Intent i = new Intent(this, MainActivity2.class);
            i.putExtra("name", nombre);
            i.putExtra("edad", edad);
            startActivity(i);
        }
    }
}