package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class imagenes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes);
    }

    public void regresar(View view){
        Intent i = new Intent(this, Opciones.class);
        startActivity(i);
    }
}