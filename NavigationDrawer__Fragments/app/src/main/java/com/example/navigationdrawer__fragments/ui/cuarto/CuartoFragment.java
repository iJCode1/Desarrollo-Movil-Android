package com.example.navigationdrawer__fragments.ui.cuarto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigationdrawer__fragments.R;

public class CuartoFragment extends Fragment {

    private CuartoViewModel cuartoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cuartoViewModel =
                new ViewModelProvider(this).get(CuartoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cuarto, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        cuartoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}