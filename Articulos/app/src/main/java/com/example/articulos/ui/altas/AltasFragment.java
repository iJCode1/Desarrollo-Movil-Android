package com.example.articulos.ui.altas;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.example.articulos.BD.Productos;
import com.example.articulos.BD.utilities;
import com.example.articulos.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class AltasFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

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

    private Intent camera;

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

        btnLimpiar.setOnClickListener((View.OnClickListener) this);
        btnAgregar.setOnClickListener((View.OnClickListener) this);
        ivPicture.setOnClickListener((View.OnClickListener) this);
        btnCalendario.setOnClickListener((View.OnClickListener) this);
    }

    private void clearFields(){
        etNombre.setText("");
        etCaducidad.setText("");
        etStock.setText("");
        ivPicture.setImageResource(R.drawable.camera);
        currentPath = "";
    }

    private void showDatePicker(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dpd = new DatePickerDialog(getContext(), this, year, month, day);

        dpd.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etCaducidad.setText(day + "/" +month+"/"+year);
    }

    private void takePicture(){
        camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camera.resolveActivity(getActivity().getPackageManager())!= null){
            picture = null;
            try {
                picture = createPicture();
            }catch (Exception e){
                Toast.makeText(getContext(), "Hubo un error al generar la foto", Toast.LENGTH_LONG).show();
            }
            if(picture != null){
                camera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(), "com.example.articulos", picture));
                startActivityForResult(camera, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createPicture() throws IOException {
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        pictureName = "JPG" + timeStamp + "_";
        storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(pictureName, ".jpg", storageDir);
        currentPath = image.getAbsolutePath();

        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            ivPicture.setImageURI(FileProvider.getUriForFile(getContext(), "com.exampple.articulos", picture));
        }
    }

    private boolean validarFields(){
        if(etNombre.getText().toString().equals("") ||
        etCaducidad.getText().toString().equals("") ||
        etStock.getText().toString().equals("") ||
        currentPath.equals("")){
            return false;
        }
        return true;
    }

    private boolean getStatus(String toString){
        if(spStatus.getSelectedItem().toString().equals("Activo")){
            return true;
        }
        return false;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.fa_btnClear:
                clearFields();
                break;
            case R.id.fa_calendario:
                showDatePicker();
                break;
            case R.id.fa_imagen:
                takePicture();
                break;
            case R.id.fa_btnAdd:
                if(!validarFields()){
                    Toast.makeText(getContext(), "Completa los campos por favor", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        Productos p = new Productos(
                                Integer.parseInt(etStock.getText().toString()), etNombre.getText().toString(), currentPath, etCaducidad.getText().toString(), spCategoria.getSelectedItem().toString(), getStatus(spStatus.getSelectedItem().toString()));

                        if(utilities.createProduct(p)){
                            Toast.makeText(getContext(), "Producto creado", Toast.LENGTH_LONG).show();
                            clearFields();
                        }
                    }catch(Exception e){
                        Toast.makeText(getContext(), "Campos no validos", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}