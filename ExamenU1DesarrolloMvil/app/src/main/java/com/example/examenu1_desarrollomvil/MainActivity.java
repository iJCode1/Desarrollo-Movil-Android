package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtCorreo);
        et2 = (EditText)findViewById(R.id.txtContraseña);
    }

    public void validarIngreso(View view){
        String correo = String.valueOf(et1.getText());
        String contraseña = String.valueOf(et2.getText());

        if(correo.trim().isEmpty() ||contraseña.trim().isEmpty()){
            Toast.makeText(this, "Debes ingresar ambos datos!", Toast.LENGTH_SHORT).show();
        }else{
            if(correo.equals("joeldome17@gmail.com") && contraseña.equals("1234")){
                Toast.makeText(this, "Inicio de Sesión Valido", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, Opciones.class);
                startActivity(i);
            }else{
                Toast.makeText(this, "Correo o Contraseña incorrecta", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Finalizar(View view){
        Toast.makeText(this, "Que tengas un buen dia!, Adios :D", Toast.LENGTH_LONG).show();
        finishAffinity();
    }
}