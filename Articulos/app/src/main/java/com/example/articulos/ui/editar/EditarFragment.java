package com.example.articulos.ui.editar;

import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.example.articulos.BD.Products;
import com.example.articulos.BD.utilities;
import com.example.articulos.R;

import java.io.File;
import java.util.Calendar;

public class EditarFragment extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    private EditText etNombre, etCaducidad, etStock, etIDproducto;
    private Spinner spCategoria, spStatus;
    private ImageView ivPicture;
    private Button btnCancelar, btnActualizar;
    private ImageButton btnCalendario, btnIDProducto;
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

    private Products originalP = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editar_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(EditarViewModel.class);
        // TODO: Use the ViewModel
    }


    private void initComponents() {

        etIDproducto = root.findViewById(R.id.fe_idProducto);
        btnIDProducto = root.findViewById(R.id.fe_btnBuscar);

        etNombre = root.findViewById(R.id.fe_nombre);
        etCaducidad = root.findViewById(R.id.fe_caducidad);
        etStock = root.findViewById(R.id.fe_stock);
        spCategoria = root.findViewById(R.id.fe_categoria);
        spStatus = root.findViewById(R.id.fe_status);
        ivPicture = root.findViewById(R.id.fe_imagen);
        btnCancelar = root.findViewById(R.id.fe_btnCancel);
        btnActualizar = root.findViewById(R.id.fb_btnUpdate);
        btnCalendario = root.findViewById(R.id.fe_calendario);

        catAdapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_item);
        statusAdapter = ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_item);

        spCategoria.setAdapter(catAdapter);
        spStatus.setAdapter(statusAdapter);

        btnCancelar.setOnClickListener(this);
        btnActualizar.setOnClickListener(this);
        ivPicture.setOnClickListener(this);
        btnCalendario.setOnClickListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {

    }

    private void clearFields(){
        etNombre.setText("");
        etCaducidad.setText("");
        etStock.setText("");
        ivPicture.setImageResource(R.drawable.camera);
        currentPath = "";
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

    private void disabledComponents(){
        etIDproducto.setEnabled(false);
        btnIDProducto.setEnabled(false);
        etNombre.setEnabled(false);
        etCaducidad.setEnabled(false);
        etStock.setEnabled(false);
        spCategoria.setEnabled(false);
        spStatus.setEnabled(false);
        ivPicture.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCalendario.setEnabled(false);
    }

    private void enabledComponents(){
        etIDproducto.setEnabled(true);
        btnIDProducto.setEnabled(true);
        etNombre.setEnabled(true);
        etCaducidad.setEnabled(true);
        etStock.setEnabled(true);
        spCategoria.setEnabled(true);
        spStatus.setEnabled(true);
        ivPicture.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnActualizar.setEnabled(true);
        btnCalendario.setEnabled(true);
    }

    private void showDatePicker(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dpd = new DatePickerDialog(getContext(), this, year, month, day);

        dpd.show();
    }

    private void showProduct(int id){
        originalP = utilities.searchProducts(id);
        if(originalP != null){
            etIDproducto.setText(String.valueOf(originalP.getId()));
            etNombre.setText(originalP.getName());
            etCaducidad.setText(originalP.getDateDue());
            etStock.setText(originalP.getStock());
            currentPath = originalP.getPic();
            setPicture(currentPath);
            enabledComponents();

        }else{
            Toast.makeText(getContext(), "Inserte un ID valido", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinner(String category, boolean status){
        for (int i=0; i<spCategoria.getCount(); i++){
            if(spCategoria.getItemAtPosition(i).toString().equals(category)){
                spCategoria.setSelection(i);
            }
        }

        for(int i=0; i<spStatus.getCount(); i++){
            if(status){
                spStatus.setSelection(0);
            }else{
                spStatus.setSelection(1);
            }
        }
    }

    private void setPicture(String pic){
        try{
                File file = new File(pic);
                Uri photoUri = FileProvider.getUriForFile(getContext(), "com.example.articulos", file);
                ivPicture.setImageURI(photoUri);
        }catch(Exception ex){
            Toast.makeText(getContext(), "Error al cargar la foto", Toast.LENGTH_SHORT).show();
        }
    }

    public void onclick(View view){
        switch(view.getId()){
            case R.id.fe_btnBuscar:break;
            case R.id.fe_calendario:
                showDatePicker();
                break;
            case R.id.fb_btnUpdate:break;
            case R.id.fe_btnCancel:break;
            case R.id.fe_imagen:break;
        }
    }
}