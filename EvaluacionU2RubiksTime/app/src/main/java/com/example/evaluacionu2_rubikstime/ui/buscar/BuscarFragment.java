package com.example.evaluacionu2_rubikstime.ui.buscar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaluacionu2_rubikstime.R;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

public class BuscarFragment extends Fragment {

    private EditText etNombre, etDescription, etFecha, etTime;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;


    private boolean status;
    private View root;

    private BuscarViewModel buscarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_buscar, container, false);
        initComponents();
        leerDatos();
        return root;
    }

    private void initComponents() {
        etNombre = root.findViewById(R.id.fb_nombre);
        etDescription = root.findViewById(R.id.fb_description);
        etFecha = root.findViewById(R.id.fb_fecha);
        etTime = root.findViewById(R.id.fb_time);
        sCategoria = root.findViewById(R.id.fb_cubos_categoria);
        sStatus = root.findViewById(R.id.fb_status);
        ivpic = root.findViewById(R.id.fb_imagen);

    }

    private void leerDatos(){
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String name = result.getString("name");
                String descripcion = result.getString("descripcion");
                String fecha = result.getString("fecha");
                String categoria = result.getString("categoria");
                String status = result.getString("status");
                String tiempo = result.getString("tiempo");
                Serializable imagen = result.getSerializable("imagen");

                etNombre.setText(name);
                etDescription.setText(descripcion);
                etFecha.setText(fecha);
                ivpic.setImageURI(FileProvider.getUriForFile(getContext(),"com.example.evaluacionu2_rubikstime", (File) imagen));
                etTime.setText(tiempo);
            }
        });
    }


}