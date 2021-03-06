package com.example.videoview;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView videoV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoV = (VideoView)findViewById(R.id.videov);

        Uri videoUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.animation);

        videoV.setVideoURI(videoUri);

        MediaController mediaC = new MediaController(this);

        videoV.setMediaController(mediaC);

        videoV.start();
    }
}