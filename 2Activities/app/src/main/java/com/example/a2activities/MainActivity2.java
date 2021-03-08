package com.example.a2activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void Anterior(View view){
        Intent i = new Intent(this, MainActivity.class);
        Toast.makeText(this, "Regresando ando \uD83D\uDC40", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}