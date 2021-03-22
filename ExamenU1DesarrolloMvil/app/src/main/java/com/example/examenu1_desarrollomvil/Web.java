package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class Web extends AppCompatActivity {

    EditText txtBusqueda;
    WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        txtBusqueda = (EditText)findViewById(R.id.txtBusqueda);
        wv1 = (WebView)findViewById(R.id.webV1);


    }

    public void buscar(View view){
        String URL = String.valueOf(txtBusqueda.getText());

        if(URL.trim().isEmpty()){
            Toast.makeText(this, "Debes ingresar una palabra", Toast.LENGTH_LONG).show();
        }else{
            wv1.loadUrl("https://www.google.com/search?q="+URL);
            wv1.setWebViewClient(new WebViewClient());
        }
    }

    public void regresar(View view){
        Intent i = new Intent(this, Opciones.class);
        startActivity(i);
    }
}