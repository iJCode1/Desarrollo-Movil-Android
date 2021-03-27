package com.example.aprendiendomaterialdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Salir(View view){
        finishAffinity();
    }

    public void Gato(View view){
        Toast.makeText(this, "Miauuuuuuuuu", Toast.LENGTH_LONG).show();
    }

    public void segundoActivity(View view){
        Intent i = new Intent(this, activity2.class);
        startActivity(i);
    }
}

