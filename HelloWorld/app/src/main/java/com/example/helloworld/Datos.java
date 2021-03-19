package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Datos extends AppCompatActivity {

    TextView tvNombre, tvFecha, tvTelefono, tvSexo, tvColor;

    Button btnAnterior, btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        tvNombre = findViewById(R.id.txtNombreD);
        tvFecha = findViewById(R.id.txtFechaD);
        tvTelefono = findViewById(R.id.txtTelefonoD);
        tvSexo = findViewById(R.id.txtGeneroD);
        tvColor = findViewById(R.id.txtColorFavD);

        Bundle extras = getIntent().getExtras();

        tvNombre.setText(extras.getString("nombre"));
        tvFecha.setText(extras.getString("fecha"));
        tvTelefono.setText(extras.getString("telefono"));
        tvSexo.setText(extras.getString("sexo"));
        tvColor.setText(extras.getString("colores"));

        btnAnterior = findViewById(R.id.btnSiguiente);

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Formulario.class);
                startActivity(i);
            }
        });

        btnSiguiente = findViewById(R.id.btnFinalizar);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Final.class);
                startActivity(i);
            }
        });
    }
}