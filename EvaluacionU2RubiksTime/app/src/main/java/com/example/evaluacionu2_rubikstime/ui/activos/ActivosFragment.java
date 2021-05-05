package com.example.evaluacionu2_rubikstime.ui.activos;

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

public class ActivosFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{


    private EditText etId, etNombre, etDescription, etFecha, etTime;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;
    private Button btnEditar, btnEliminar;
    private ImageButton ibtnCalendario;
    private View root;

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

    private AltasViewModel mViewModel;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_activos, container, false);
        initComponents();
        leerDatos();

        return root;
    }

    private void initComponents() {
        etId = root.findViewById(R.id.fact_id);
        etNombre = root.findViewById(R.id.fact_nombre);
        etDescription = root.findViewById(R.id.fact_description);
        etFecha = root.findViewById(R.id.fact_fecha);
        etTime = root.findViewById(R.id.fact_time);
        ivpic = root.findViewById(R.id.fact_imagen);
        btnEditar = root.findViewById(R.id.fact_btnEdit);
        btnEliminar = root.findViewById(R.id.fact_btnDelete);
        ibtnCalendario = root.findViewById(R.id.fact_calendario);
        sCategoria = root.findViewById(R.id.fact_cubos_categoria);
        sStatus = root.findViewById(R.id.fact_status);

        CatAdapter = ArrayAdapter.createFromResource(getContext(),R.array.cubes_category,android.R.layout.simple_spinner_item);
        StatusAdapter = ArrayAdapter.createFromResource(getContext(),R.array.status,android.R.layout.simple_spinner_item);

        sCategoria.setAdapter(CatAdapter);
        sStatus.setAdapter(StatusAdapter);

        btnEditar.setOnClickListener((View.OnClickListener) this);
        btnEliminar.setOnClickListener((View.OnClickListener) this);
        ivpic.setOnClickListener((View.OnClickListener) this);
        ibtnCalendario.setOnClickListener((View.OnClickListener) this);
    }


    private void leerDatos(){
        getParentFragmentManager().setFragmentResultListener("keyModificada", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String modificado = result.getString("modificado");

                if(modificado.equals("si")) {
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
                etTime.getText().toString().equals("") ||
                currentPath.equals("")){
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
            case R.id.fact_btnDelete:
                deleteFields();
                break;
            case R.id.fact_btnEdit:
                if (!validateFields()){
                    Toast.makeText(getContext(),"Complete campos", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(), etNombre.getText().toString(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),
                            "ID del Tiempo: "+etId.getText().toString()+"\n"+
                                    "Nombre del Cubo: "+ etNombre.getText().toString() + "\n"+
                                    "Descripción del Solve: "+ etDescription.getText().toString() + "\n"+
                                    "Fecha: "+ etFecha.getText().toString() + "\n"+
                                    "URL Image: "+ currentPath + "\n"+
                                    "Categoria: "+ sCategoria.getSelectedItem().toString() + "\n"+
                                    "Status: " + getStatus(sStatus.getSelectedItem().toString()) + "\n"+
                                    "Tiempo: "+ etTime.getText().toString() + "\n",
                            Toast.LENGTH_SHORT).show();
                    Bundle editadoActivo = new Bundle();
                    editadoActivo.putString("editado", "si");
                    editadoActivo.putString("id", etId.getText().toString().trim());
                    editadoActivo.putString("name", etNombre.getText().toString().trim());
                    editadoActivo.putString("descripcion", etDescription.getText().toString().trim());
                    editadoActivo.putString("fecha", etFecha.getText().toString().trim());
                    editadoActivo.putString("categoria", sCategoria.getSelectedItem().toString().trim());
                    editadoActivo.putString("status", sStatus.getSelectedItem().toString().trim());
                    editadoActivo.putString("tiempo", etTime.getText().toString().trim());
                    editadoActivo.putSerializable("imagen", picture);
                    editadoActivo.putString("path", currentPath);
                    getParentFragmentManager().setFragmentResult("keyEditada", editadoActivo);

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
            case R.id.fact_calendario:
                showDatePicker();
                break;
            case R.id.fact_imagen:
                takePicture();
                break;
        }
    }

}