package com.example.radiobuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tv1;
    private RadioButton rb1, rb2, rb3, rb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtN1);
        et2 = (EditText)findViewById(R.id.txtN2);

        tv1 = (TextView)findViewById(R.id.txtResult);

        rb1 = (RadioButton)findViewById(R.id.rbSuma);
        rb2 = (RadioButton)findViewById(R.id.rbResta);
        rb3 = (RadioButton)findViewById(R.id.rbMult);
        rb4 = (RadioButton)findViewById(R.id.rbDiv);
    }

    public void Calcular(View view){

        String valor1_string = et1.getText().toString();
        String valor2_string = et2.getText().toString();

        if(valor1_string.trim().isEmpty() | valor2_string.trim().isEmpty()){
            Toast.makeText(this, "Debes Ingresar ambos némeros!", Toast.LENGTH_SHORT).show();
        }else{
            if (!rb1.isChecked() && !rb2.isChecked() && !rb3.isChecked() && !rb4.isChecked()){
                Toast.makeText(this, "Debes elegir una opción", Toast.LENGTH_SHORT).show();
            }else{
                float valor1_float = Float.parseFloat(valor1_string);
                float valor2_float = Float.parseFloat(valor2_string);
                float operacion;
                if(rb1.isChecked()){
                    operacion = valor1_float + valor2_float;
                    String result = String.valueOf(operacion);
                    tv1.setText(result);
                }else if(rb2.isChecked()){
                    operacion = valor1_float - valor2_float;
                    String result = String.valueOf(operacion);
                    tv1.setText(result);
                }else if(rb3.isChecked()) {
                    operacion = valor1_float * valor2_float;
                    String result = String.valueOf(operacion);
                    tv1.setText(result);
                }else if(rb4.isChecked()){
                    if(valor2_float == 0){
                        Toast.makeText(this, "No puedes dividir entre 0", Toast.LENGTH_SHORT).show();
                        tv1.setText("");
                    }else{
                        operacion = valor1_float / valor2_float;
                        String result = String.valueOf(operacion);
                        tv1.setText(result);
                    }

                }
            }
        }
    }
}