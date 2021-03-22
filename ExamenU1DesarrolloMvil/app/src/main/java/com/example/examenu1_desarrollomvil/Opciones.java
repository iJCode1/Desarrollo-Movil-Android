package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Opciones extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
    }

    public void calculadora(View view){

    }

    public void imagenes(View view){
        Intent i = new Intent(this, imagenes.class);
        startActivity(i);
    }

    public void web(View view){

    }

    public void video(View view){

    }

    public void reloj(View view){

    }

    public void diferente(View view){

    }

    public void rating(View view){

    }

    public void acerca(View view){

    }

    public void salir(View view){
        finishAffinity();
    }
}