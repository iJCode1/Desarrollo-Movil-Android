package com.example.parametros_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv1 = (TextView)findViewById(R.id.txtMensaje);

        String nombre = getIntent().getStringExtra("name");
        String edad = getIntent().getStringExtra("edad");

        tv1.setText("Hola "+ nombre+"! tienes "+edad+" años \uD83D\uDC4C\uD83C\uDFFB");
    }

    public void Regresar(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}