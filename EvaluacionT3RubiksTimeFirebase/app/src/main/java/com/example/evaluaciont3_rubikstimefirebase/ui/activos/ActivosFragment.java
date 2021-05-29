package com.example.evaluaciont3_rubikstimefirebase.ui.activos;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.evaluaciont3_rubikstimefirebase.R;
import com.example.evaluaciont3_rubikstimefirebase.dbfb.Cubo;
import com.example.evaluaciont3_rubikstimefirebase.ui.altas.AltasViewModel;
import com.example.evaluaciont3_rubikstimefirebase.ui.buscar.BuscarViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivosFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

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
    ListView lvCubos;

    private int idElemento;
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

    private List<Cubo> ListaCubo = new ArrayList<Cubo>();
    ArrayAdapter<Cubo> arrayAdapterCubo;
    Cubo cuboSelected;

    String idA;
    String uidA;
    String nombreA;
    String descripcionA;
    String fechaA;
    String tiempoA;
    String pathA;
    String categoriaA;
    String statusA;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(ActivosViewModel.class);



        root = inflater.inflate(R.layout.fragment_activos, container, false);
        initComponents();
        iniciarFirebase();
        listarDatos();
        lvCubos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cuboSelected = (Cubo) parent.getItemAtPosition(position);
                /*etNombre.setText(cuboSelected.getNombre());
                etDescription.setText(cuboSelected.getDescripcion());
                etFecha.setText(cuboSelected.getFecha());
                etTime.setText(cuboSelected.getTiempo().toString());*/

                //-------------------------
                try {
                    Cubo c = new Cubo();
                    idElemento = Integer.parseInt(String.valueOf(c.getUid()));
                }catch(NumberFormatException e){
                }
                Query consulta = FirebaseDatabase.getInstance().getReference().child("Cubo").orderByChild("uid").equalTo(cuboSelected.getUid());
                consulta.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                uidA = ds.getKey();
                                idA = ds.child("id").getValue().toString();
                                nombreA = ds.child("nombre").getValue().toString();
                                descripcionA = ds.child("descripcion").getValue().toString();
                                fechaA = ds.child("fecha").getValue().toString();
                                tiempoA = ds.child("tiempo").getValue().toString();
                                pathA = ds.child("path").getValue().toString();
                                categoriaA = ds.child("categoria").getValue().toString();
                                statusA = ds.child("status").getValue().toString();
                                /*etNombre.setText(nombre);
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
                                }*/
                            }
                        }else{
                            Toast.makeText(getContext(), "No hay elemento :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                //-------------------------

                AlertDialog dialogo = new AlertDialog
                        .Builder(getActivity())
                        /*.setNeutralButton("Baja Lógica (Status)", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*ArrayList<String> status2 = new ArrayList<String>();
                                status2.add("inactivo");
                                //ArrayAdapter<String> sp2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, status2);
                                //sp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //sStatus.setAdapter(sp2);
                                sStatus.setSelection(1);
                            }
                        })*/
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Cubo c = new Cubo();
                                Query eliminar = FirebaseDatabase.getInstance().getReference().child("Cubo").orderByChild("uid").equalTo(cuboSelected.getUid());
                                eliminar.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                                c.setUid(ds.child("uid").getValue().toString());
                                                databaseReference.child("Cubo").child(c.getUid()).removeValue();
                                                Toast.makeText(getContext(), "Modificado Correctamente", Toast.LENGTH_LONG).show();
                                                //clearFields();

                                            }
                                        }else{
                                            Toast.makeText(getContext(), "El ID Ingresado no existe", Toast.LENGTH_SHORT).show();
                                            //clearFields();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });

                                //clearFields();
                            }
                        })
                        .setNegativeButton("Aceptar Información", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getContext(), "Que bueno que no decidiste eliminar los datos", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Acciones") // El título
                        .setMessage("¿Deseas eliminar o aceptar la información?"+"\n"+
                                "UID: "+uidA+"\n"+
                                "ID: "+idA+"\n"+
                                "Nombre del Cubo: "+nombreA+"\n"+
                                "Descripcion del solve: "+descripcionA+"\n"+
                                "Fecha: "+fechaA+"\n"+
                                "Tiempo: "+tiempoA+"\n"+
                                "Categoria: "+categoriaA+"\n"+
                                "Path: "+pathA+"\n"+
                                "Status: "+statusA) // El mensaje
                        .create();

                dialogo.show();
            }
        });

        return root;
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarDatos() {

        Query activos = FirebaseDatabase.getInstance().getReference().child("Cubo").orderByChild("status").equalTo("Activo");
        activos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ListaCubo.clear();
                    for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                        Cubo c = objSnapshot.getValue(Cubo.class);
                        ListaCubo.add(c);
                        arrayAdapterCubo = new ArrayAdapter<Cubo>(getContext(), android.R.layout.simple_list_item_1, ListaCubo);
                        lvCubos.setAdapter(arrayAdapterCubo);
                    }

                }else{
                    Toast.makeText(getContext(), "Ingrese un ID Valido :(", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        /*
        databaseReference.child("Cubo").addValueEventListener(new ValueEventListener() {
            @Override
            //@NonNull @org.jetbrains.annotations.NotNull
            public void onDataChange(DataSnapshot snapshot) {
                ListaCubo.clear();
                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    Cubo c = objSnapshot.getValue(Cubo.class);
                    ListaCubo.add(c);
                    arrayAdapterCubo = new ArrayAdapter<Cubo>(getContext(), android.R.layout.simple_list_item_1, ListaCubo);
                    lvCubos.setAdapter(arrayAdapterCubo);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });*/
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
        lvCubos = root.findViewById(R.id.lvListaElementos);

        /*CatAdapter = ArrayAdapter.createFromResource(getContext(),R.array.cubes_category,android.R.layout.simple_spinner_item);
        StatusAdapter = ArrayAdapter.createFromResource(getContext(),R.array.status,android.R.layout.simple_spinner_item);

        sCategoria.setAdapter(CatAdapter);
        sStatus.setAdapter(StatusAdapter);
        */
        /*btnLimpiar.setOnClickListener((View.OnClickListener) this);
        btnAgregar.setOnClickListener((View.OnClickListener) this);
        ivpic.setOnClickListener((View.OnClickListener) this);
        ibtnCalendario.setOnClickListener((View.OnClickListener) this);*/
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {

    }
}