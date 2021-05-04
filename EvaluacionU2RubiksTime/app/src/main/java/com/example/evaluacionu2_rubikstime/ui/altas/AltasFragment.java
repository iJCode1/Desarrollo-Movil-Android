package com.example.evaluacionu2_rubikstime.ui.altas;

import androidx.core.content.FileProvider;
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

import com.example.evaluacionu2_rubikstime.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class AltasFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText etNombre, etDescription, etFecha, etTime;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;
    private Button btnAgregar, btnLimpiar;
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
        root = inflater.inflate(R.layout.fragment_altas, container, false);
        initComponents();
        return root;
    }

    private void initComponents() {
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
        day = calendar.get(Calendar.DAY_OF_YEAR);

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
        if (etNombre.getText().toString().equals("") ||
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
            case R.id.fa_btnClear:
                clearFields();
                break;
            case R.id.fa_btnAdd:
                if (!validateFields()){
                    Toast.makeText(getContext(),"Complete campos", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), etNombre.getText().toString(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),
                            "Nombre del Cubo: "+ etNombre.getText().toString() + "\n"+
                                    "Descripción del Solve: "+ etDescription.getText().toString() + "\n"+
                                    "Fecha: "+ etFecha.getText().toString() + "\n"+
                                    "URL Image: "+ currentPath + "\n"+
                                    "Categoria: "+ sCategoria.getSelectedItem().toString() + "\n"+
                                    "Status: " + getStatus(sStatus.getSelectedItem().toString()) + "\n"+
                                    "Tiempo: "+ etTime.getText().toString() + "\n",
                            Toast.LENGTH_SHORT).show();
                    Bundle datos = new Bundle();
                    datos.putString("name", etNombre.getText().toString().trim());
                    datos.putString("descripcion", etDescription.getText().toString().trim());
                    datos.putString("fecha", etFecha.getText().toString().trim());
                    datos.putString("categoria", sCategoria.getSelectedItem().toString().trim());
                    datos.putString("status", sStatus.getSelectedItem().toString().trim());
                    datos.putString("tiempo", etTime.getText().toString().trim());
                    datos.putSerializable("imagen", picture);
                    getParentFragmentManager().setFragmentResult("key", datos);

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
            case R.id.fa_calendario:
                showDatePicker();
                break;
            case R.id.fa_imagen:
                takePicture();
                break;
        }
    }
}