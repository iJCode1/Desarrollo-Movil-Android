package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity2 extends AppCompatActivity {
    
    WebView wb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        wb1 = (WebView)findViewById(R.id.wbResultado);

        String URL = getIntent().getStringExtra("busqueda");

        wb1.loadUrl("https://"+URL);
        wb1.setWebViewClient(new WebViewClient());
    }

    public void Regresar(View view){
        finish();
    }
}