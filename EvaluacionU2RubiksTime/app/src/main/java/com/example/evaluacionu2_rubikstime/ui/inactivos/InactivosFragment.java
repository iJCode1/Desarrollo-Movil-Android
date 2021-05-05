package com.example.evaluacionu2_rubikstime.ui.inactivos;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import com.example.evaluacionu2_rubikstime.R;
import com.example.evaluacionu2_rubikstime.ui.altas.AltasViewModel;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class InactivosFragment extends Fragment implements View.OnClickListener{

    private EditText etId, etNombre, etDescription, etFecha, etTime;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;
    private Button btnActivar;
    private View root;

    Serializable imagen;

    private ArrayAdapter<CharSequence> CatAdapter, StatusAdapter;

    private String currentPath="";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_inactivos, container, false);
        initComponents();


        return root;
    }

    private void initComponents() {
        etId = root.findViewById(R.id.fina_id);
        etId.setEnabled(false);
        etNombre = root.findViewById(R.id.fina_nombre);
        etNombre.setEnabled(false);
        etDescription = root.findViewById(R.id.fina_description);
        etDescription.setEnabled(false);
        etFecha = root.findViewById(R.id.fina_fecha);
        etFecha.setEnabled(false);
        etTime = root.findViewById(R.id.fina_time);
        etTime.setEnabled(false);
        ivpic = root.findViewById(R.id.fina_imagen);
        btnActivar = root.findViewById(R.id.fina_btnActivar);
        sCategoria = root.findViewById(R.id.fina_cubos_categoria);
        sCategoria.setEnabled(false);
        sStatus = root.findViewById(R.id.fina_status);
        sStatus.setEnabled(false);

        CatAdapter = ArrayAdapter.createFromResource(getContext(),R.array.cubes_category,android.R.layout.simple_spinner_item);
        StatusAdapter = ArrayAdapter.createFromResource(getContext(),R.array.status,android.R.layout.simple_spinner_item);

        sCategoria.setAdapter(CatAdapter);
        sStatus.setAdapter(StatusAdapter);

        btnActivar.setOnClickListener((View.OnClickListener) this);

        leerDatos();
    }


    private void leerDatos(){
        getParentFragmentManager().setFragmentResultListener("keyModificada", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String statusA = result.getString("status");

                if(statusA.equals("Inactivo")) {
                    String id = result.getString("id");
                    String name = result.getString("name");
                    String descripcion = result.getString("descripcion");
                    String fecha = result.getString("fecha");
                    String categoria = result.getString("categoria");
                    String status = result.getString("status");
                    String tiempo = result.getString("tiempo");
                    String path = result.getString("path");
                     imagen = result.getSerializable("imagen");

                    ArrayList<String> aCategoria = new ArrayList<String>();
                    aCategoria.add(categoria);

                    ArrayList<String> aStatus = new ArrayList<String>();
                    aStatus.add(status);

                    etId.setText(id);
                    etNombre.setText(name);
                    etDescription.setText(descripcion);
                    etFecha.setText(fecha);
                    ivpic.setImageURI(FileProvider.getUriForFile(getContext(), "com.example.evaluacionu2_rubikstime", (File) imagen));
                    etTime.setText(tiempo);
                    currentPath = path;

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


            }
        });
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

    private boolean validateFields(){
        if (etId.getText().toString().equals("")||
                etNombre.getText().toString().equals("") ||
                etDescription.getText().toString().equals("") ||
                etFecha.getText().toString().equals("") ||
                etTime.getText().toString().equals("") ||
                currentPath.equals("")){
            return false;
        }
        return true;
    }


    public void onClick(View view){
        switch(view.getId()){
            case R.id.fina_btnActivar:
                if (!validateFields()){
                    Toast.makeText(getContext(),"Complete campos", Toast.LENGTH_LONG).show();
                }else{
                    /*Toast.makeText(getContext(),
                            "ID del Tiempo: "+etId.getText().toString()+"\n"+
                                    "Nombre del Cubo: "+ etNombre.getText().toString() + "\n"+
                                    "Descripción del Solve: "+ etDescription.getText().toString() + "\n"+
                                    "Fecha: "+ etFecha.getText().toString() + "\n"+
                                    "URL Image: "+ currentPath + "\n"+
                                    "Categoria: "+ sCategoria.getSelectedItem().toString() + "\n"+
                                    "Status: " + getStatus(sStatus.getSelectedItem().toString()) + "\n"+
                                    "Tiempo: "+ etTime.getText().toString() + "\n",
                            Toast.LENGTH_SHORT).show();*/
                    Toast.makeText(getContext(), "Activado", Toast.LENGTH_SHORT).show();

                    Bundle Activado = new Bundle();
                    Activado.putString("activado", "si");
                    Activado.putString("id", etId.getText().toString().trim());
                    Activado.putString("name", etNombre.getText().toString().trim());
                    Activado.putString("descripcion", etDescription.getText().toString().trim());
                    Activado.putString("fecha", etFecha.getText().toString().trim());
                    Activado.putString("categoria", sCategoria.getSelectedItem().toString().trim());
                    Activado.putString("status", sStatus.getSelectedItem().toString().trim());
                    Activado.putString("tiempo", etTime.getText().toString().trim());
                    Activado.putSerializable("imagen", imagen);
                    Activado.putString("path", currentPath);
                    getParentFragmentManager().setFragmentResult("keyActivada", Activado);
                    clearFields();
                   /* try{
                        Products p = new Products(
                                Integer.parseInt(etstock.getText().toString()),
                                etnombre.getText().toString(),currentPath,
                                etcaducidad.getText().toString(),
                                sCategoria.getSelectedItem().toString(),
                                getStatus(sStatus.getSelectedItem().toString()));
                        if (utilities.createProduct(p)){
                            Toast.makeText(getContext(),"Producto creado", Toast.LENGTH_LONG).show();
                            clearFields();
                        }
                        utilities.closeDataBase();
                    }catch(Exception e){
                        Toast.makeText(getContext(),"Campos no válidos", Toast.LENGTH_LONG).show();
                    }

                    */
                }
                break;
        }
    }

}