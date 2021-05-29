package com.example.evaluaciont3_rubikstimefirebase.ui.buscar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaluaciont3_rubikstimefirebase.R;
import com.example.evaluaciont3_rubikstimefirebase.databinding.FragmentBuscarBinding;
import com.example.evaluaciont3_rubikstimefirebase.dbfb.Cubo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class BuscarFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private BuscarViewModel buscarViewModel;
    private View root;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private EditText etId, etNombre, etDescription, etFecha, etTime;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;
    private Button btnModificar, btnEliminar;
    private ImageButton ibtnCalendario, btnBuscar;

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

    private int id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        buscarViewModel =
                new ViewModelProvider(this).get(BuscarViewModel.class);

        root = inflater.inflate(R.layout.fragment_buscar, container, false);
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
        etId = root.findViewById(R.id.fb_idTiempo);
        //etId.setEnabled(false);
        etNombre = root.findViewById(R.id.fb_nombre);
        etDescription = root.findViewById(R.id.fb_description);
        etFecha = root.findViewById(R.id.fb_fecha);
        etTime = root.findViewById(R.id.fb_time);
        ivpic = root.findViewById(R.id.fb_imagen);
        btnEliminar = root.findViewById(R.id.fb_btnEliminar);
        btnModificar = root.findViewById(R.id.fb_btnModificar);
        btnBuscar = root.findViewById(R.id.fb_btnBuscar);
        ibtnCalendario = root.findViewById(R.id.fb_calendario);
        sCategoria = root.findViewById(R.id.fb_cubos_categoria);
        sStatus = root.findViewById(R.id.fb_status);

        CatAdapter = ArrayAdapter.createFromResource(getContext(),R.array.cubes_category,android.R.layout.simple_spinner_item);
        StatusAdapter = ArrayAdapter.createFromResource(getContext(),R.array.status,android.R.layout.simple_spinner_item);

        sCategoria.setAdapter(CatAdapter);
        sStatus.setAdapter(StatusAdapter);

        btnModificar.setOnClickListener((View.OnClickListener) this);
        btnEliminar.setOnClickListener((View.OnClickListener) this);
        btnBuscar.setOnClickListener((View.OnClickListener) this);
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
        if (etId.getText().toString().equals("")||
                etNombre.getText().toString().equals("") ||
                etDescription.getText().toString().equals("") ||
                etFecha.getText().toString().equals("") ||
                etTime.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private boolean getStatus(String toString){
        if (sStatus.getSelectedItem().toString().equals("Activo")){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        try {
            id = Integer.parseInt(etId.getText().toString());
        }catch(NumberFormatException e){
        }
        switch (v.getId()) {
            case R.id.fb_btnBuscar:

                Query consulta = FirebaseDatabase.getInstance().getReference().child("Cubo").orderByChild("id").equalTo(id);
                consulta.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                String id = ds.getKey();
                                String nombre = ds.child("nombre").getValue().toString();
                                String descripcion = ds.child("descripcion").getValue().toString();
                                String fecha = ds.child("fecha").getValue().toString();
                                String tiempo = ds.child("tiempo").getValue().toString();
                                String path = ds.child("path").getValue().toString();
                                String categoria = ds.child("categoria").getValue().toString();
                                String status = ds.child("status").getValue().toString();
                                etNombre.setText(nombre);
                                etDescription.setText(descripcion);
                                etFecha.setText(fecha);
                                etTime.setText(tiempo);
                                ivpic.setImageURI(Uri.parse(path));

                                for(int i = 0; i<sCategoria.getCount(); i++){
                                    if(sCategoria.getItemAtPosition(i).toString().equalsIgnoreCase(categoria)){
                                        sCategoria.setSelection(i);
                                    }
                                }

                                for(int i = 0; i<sStatus.getCount(); i++){
                                    if(sStatus.getItemAtPosition(i).toString().equalsIgnoreCase(status)){
                                        sStatus.setSelection(i);
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(getContext(), "Ingrese un ID Valido :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                break;
            case R.id.fb_btnEliminar:
                Toast.makeText(getContext(), "Eliminando!!!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fb_btnModificar:
                if (!validateFields()) {
                    Toast.makeText(getContext(), "Complete campos", Toast.LENGTH_LONG).show();
                } else {
                    Cubo c = new Cubo();
                    Query modificar = FirebaseDatabase.getInstance().getReference().child("Cubo").orderByChild("id").equalTo(id);
                    modificar.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for(DataSnapshot ds: dataSnapshot.getChildren()){
                                    c.setUid(ds.child("uid").getValue().toString());
                                    c.setId(Integer.parseInt(etId.getText().toString()));
                                    c.setNombre(etNombre.getText().toString().trim());
                                    c.setDescripcion(etDescription.getText().toString().trim());
                                    c.setFecha(etFecha.getText().toString().trim());
                                    c.setTiempo(Float.parseFloat(etTime.getText().toString().trim()));
                                    if(currentPath.equals("")){
                                        c.setPath(ds.child("path").getValue().toString());
                                    }else{
                                        c.setPath(currentPath);
                                    }
                                    c.setCategoria(sCategoria.getSelectedItem().toString());
                                    c.setStatus(sStatus.getSelectedItem().toString());
                                    databaseReference.child("Cubo").child(c.getUid()).setValue(c);
                                    Toast.makeText(getContext(), "Modificado Correctamente", Toast.LENGTH_LONG).show();
                                    clearFields();

                                }
                            }else{
                                Toast.makeText(getContext(), "El ID Ingresado no existe", Toast.LENGTH_SHORT).show();
                                clearFields();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
                break;
            case R.id.fb_calendario:
                showDatePicker();
                break;
            case R.id.fb_imagen:
                takePicture();
                break;
        }
    }
}