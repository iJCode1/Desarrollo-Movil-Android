package com.example.evaluacionu2_rubikstime.ui.buscar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaluacionu2_rubikstime.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;

import static android.app.Activity.RESULT_OK;

public class BuscarFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private EditText etId, etNombre, etDescription, etFecha, etTime;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;
    private Button btnModificar, btnEliminar;
    private ImageButton ibtnCalendario;
    private View root;

    public static final int REQUEST_TAKE_PHOTO = 1;
    private static int year, month, day;
    private DatePickerDialog dpd;
    private Calendar calendar;

    private ArrayAdapter<String> CatAdapter, StatusAdapter;

    private Intent camera;
    private File picture, storageDir;
    private String timeStamp="";
    private String pictureName="";
    private String currentPath="";

    private boolean status;

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
        etId.setEnabled(false);
        etNombre = root.findViewById(R.id.fb_nombre);
        etDescription = root.findViewById(R.id.fb_description);
        etFecha = root.findViewById(R.id.fb_fecha);
        etTime = root.findViewById(R.id.fb_time);
        ivpic = root.findViewById(R.id.fb_imagen);
        btnEliminar = root.findViewById(R.id.fb_btnEliminar);
        btnModificar = root.findViewById(R.id.fb_btnModificar);
        ibtnCalendario = root.findViewById(R.id.fb_calendario);
        sCategoria = root.findViewById(R.id.fb_cubos_categoria);
        sStatus = root.findViewById(R.id.fb_status);



        btnModificar.setOnClickListener((View.OnClickListener) this);
        btnEliminar.setOnClickListener((View.OnClickListener) this);
        ivpic.setOnClickListener((View.OnClickListener) this);
        ibtnCalendario.setOnClickListener((View.OnClickListener) this);
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
                String path = result.getString("path");
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
                currentPath = path;


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

    private void deleteFields(){
        AlertDialog dialogo = new AlertDialog
                .Builder(getActivity())
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etId.setText("");
                        etNombre.setText("");
                        etDescription.setText("");
                        etFecha.setText("");
                        etTime.setText("");
                        ivpic.setImageResource(R.drawable.ifhome);
                        currentPath = "";
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Que bueno que no decidiste eliminar los datos", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setTitle("Confirmar") // El título
                .setMessage("¿Deseas eliminar los Datos?"+"\n"+
                        "ID: "+etId.getText()+"\n"+
                        "Nombre del Cubo: "+etNombre.getText().toString()+"\n"+
                        "Descripcion del solve: "+etDescription.getText().toString()+"\n"+
                        "Fecha: "+etFecha.getText().toString()+"\n"+
                        "Tiempo: "+etTime.getText().toString()+"\n"+
                        "Categoria: "+sCategoria.getSelectedItem().toString()+"\n"+
                        "Status: "+sStatus.getSelectedItem().toString()) // El mensaje
                .create();

        dialogo.show();

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
                camera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(),"com.example.evaluacionu2_rubikstime", picture));
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
            ivpic.setImageURI(FileProvider.getUriForFile(getContext(),"com.example.evaluacionu2_rubikstime", picture));
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

    public void onClick(View view){
        switch(view.getId()){
            case R.id.fb_btnEliminar:
                deleteFields();
                break;
            case R.id.fb_btnModificar:
                if (!validateFields()){
                    Toast.makeText(getContext(),"Complete campos", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(), etNombre.getText().toString(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),
                            "ID del Tiempo: "+etId.getText().toString()+"\n"+
                                    "Nombre del Cubo: "+ etNombre.getText().toString() + "\n"+
                                    "Descripción del Solve: "+ etDescription.getText().toString() + "\n"+
                                    "Fecha: "+ etFecha.getText().toString() + "\n"+
                                    "Categoria: "+ sCategoria.getSelectedItem().toString() + "\n"+
                                    "Status: " + getStatus(sStatus.getSelectedItem().toString()) + "\n"+
                                    "Tiempo: "+ etTime.getText().toString() + "\n"+
                                    "Path: "+ currentPath,
                            Toast.LENGTH_SHORT).show();

                    Bundle datosModificados = new Bundle();
                    datosModificados.putString("modificado", "si");
                    datosModificados.putString("id", etId.getText().toString().trim());
                    datosModificados.putString("name", etNombre.getText().toString().trim());
                    datosModificados.putString("descripcion", etDescription.getText().toString().trim());
                    datosModificados.putString("fecha", etFecha.getText().toString().trim());
                    datosModificados.putString("categoria", sCategoria.getSelectedItem().toString().trim());
                    datosModificados.putString("status", sStatus.getSelectedItem().toString().trim());
                    datosModificados.putString("tiempo", etTime.getText().toString().trim());
                    datosModificados.putSerializable("imagen", picture);
                    datosModificados.putString("path", currentPath);
                    getParentFragmentManager().setFragmentResult("keyModificada", datosModificados);

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
            case R.id.fb_calendario:
                showDatePicker();
                break;
            case R.id.fb_imagen:
                takePicture();
                break;
        }
    }




}