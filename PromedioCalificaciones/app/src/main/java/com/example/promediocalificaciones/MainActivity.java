package com.example.promediocalificaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private EditText et5;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtC1);
        et2 = (EditText)findViewById(R.id.txtC2);
        et3 = (EditText)findViewById(R.id.txtC3);
        et4 = (EditText)findViewById(R.id.txtC4);
        et5 = (EditText)findViewById(R.id.txtC5);
        tv1 = (TextView)findViewById(R.id.txtMsj);
    }

    public void Promediar(View view){
       String valor1 = et1.getText().toString();
       String valor2 = et2.getText().toString();
       String valor3 = et3.getText().toString();
       String valor4 = et4.getText().toString();
       String valor5 = et5.getText().toString();
       String estatus;

       float calif1 = Float.parseFloat(valor1);
       float calif2 = Float.parseFloat(valor2);
       float calif3 = Float.parseFloat(valor3);
       float calif4 = Float.parseFloat(valor4);
       float calif5 = Float.parseFloat(valor5);

       float promedio = (calif1 + calif2 + calif3 + calif4 + calif5) / 5;

       if(promedio >=70){
           estatus = "Aprobado!";
       }else{
           estatus = "Reprobado!";
       }

       tv1.setText(estatus);
    }

}