package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Formulario extends AppCompatActivity {

    Button btnSalir;
    Button btnGuardar;
    EditText etNombre, etFecha, etTelefono;
    RadioButton rbFem, rbMas;
    CheckBox cbAzul, cbAma, cbMorado, cbNegro, cbVerde;
    Button btnmuestra;
    Boolean c1, c2, c3, c4, c5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        btnSalir = findViewById(R.id.btnLimpiar);
        btnGuardar = findViewById(R.id.btnMostrar);

        etNombre = findViewById(R.id.txtNombre);
        etFecha = findViewById(R.id.txtFecha);
        etTelefono = findViewById(R.id.txtTelefono);

        rbFem = findViewById(R.id.rbFemenino);
        rbMas = findViewById(R.id.rbMasculino);

        cbAzul = findViewById(R.id.cbAzul);
        cbAma = findViewById(R.id.cbAmarillo);
        cbMorado = findViewById(R.id.cbMorado);
        cbNegro = findViewById(R.id.cbNegro);
        cbVerde = findViewById(R.id.cbVerde);

        btnmuestra = findViewById(R.id.btnMostrar);

        btnmuestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Datos.class);

                //Asignando Datos al Intent
                i.putExtra("nombre", etNombre.getText().toString());
                i.putExtra("fecha", etFecha.getText().toString());
                i.putExtra("telefono", etTelefono.getText().toString());
                i.putExtra("sexo", getGenero());
                i.putExtra("colores", getColores());

                startActivity(i);
            }
        });

    }

    private String getGenero(){
        if(rbFem.isChecked()){
            return "Femenino";
        }else{
            return "Masculino";
        }
    }

    private String getColores(){
        c1 = cbAzul.isChecked();
        c2 = cbAma.isChecked();
        c3 = cbMorado.isChecked();
        c4 = cbNegro.isChecked();
        c5 = cbVerde.isChecked();

        String colores = "";

        if(c1){
            colores = colores + "Azul ";
        }
        if(c2){
            colores = colores + "Amarillo ";
        }
        if(c3){
            colores = colores + "Morado ";
        }
        if(c4){
            colores = colores + "Negro ";
        }
        if(c5){
            colores = colores + "Verde ";
        }

        return colores;
    }
}