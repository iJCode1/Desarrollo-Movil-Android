package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
        Intent i = new Intent(this, Web.class);
        startActivity(i);

    }

    public void video(View view){
        Intent i = new Intent(this, Video.class);
        startActivity(i);

    }

    public void reloj(View view){
        Intent i = new Intent(this, Reloj.class);
        startActivity(i);
    }

    public void diferente(View view){
        Intent i = new Intent(this, Diferente.class);
        startActivity(i);
    }

    public void rating(View view){
        Intent i = new Intent(this, ratingBar.class);
        startActivity(i);
    }

    public void alerta(View view){
        new AlertDialog.Builder(this)
                .setTitle("Acerca de")
                .setMessage("Alumno: Joel Dominguez Merino\n"+
                "Materia: Desarrollo Móvil\n"+
                "Docente: Rocio Elizabeth Pulido Alba\n"+
                "Curso: Febrero-Junio 2021").setPositiveButton("Aceptar", null).show();
    }

    public void salir(View view){
        finishAffinity();
    }



}