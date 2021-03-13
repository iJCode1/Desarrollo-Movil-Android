package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();

       /* int calif1 = 7, calif2 = 9, calif3 = 8;

        int promedio = (calif1 + calif2 + calif3) / 3;

        if(promedio >= 7){
            Toast.makeText(this, "Aprobaste, Felicidades!!!", Toast.LENGTH_SHORT).show();
        }else if(promedio <= 6){
            //Toast.makeText(this, "Reprobaste :(", Toast.LENGTH_SHORT).show();
        }
        */

        Button btnSalir;
        Button btnIngresar;

        btnIngresar = findViewById(R.id.btnIngresar);
        btnSalir = findViewById(R.id.btnCancelar);

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hasta Pronto ✌️", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Formulario.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Toast.makeText(this, "onStop", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
    }
}