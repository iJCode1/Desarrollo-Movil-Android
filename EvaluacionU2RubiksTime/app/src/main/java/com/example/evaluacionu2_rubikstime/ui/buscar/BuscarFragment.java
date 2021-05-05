package com.example.evaluacionu2_rubikstime.ui.buscar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.Calendar;

public class BuscarFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText etId, etNombre, etDescription, etFecha, etTime;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;

    private ArrayAdapter<String> CatAdapter, StatusAdapter;


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
        etId = root.findViewById(R.id.fb_idTiempo);
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
                String id = result.getString("id");
                String name = result.getString("name");
                String descripcion = result.getString("descripcion");
                String fecha = result.getString("fecha");
                String categoria = result.getString("categoria");
                String status = result.getString("status");
                String tiempo = result.getString("tiempo");
                Serializable imagen = result.getSerializable("imagen");

                ArrayList<String> aCategoria = new ArrayList<String>();
                aCategoria.add(categoria);

                ArrayList<String> aStatus = new ArrayList<String>();
                aStatus.add(status);



                etId.setText(id);
                etNombre.setText(name);
                etDescription.setText(descripcion);
                etFecha.setText(fecha);
                ivpic.setImageURI(FileProvider.getUriForFile(getContext(),"com.example.evaluacionu2_rubikstime", (File) imagen));
                etTime.setText(tiempo);


                //sCategoria.setOnItemClickListener(this());


                ArrayAdapter<String> spCategoria = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, aCategoria);
                spCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sCategoria.setAdapter(spCategoria);

                ArrayAdapter<String> spStatus = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, aStatus);
                spStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sStatus.setAdapter(spStatus);

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}