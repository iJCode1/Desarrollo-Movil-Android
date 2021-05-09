package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firebase.model.Cubo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etCategoria, etPrecio;
    ListView lvCubos;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etNombre = (EditText) findViewById(R.id.txtNombre);
        etCategoria = (EditText) findViewById(R.id.txtCategoria);
        etPrecio =  (EditText) findViewById(R.id.txtPrecio);
        lvCubos = findViewById(R.id.lv_cubos);

        inicializarFirebase();
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = etNombre.getText().toString();
        String categoria = etCategoria.getText().toString();
        Double precio = Double.parseDouble(etPrecio.getText().toString());

        switch(item.getItemId()){
            case R.id.icon_add:{
                if(nombre.equals("")| categoria.equals("")| precio.equals("")){
                    validacion();
                }else{
                    Cubo cubo = new Cubo();
                    cubo.setUid(UUID.randomUUID().toString());
                    cubo.setNombre(nombre);
                    cubo.setCategoria(categoria);
                    cubo.setPrecio(precio);

                    databaseReference.child("Cubo").child(cubo.getUid()).setValue(cubo);

                    Toast.makeText(this, "Alta relaizada", Toast.LENGTH_SHORT).show();
                    limpiarCajas();

                }
                break;
            }
            case R.id.icon_save:{
                Toast.makeText(this, "Guardado Exitoso", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.icon_delete:{
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiarCajas() {
        etNombre.setText("");
        etCategoria.setText("");
        etPrecio.setText("");
    }

    private void validacion() {
        String nombre = etNombre.getText().toString();
        String categoria = etCategoria.getText().toString();
        String precio = etPrecio.getText().toString();

        if(nombre.equals("")){
            etNombre.setError("Required");
        }else if(categoria.equals("")){
            etCategoria.setError("Required");
        }else if(precio.equals("")){
            etPrecio.setError("Required");
        }
    }
}