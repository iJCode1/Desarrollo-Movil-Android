package com.example.ejemplowidget;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.widget.ProgressBar;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pgsBar;
    private Handler hdlr = new Handler();
    private int i = 0;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pgsBar = (ProgressBar) findViewById(R.id.progressBar);
        txtView = (TextView) findViewById(R.id.textView3);
        i = pgsBar.getProgress();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 1;

                    hdlr.post(new Runnable() {
                        public void run() {
                            pgsBar.setProgress(i);
                            txtView.setText(i+"/"+pgsBar.getMax());
                        }
                    });
                    try {
                        
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(i==100){
                        i=0;
                    }
                }
            }
        }).start();
    }
}