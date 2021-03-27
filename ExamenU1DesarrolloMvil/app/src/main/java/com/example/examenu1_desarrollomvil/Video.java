package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video extends AppCompatActivity {

    VideoView videov1, videov2;
    //https://www.youtube.com/watch?v=_bE3PCRHq-A

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videov1 = (VideoView)findViewById(R.id.videoV1);

        videov1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animation));
        MediaController mc = new MediaController(this);

        videov1.setMediaController(mc);
        videov1.start();
    }

    public void regresar(View view){
        Intent i = new Intent(this, Opciones.class);
        startActivity(i);
    }
}