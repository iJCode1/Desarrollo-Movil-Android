package com.example.evaluaciont3_rubikstimefirebase.ui.buscar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaluaciont3_rubikstimefirebase.R;
import com.example.evaluaciont3_rubikstimefirebase.databinding.FragmentBuscarBinding;
import com.example.evaluaciont3_rubikstimefirebase.ui.altas.AltasViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Calendar;

public class BuscarFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private BuscarViewModel buscarViewModel;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        buscarViewModel =
                new ViewModelProvider(this).get(BuscarViewModel.class);

        root = inflater.inflate(R.layout.fragment_altas, container, false);
        initComponents();
        iniciarFirebase();
        return root;


    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Cubo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("path").getValue().toString();
                    etNombre.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {

    }
}