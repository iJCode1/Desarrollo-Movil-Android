package com.example.evaluaciont3_rubikstimefirebase.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaluaciont3_rubikstimefirebase.R;
import com.example.evaluaciont3_rubikstimefirebase.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private VideoView videoV;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);

        initComponents();

        return root;
    }

    private void initComponents() {
        videoV = root.findViewById(R.id.videoHome);

        Uri videoUri = Uri.parse("android.resource://"+getContext().getPackageName()+"/"+R.raw.appt3);

        videoV.setVideoURI(videoUri);

        MediaController mediaC = new MediaController(getContext());

        videoV.setMediaController(mediaC);

        videoV.start();

    }
}