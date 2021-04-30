package com.example.articulos.ui.buscar;

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

import com.example.articulos.BD.Products;
import com.example.articulos.BD.utilities;
import com.example.articulos.R;
import com.example.articulos.ui.altas.AltasViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BuscarFragment extends Fragment implements View.OnClickListener{

    private EditText etnombre, etcaducidad, etstock, etIDproducto;
    private Spinner sCategoria, sStatus;
    private ImageView ivpic;
    private Button btnCancelar, btnModificar;
    private ImageButton ibtncalendario, ibtnIDproducto;
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

    private Products originalP = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_buscar, container, false);

        utilities.initDataBase(getContext());
        initComponents();

        return root;
    }

    private void initComponents() {
        etIDproducto = root.findViewById(R.id.fb_idProducto);
        ibtnIDproducto = root.findViewById(R.id.fb_buscar);

        etnombre = root.findViewById(R.id.fb_nombre);
        etstock = root.findViewById(R.id.fb_stock);
        etcaducidad = root.findViewById(R.id.fb_caducidad);
        ivpic = root.findViewById(R.id.fb_imagen);
        btnCancelar = root.findViewById(R.id.fb_btnCancel);
        btnModificar = root.findViewById(R.id.fb_btnUpdate);
        ibtncalendario = root.findViewById(R.id.fb_calendario);
        sCategoria = root.findViewById(R.id.fb_categoria);
        sStatus = root.findViewById(R.id.fb_status);

        disabledComponents();

        CatAdapter = ArrayAdapter.createFromResource(getContext(),R.array.category,android.R.layout.simple_spinner_item);
        StatusAdapter = ArrayAdapter.createFromResource(getContext(),R.array.status,android.R.layout.simple_spinner_item);

        sCategoria.setAdapter(CatAdapter);
        sStatus.setAdapter(StatusAdapter);

        ibtncalendario.setOnClickListener((View.OnClickListener) this);
        ibtnIDproducto.setOnClickListener((View.OnClickListener) this);
        btnCancelar.setOnClickListener((View.OnClickListener) this);
        btnModificar.setOnClickListener((View.OnClickListener) this);
        ivpic.setOnClickListener((View.OnClickListener) this);
        ibtncalendario.setOnClickListener((View.OnClickListener) this);
    }

    private void clearFields(){
        etnombre.setText("");
        etcaducidad.setText("");
        etstock.setText("");
        ivpic.setImageResource(R.drawable.ic_menu_camera);
        currentPath = "";
    }

    private void showDatePicker(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //dpd = new DatePickerDialog(getContext(), this,year,month,day);
        dpd = new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener) this,year,month,day);
        dpd.show();
    }

    //@Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etcaducidad.setText(day+"/"+(month+1)+"/"+year);
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
                camera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(),"com.example.articulos", picture));
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

    private boolean validateFields(){
        if (etnombre.getText().toString().equals("") ||
                etcaducidad.getText().toString().equals("") ||
                etstock.getText().toString().equals("") ||
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

    private void disabledComponents(){
        etIDproducto.setEnabled(true);
        ibtnIDproducto.setEnabled(true);
        etstock.setEnabled(false);
        etnombre.setEnabled(false);
        ivpic.setEnabled(false);
        etcaducidad.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnModificar.setEnabled(false);
        ibtncalendario.setEnabled(false);
        sCategoria.setEnabled(false);
        sStatus.setEnabled(false);
    }

    private void enabledComponents(){
        etIDproducto.setEnabled(false);
        ibtnIDproducto.setEnabled(false);
        etstock.setEnabled(true);
        etnombre.setEnabled(true);
        ivpic.setEnabled(true);
        etcaducidad.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnModificar.setEnabled(true);
        ibtncalendario.setEnabled(true);
        sCategoria.setEnabled(true);
        sStatus.setEnabled(true);
    }

    private void showProduct(int id){
        originalP = utilities.searchProducts(id);
        if(originalP != null){
            etIDproducto.setText(String.valueOf(originalP.getId()));
            etstock.setText(String.valueOf(originalP.getStock()));
            etnombre.setText(originalP.getName());
            currentPath = originalP.getPic();
            setPicture(currentPath);
            setSpinner(originalP.getCategory(), originalP.getStatus());
            etcaducidad.setText(originalP.getDateDue());
            enabledComponents();
        }else{
            Toast.makeText(getContext(), "Ingrese ID valido", Toast.LENGTH_LONG).show();
        }
    }

    private void setPicture(String pic){
        try{
            File filephoto = new File(pic);
            Uri photoUri = FileProvider.getUriForFile(getContext(), "com.example.articulos", filephoto);
            ivpic.setImageURI(photoUri);
        }catch(Exception ex){
            Toast.makeText(getContext(), "Ocurrio un error en la carga de la imagen", Toast.LENGTH_LONG).show();
        }
    }

    private void setSpinner(String category, boolean status){
        for(int i = 0; i<sCategoria.getCount(); i++){
            if (sCategoria.getItemAtPosition(i).toString().equals(category)){
                sCategoria.setSelection(i);
            }
        }
        for(int i=0; i<sStatus.getCount(); i++){
            if(status){
                sStatus.setSelection(0);
            }else{
                sStatus.setSelection(1);
            }
        }
    }

    private boolean somethingChange(){
        int val1 = (originalP.getStatus()) ? 1:0;
        int val2 = (getStatus(sStatus.getSelectedItem().toString())) ? 1:0;

        if(!originalP.getName().equals(etnombre.getText().toString())||
                !originalP.getDateDue().equals(etcaducidad.getText().toString())||
                !originalP.getPic().equals(currentPath)||
                !originalP.getCategory().equals(sCategoria.getSelectedItem().toString())||
                !(val1 == val2)||
                !(originalP.getStock() == Integer.parseInt(etstock.getText().toString()))){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fb_buscar:
                if(etIDproducto.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingrese el ID", Toast.LENGTH_LONG).show();
                }else{
                    showProduct(Integer.parseInt(etIDproducto.getText().toString()));
                }
                break;
            case R.id.fb_btnCancel:
                clearFields();
                break;
            case R.id.fb_btnUpdate:
                if(!validateFields()){
                    Toast.makeText(getContext(), "Complete campos", Toast.LENGTH_LONG).show();
                }else{
                    if(somethingChange()){
                        try{
                            Products p = new Products(
                                    Integer.parseInt(etIDproducto.getText().toString()),
                                    Integer.parseInt(etstock.getText().toString()),
                                    etnombre.getText().toString(),currentPath,
                                    etcaducidad.getText().toString(),
                                    sCategoria.getSelectedItem().toString(),
                                    getStatus(sStatus.getSelectedItem().toString()));
                            if (utilities.updateProducts(p)){
                                Toast.makeText(getContext(),"Producto creado", Toast.LENGTH_LONG).show();
                                clearFields();
                                showProduct(Integer.parseInt(etIDproducto.getText().toString()));
                            }
                            utilities.closeDataBase();
                        }catch(Exception ex){
                            Toast.makeText(getContext(), "Campos no valdos", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Sin cambios",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.fb_imagen:
                takePicture();
                break;
            case R.id.fb_calendario:
                showDatePicker();
                break;
        }
    }
}