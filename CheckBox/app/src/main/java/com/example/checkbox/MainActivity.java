package com.example.checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tv1;
    private CheckBox cb1, cb2, cb3, cb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtN1);
        et2 = (EditText)findViewById(R.id.txtN2);

        tv1 = (TextView)findViewById(R.id.txtResultado);

        cb1 = (CheckBox)findViewById(R.id.txtSuma);
        cb2 = (CheckBox)findViewById(R.id.txtResta);
        cb3 = (CheckBox)findViewById(R.id.txtMult);
        cb4 = (CheckBox)findViewById(R.id.txtDiv);
    }

    public void Calcular(View view){
        String numero1_st = et1.getText().toString();
        String numero2_st = et2.getText().toString();

        if(numero1_st.isEmpty() || numero2_st.isEmpty()){
            Toast.makeText(this, "Debe ingresar los 2 números!", Toast.LENGTH_SHORT).show();
        }else{
            if(cb1.isChecked() | cb2.isChecked() | cb3.isChecked() | cb4.isChecked()){
                Float numero1_fl = Float.parseFloat(numero1_st);
                Float numero2_fl = Float.parseFloat(numero2_st);
                Float operacion;
                String operacion_str = "";
                String result;
                if(cb1.isChecked()){
                    operacion = numero1_fl + numero2_fl;
                    operacion_str += String.valueOf(operacion + "\n");

                    tv1.setText(operacion_str);
                }
                if(cb2.isChecked()){
                    operacion = numero1_fl - numero2_fl;
                    operacion_str += String.valueOf(operacion + "\n");

                    tv1.setText(operacion_str );
                }
                if(cb3.isChecked()){
                    operacion = numero1_fl * numero2_fl;
                    operacion_str += String.valueOf(operacion + "\n");

                    tv1.setText(operacion_str);
                }
                if(cb4.isChecked()){
                    if(numero2_fl !=0){
                        operacion = numero1_fl / numero2_fl;
                        operacion_str += String.valueOf(operacion);

                        tv1.setText(operacion_str);
                    }else{
                        Toast.makeText(this, "No puedes dividir entre 0!", Toast.LENGTH_SHORT).show();
                        tv1.setText("");
                    }

                }
            }else{
                tv1.setText("");
                Toast.makeText(this, "Debes elegir una opción!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}