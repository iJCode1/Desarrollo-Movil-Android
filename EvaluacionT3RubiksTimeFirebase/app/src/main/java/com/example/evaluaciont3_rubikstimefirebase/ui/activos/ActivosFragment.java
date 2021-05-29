package com.example.evaluaciont3_rubikstimefirebase.ui.activos;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.evaluaciont3_rubikstimefirebase.R;
import com.example.evaluaciont3_rubikstimefirebase.ui.altas.AltasViewModel;
import com.example.evaluaciont3_rubikstimefirebase.ui.buscar.BuscarViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.Calendar;

public class ActivosFragment extends Fragment {

    private ActivosViewModel mViewModel;
    private View root;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private AltasViewModel altasViewModel;
    private EditText etId, etNombre, etDescription, etFecha, etTime;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;
    private Button btnAgregar, btnLimpiar;
    private ImageButton ibtnCalendario;

    public static final int REQUEST_TAKE_PHOTO = 1;

    private static int year, month, day;
    private DatePickerDialog dpd;
    private Calendar calendar;



    private ArrayAdapter<CharSequence> CatAdapter, StatusAdapter;

    private Intent camera;
    private File picture, storageDir;
    private String timeStamp="";
    private String pictureName="";
    private String currentPath="";

    private boolean status;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(ActivosViewModel.class);

        root = inflater.inflate(R.layout.fragment_altas, container, false);
        initComponents();
        iniciarFirebase();
        return root;
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void initComponents() {
        etId = root.findViewById(R.id.fa_id);
        etNombre = root.findViewById(R.id.fa_nombre);
        etDescription = root.findViewById(R.id.fa_description);
        etFecha = root.findViewById(R.id.fa_fecha);
        etTime = root.findViewById(R.id.fa_time);
        ivpic = root.findViewById(R.id.fa_imagen);
        btnLimpiar = root.findViewById(R.id.fa_btnClear);
        btnAgregar = root.findViewById(R.id.fa_btnAdd);
        ibtnCalendario = root.findViewById(R.id.fa_calendario);
        sCategoria = root.findViewById(R.id.fa_cubos_categoria);
        sStatus = root.findViewById(R.id.fa_status);

        CatAdapter = ArrayAdapter.createFromResource(getContext(),R.array.cubes_category,android.R.layout.simple_spinner_item);
        StatusAdapter = ArrayAdapter.createFromResource(getContext(),R.array.status,android.R.layout.simple_spinner_item);

        sCategoria.setAdapter(CatAdapter);
        sStatus.setAdapter(StatusAdapter);

        btnLimpiar.setOnClickListener((View.OnClickListener) this);
        btnAgregar.setOnClickListener((View.OnClickListener) this);
        ivpic.setOnClickListener((View.OnClickListener) this);
        ibtnCalendario.setOnClickListener((View.OnClickListener) this);
    }

}