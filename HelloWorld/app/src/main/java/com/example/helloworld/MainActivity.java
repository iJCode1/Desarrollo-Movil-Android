package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int calif1 = 7, calif2 = 9, calif3 = 8;

        int promedio = (calif1 + calif2 + calif3) / 3;

        if(promedio >= 7){
            Toast.makeText(this, "Aprobaste, Felicidades!!!", Toast.LENGTH_SHORT).show();
        }else if(promedio <= 6){
            Toast.makeText(this, "Reprobaste :(", Toast.LENGTH_SHORT).show();
        }


    }
}