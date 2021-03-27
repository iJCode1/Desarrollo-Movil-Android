package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class calc extends AppCompatActivity implements View.OnClickListener{

    boolean suma;
    boolean resta;
    boolean mult;
    boolean div;
    boolean pot;
    boolean sen;
    boolean cos;
    boolean log;
    boolean tan;
    boolean raiz;
    boolean flotante;

    Double[] valor = new Double[20];
    Double result;

    Button btnCero, btnUno, btnDos, btnTres, btnCuatro, btnCinco, btnSeis, btnSiete, btnOcho, btnNueve;

    Button btnSuma, btnResta, btnMult, btnDiv, btnPunto, btnPot, btnSin, btnCos, btnTan, btnLog, btnBorrar, btnRaiz, btnIgual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        btnCero = (Button)findViewById(R.id.btnCero);
        btnCero.setOnClickListener(this);

        btnUno = (Button)findViewById(R.id.btnUno);
        btnUno.setOnClickListener(this);

        btnDos = (Button)findViewById(R.id.btnDos);
        btnDos.setOnClickListener(this);

        btnTres = (Button)findViewById(R.id.btnTres);
        btnTres.setOnClickListener(this);

        btnCuatro = (Button)findViewById(R.id.btnCuatro);
        btnCuatro.setOnClickListener(this);

        btnCinco = (Button)findViewById(R.id.btnCinco);
        btnCinco.setOnClickListener(this);

        btnSeis = (Button)findViewById(R.id.btnSeis);
        btnSeis.setOnClickListener(this);

        btnSiete = (Button)findViewById(R.id.btnSiete);
        btnSiete.setOnClickListener(this);

        btnOcho = (Button)findViewById(R.id.btnOcho);
        btnOcho.setOnClickListener(this);

        btnNueve = (Button)findViewById(R.id.btnNueve);
        btnNueve.setOnClickListener(this);

        btnSuma = (Button)findViewById(R.id.btnSuma);
        btnSuma.setOnClickListener(this);

        btnResta = (Button)findViewById(R.id.btnResta);
        btnResta.setOnClickListener(this);

        btnMult = (Button)findViewById(R.id.btnMult);
        btnMult.setOnClickListener(this);

        btnDiv = (Button)findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(this);

        btnPunto = (Button)findViewById(R.id.btnPunto);
        btnPunto.setOnClickListener(this);

        btnSin = (Button)findViewById(R.id.btnSin);
        btnSin.setOnClickListener(this);

        btnCos = (Button)findViewById(R.id.btnCos);
        btnCos.setOnClickListener(this);

        btnTan = (Button)findViewById(R.id.btnTan);
        btnTan.setOnClickListener(this);

        btnLog = (Button)findViewById(R.id.btnLog);
        btnLog.setOnClickListener(this);

        btnRaiz = (Button)findViewById(R.id.btnRaiz);
        btnRaiz.setOnClickListener(this);

        btnPot = (Button)findViewById(R.id.btnPot);
        btnPot.setOnClickListener(this);

        btnIgual = (Button)findViewById(R.id.btnIgual);
        btnIgual.setOnClickListener(this);

        btnBorrar = (Button)findViewById(R.id.btnBorrar);
        btnBorrar.setOnClickListener(this);
    }

    public void regresar(View view){
        Intent i = new Intent(this, Opciones.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        TextView tv1 = (TextView)findViewById(R.id.tvResultado);
        int opcion = v.getId();

        String dato1 = tv1.getText().toString();
        try{
            switch(opcion){
                case R.id.btnCero:
                    tv1.setText(dato1+"0");
                    break;
                case R.id.btnUno:
                    tv1.setText(dato1+"1");
                    break;
                case R.id.btnDos:
                    tv1.setText(dato1+"2");
                    break;
                case R.id.btnTres:
                    tv1.setText(dato1+"3");
                    break;
                case R.id.btnCuatro:
                    tv1.setText(dato1+"4");
                    break;
                case R.id.btnCinco:
                    tv1.setText(dato1+"5");
                    break;
                case R.id.btnSeis:
                    tv1.setText(dato1+"6");
                    break;
                case R.id.btnSiete:
                    tv1.setText(dato1+"7");
                    break;
                case R.id.btnOcho:
                    tv1.setText(dato1+"8");
                    break;
                case R.id.btnNueve:
                    tv1.setText(dato1+"9");
                    break;
                case R.id.btnPunto:
                    if(flotante == false){
                        tv1.setText(dato1+".");
                        flotante=true;
                    }else{
                        return;
                    }
                    break;
                case R.id.btnResta:
                    resta=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText("");
                    flotante=false;
                    break;
                case R.id.btnSuma:
                    suma=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText("");
                    flotante=false;
                    break;
                case R.id.btnMult:
                    mult=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText("");
                    flotante=false;
                    break;
                case R.id.btnDiv:
                    div=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText("");
                    flotante=false;
                    break;
                case R.id.btnPot:
                    pot=true;
                    valor[0]=Double.parseDouble(dato1);
                    valor[1]=Double.parseDouble(dato1);
                    tv1.setText("");
                    result = Math.pow(valor[0], valor[1]);
                    tv1.setText(String.valueOf(result));
                    flotante=false;
                    break;
                case R.id.btnRaiz:
                    raiz=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText("");
                    result = Math.sqrt(valor[0]);
                    tv1.setText(String.valueOf(result));
                    flotante=false;
                    break;
                case R.id.btnSin:
                    sen=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText("");
                    result = Math.sin(valor[0]*Math.PI/180);
                    tv1.setText(String.valueOf(result));
                    flotante=false;
                    break;
                case R.id.btnCos:
                    cos=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText(dato1+"Cos");
                    result = Math.cos(valor[0]*Math.PI/180);
                    tv1.setText(String.valueOf(result));
                    flotante=false;
                    break;
                case R.id.btnTan:
                    tan=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText(dato1+"Tan");
                    result = Math.tan(valor[0]*Math.PI/180);
                    tv1.setText(String.valueOf(result));
                    flotante=false;
                    break;
                case R.id.btnLog:
                    log=true;
                    valor[0]=Double.parseDouble(dato1);
                    tv1.setText(dato1+"Log");
                    result = Math.log(valor[0]);
                    tv1.setText(String.valueOf(result));
                    flotante=false;
                    break;
                case R.id.btnIgual:
                    valor[1]=Double.parseDouble(dato1);
                    if(suma==true){
                        result = valor[0]+valor[1];
                        tv1.setText(String.valueOf(result));
                    }else if(resta==true){
                        result = valor[0]-valor[1];
                        tv1.setText(String.valueOf(result));
                    }else if(mult==true){
                        result =  valor[0]*valor[1];
                        tv1.setText(String.valueOf(result));
                    }else if(div==true){
                        if (valor[1]==0) {
                            Toast.makeText(this, "No se puede dividir entre 0", Toast.LENGTH_LONG).show();
                        }else{
                            result = valor[0]/valor[1];
                            tv1.setText(String.valueOf(result));
                        }

                    }
                    flotante=false;
                    suma=false;
                    resta=false;
                    mult=false;
                    div=false;
                    sen=false;
                    cos=false;
                    tan=false;
                    log=false;
                    pot=false;
                    break;
                case R.id.btnBorrar:
                    tv1.setText("");
                    flotante=false;
                    break;
            }
        }catch(Exception ex){
            tv1.setText("Error");
        }
    }
}