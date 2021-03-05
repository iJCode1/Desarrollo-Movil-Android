package com.example.multiplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtN1);
        et2 = (EditText)findViewById(R.id.txtN2);
        tv1 = (TextView)findViewById(R.id.txtMsj);
    }

    //Métodos para sumar, restar, multiplicar y dividir los numeros
    // ingresados por el usuarios en las cajas de texto de tipo Number

    public void Suma(View view){
        // Recuperando numeros de las cajas en forma de String
        String numero1 = et1.getText().toString();
        String numero2 = et2.getText().toString();

        int n1 = Integer.parseInt(numero1);
        int n2 = Integer.parseInt(numero2);

        int suma = n1 + n2;

        String resultado = String.valueOf(suma);

        tv1.setText(resultado);
    }

    public void Resta(View view){
        // Recuperando numeros de las cajas en forma de String
        String numero1 = et1.getText().toString();
        String numero2 = et2.getText().toString();

        int n1 = Integer.parseInt(numero1);
        int n2 = Integer.parseInt(numero2);

        int resta = n1 - n2;

        String resultado = String.valueOf(resta);

        tv1.setText(resultado);
    }

    public void Multiplicacion(View view){
        // Recuperando numeros de las cajas en forma de String
        String numero1 = et1.getText().toString();
        String numero2 = et2.getText().toString();

        int n1 = Integer.parseInt(numero1);
        int n2 = Integer.parseInt(numero2);

        int mult = n1 * n2;

        String resultado = String.valueOf(mult);

        tv1.setText(resultado);
    }

    public void Division(View view){
        // Recuperando numeros de las cajas en forma de String
        String numero1 = et1.getText().toString();
        String numero2 = et2.getText().toString();

        int n1 = Integer.parseInt(numero1);
        int n2 = Integer.parseInt(numero2);

        int mult = n1 / n2;

        String resultado = String.valueOf(mult);

        tv1.setText(resultado);
    }
}