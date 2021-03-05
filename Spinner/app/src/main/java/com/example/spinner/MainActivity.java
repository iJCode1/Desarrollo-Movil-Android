package com.example.spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tv1;
    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtN1);
        et2 = (EditText)findViewById(R.id.txtN2);

        tv1 = (TextView)findViewById(R.id.txtResultado);

        sp = (Spinner)findViewById(R.id.spinner);

        String operaciones[] = {"Suma", "Resta", "Multiplicación", "División"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, operaciones);

        sp.setAdapter(adapter);

    }

    public void Calcular(View view){

        String valor1_st = et1.getText().toString();
        String valor2_st = et2.getText().toString();

        if(valor1_st.isEmpty() | valor2_st.isEmpty()){
            Toast.makeText(this, "Debes ingresar ambos numeros", Toast.LENGTH_SHORT).show();
        }else{
            float valor1_flo = Float.parseFloat(valor1_st);
            float valor2_flo = Float.parseFloat(valor2_st);

            String opcion = sp.getSelectedItem().toString();
            float operacion;

            if(opcion.equals("Suma")){
                operacion = valor1_flo + valor2_flo;
                String resultado_str = String.valueOf(operacion);
                tv1.setText(resultado_str);
            }else if(opcion.equals("Resta")){
                operacion = valor1_flo - valor2_flo;
                String resultado_str = String.valueOf(operacion);
                tv1.setText(resultado_str);
            }else if(opcion.equals("Multiplicación")){
                operacion = valor1_flo * valor2_flo;
                String resultado_str = String.valueOf(operacion);
                tv1.setText(resultado_str);
            }else if(opcion.equals("División")){
                if(valor2_flo !=0){
                    operacion = valor1_flo / valor2_flo;
                    String resultado_str = String.valueOf(operacion);
                    tv1.setText(resultado_str);
                }else{
                    Toast.makeText(this, "No se puede dividir entre 0", Toast.LENGTH_SHORT).show();
                    tv1.setText("");
                }
            }
        }

    }

}