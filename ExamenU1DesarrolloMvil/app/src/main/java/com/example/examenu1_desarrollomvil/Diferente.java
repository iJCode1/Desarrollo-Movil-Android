package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Diferente extends AppCompatActivity {

    ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferente);

        iv1 = (ImageView)findViewById(R.id.imageView1);


    }

    public void mostrarOcultar(View view){
        if(iv1.getVisibility() == View.GONE){
            iv1.setVisibility(View.VISIBLE);
        }else{
            iv1.setVisibility(View.GONE);
        }
    }

    public void regresar(View view){
        Intent i = new Intent(this, Opciones.class);
        startActivity(i);
    }
}