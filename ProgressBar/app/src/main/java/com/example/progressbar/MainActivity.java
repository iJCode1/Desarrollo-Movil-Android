package com.example.progressbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb1 = (ProgressBar)findViewById(R.id.pbProgreso);
    }

    public void Calcular(View view){
        String progreso = String.valueOf(pb1.getProgress());

        Toast.makeText(this, "El progreso es: "+progreso, Toast.LENGTH_SHORT).show();
    }





}