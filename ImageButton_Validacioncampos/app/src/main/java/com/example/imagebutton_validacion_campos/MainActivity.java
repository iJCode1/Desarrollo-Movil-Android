package com.example.imagebutton_validacion_campos;

import androidx.appcompat.app.AppCompatActivity;

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

        et1 = (EditText)findViewById(R.id.txtUsuario);
        et2 = (EditText)findViewById(R.id.txtContraseña);
    }

    public void Gato(View view){
        Toast.makeText(this, "Soy un Gato \uD83D\uDE40!", Toast.LENGTH_SHORT).show();
    }

    public void Validacion(View view){
        String usuario = et1.getText().toString();
        String pass = et2.getText().toString();

        if(usuario.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Debes ingresar ambos campos", Toast.LENGTH_SHORT).show();
        }else{
            if(usuario.equals("Joel") && pass.equals("1234")){
                Toast.makeText(this, "Login Correcto \uD83D\uDC40", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Usuario o Contraseña Incorrecta ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}