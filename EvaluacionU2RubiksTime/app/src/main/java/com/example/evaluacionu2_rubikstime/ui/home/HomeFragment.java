package com.example.evaluacionu2_rubikstime.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaluacionu2_rubikstime.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private VideoView videoV;
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initComponents();

        return root;
    }

    private void initComponents() {
        videoV = root.findViewById(R.id.videoHome);

        Uri videoUri = Uri.parse("android.resource://"+getContext().getPackageName()+"/"+R.raw.appt2);

        videoV.setVideoURI(videoUri);

        MediaController mediaC = new MediaController(getContext());

        videoV.setMediaController(mediaC);

        videoV.start();

    }
}