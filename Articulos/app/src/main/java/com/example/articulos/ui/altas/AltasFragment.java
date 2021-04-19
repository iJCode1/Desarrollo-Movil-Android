package com.example.articulos.ui.altas;

import android.app.DatePickerDialog;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.articulos.R;

import java.io.File;
import java.util.Calendar;

public class AltasFragment extends Fragment {

    private AltasViewModel altasViewModel;
    private EditText etNombre, etCaducidad, etStock;
    private Spinner spCategoria, spStatus;
    private ImageView ivPicture;
    private Button btnLimpiar, btnAgregar;
    private ImageButton btnCalendario;
    private View root;

    public static final int REQUEST_TAKE_PHOTO = 1;

    private static int year, month, day;
    private DatePickerDialog dpd;
    private Calendar calendar;

    private ArrayAdapter<CharSequence> catAdapter, statusAdapter;

    private Integer camera;

    private File picture, storageDir;

    private String timeStamp = "";
    private String pictureName = "";
    private String currentPath = "";

    private boolean status;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_altas, container, false);
        initComponents();

        return root;
    }

    private void initComponents() {

        etNombre = root.findViewById(R.id.fa_nombre);
        etCaducidad = root.findViewById(R.id.fa_caducidad);
        etStock = root.findViewById(R.id.fa_stock);
        spCategoria = root.findViewById(R.id.fa_categoria);
        spStatus = root.findViewById(R.id.fa_status);
        ivPicture = root.findViewById(R.id.fa_imagen);
        btnLimpiar = root.findViewById(R.id.fa_btnClear);
        btnAgregar = root.findViewById(R.id.fa_btnAdd);
        btnCalendario = root.findViewById(R.id.fa_calendario);

        catAdapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_item);
        statusAdapter = ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_item);

        spCategoria.setAdapter(catAdapter);
        spStatus.setAdapter(statusAdapter);
    }
}