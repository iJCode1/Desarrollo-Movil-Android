package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtBusqueda);
    }

    public void Buscar(View view){
        Intent i = new Intent(this, MainActivity2.class);

        String busqueda = et1.getText().toString();

        i.putExtra("busqueda", busqueda);
        startActivity(i);
    }
}