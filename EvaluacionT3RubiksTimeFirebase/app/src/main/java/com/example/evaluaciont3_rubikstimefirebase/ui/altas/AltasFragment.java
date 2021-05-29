package com.example.evaluaciont3_rubikstimefirebase.ui.altas;



import com.example.evaluaciont3_rubikstimefirebase.R;
import com.example.evaluaciont3_rubikstimefirebase.dbfb.Cubo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

import static android.app.Activity.RESULT_OK;

public class AltasFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

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


    private View root;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        altasViewModel =
                new ViewModelProvider(this).get(AltasViewModel.class);

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

    private void clearFields(){
        etId.setText("");
        etNombre.setText("");
        etDescription.setText("");
        etFecha.setText("");
        etTime.setText("");
        ivpic.setImageResource(R.drawable.icamera);
        currentPath = "";
    }

    private void showDatePicker(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dpd = new DatePickerDialog(getContext(),this,year,month,day);
        dpd.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
    }

    private void takePicture(){
        camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(getActivity().getPackageManager()) != null){
            picture = null;
            try {
                picture = createPicture();
            }catch(Exception e){
                Toast.makeText(getContext(),"Error al generar la foto", Toast.LENGTH_LONG).show();
            }

            if (picture != null){
                camera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(),"com.example.evaluaciont3_rubikstimefirebase", picture));
                startActivityForResult(camera,REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createPicture()throws IOException {
        timeStamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        pictureName = "JPEG"+timeStamp+"_";
        storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image= File.createTempFile(pictureName, ".jpg",storageDir);
        currentPath = image.getAbsolutePath();
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            ivpic.setImageURI(FileProvider.getUriForFile(getContext(),"com.example.evaluaciont3_rubikstimefirebase", picture));
        }
    }

    private boolean validateFields(){
        try {
            int id = Integer.parseInt(etId.getText().toString());
            String name = etNombre.getText().toString();
            String descripcion = etDescription.getText().toString();
            String fecha = etFecha.getText().toString();
            float tiempo = Float.parseFloat(etTime.getText().toString());
            String categoria = sCategoria.getSelectedItem().toString();
            String status = sStatus.getSelectedItem().toString();

            if (String.valueOf(id).equals("") ||
                    name.equals("") ||
                    descripcion.equals("") ||
                    fecha.equals("") ||
                    String.valueOf(tiempo).equals("") ||
                    currentPath.equals("")){
                return false;
            }
            return true;
        }catch(NumberFormatException e){
            //Toast.makeText(getContext(),"El ID es muy importante!!!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean getStatus(String toString){
        if (sStatus.getSelectedItem().toString().equals("Activo")){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fa_btnClear:
                clearFields();
                break;
            case R.id.fa_btnAdd:
                if (!validateFields()){
                    Toast.makeText(getContext(),"Complete campos", Toast.LENGTH_LONG).show();
                }else {
                    Cubo c = new Cubo();
                    c.setId(Integer.parseInt(etId.getText().toString()));
                    c.setUid(UUID.randomUUID().toString());
                    c.setNombre(etNombre.getText().toString());
                    c.setDescripcion(etDescription.getText().toString());
                    c.setFecha(etFecha.getText().toString());
                    c.setTiempo(Float.parseFloat(etTime.getText().toString()));
                    c.setPath(currentPath);
                    c.setCategoria(sCategoria.getSelectedItem().toString());
                    c.setStatus(sStatus.getSelectedItem().toString());
                    //c.setImage(picture);
                    databaseReference.child("Cubo").child(c.getUid()).setValue(c);
                    Toast.makeText(getContext(), "Agregado!", Toast.LENGTH_SHORT).show();
                    clearFields();
                }
                break;
            case R.id.fa_calendario:
                showDatePicker();
                break;
            case R.id.fa_imagen:
                takePicture();
                break;
        }
    }
}