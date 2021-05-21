package com.example.crud_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.crud_firebase.dbfb.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etApellido, etCorreo, etContraseña;
    ListView lvPersonas;

    private List<Persona> ListaPersona = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;
    Persona personaSelected;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etCorreo = findViewById(R.id.etCorreo);
        etContraseña = findViewById(R.id.etContraseña);
        lvPersonas = findViewById(R.id.lvListaElementos);

        iniciarFirebase();
        listarDatos();
        lvPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelected = (Persona)parent.getItemAtPosition(position);
                etNombre.setText(personaSelected.getNombre());
                etApellido.setText(personaSelected.getApellido());
                etCorreo.setText(personaSelected.getCorreo());
                etContraseña.setText(personaSelected.getPassword());
            }
        });
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            //@NonNull @org.jetbrains.annotations.NotNull
            public void onDataChange(DataSnapshot snapshot) {
                ListaPersona.clear();
                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    Persona p = objSnapshot.getValue(Persona.class);
                    ListaPersona.add(p);
                    arrayAdapterPersona = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, ListaPersona);
                    lvPersonas.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void limpiar(){
        etNombre.setText("");
        etApellido.setText("");
        etCorreo.setText("");
        etContraseña.setText("");
    }

    private void validacion(){
        String name = etNombre.getText().toString();
        String last_name = etApellido.getText().toString();
        String email = etCorreo.getText().toString();
        String password = etContraseña.getText().toString();

        if(name.equals("")){
            etNombre.setError("Campo Obligatorio");
        }else if(last_name.equals("")){
            etApellido.setError("Campo Obligatorio");
        }else if(email.equals("")){
            etCorreo.setError("Campo Obligatorio");
        }else if(password.equals("")){
            etContraseña.setError("Campo Obligatorio");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        String name = etNombre.getText().toString();
        String last_name = etApellido.getText().toString();
        String email = etCorreo.getText().toString();
        String password = etContraseña.getText().toString();

        switch(item.getItemId()){
            case R.id.icon_add:{

                if(name.equals("")|last_name.equals("")|email.equals("")|password.equals("")){
                    validacion();
                }else {
                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(name);
                    p.setApellido(last_name);
                    p.setCorreo(email);
                    p.setPassword(password);
                    databaseReference.child("Persona").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Añadido Correctamente", Toast.LENGTH_LONG).show();
                    limpiar();
                }
            } break;
            case R.id.icon_save:{
                Persona p = new Persona();
                p.setUid(personaSelected.getUid());
                p.setNombre(etNombre.getText().toString().trim());
                p.setApellido(etApellido.getText().toString().trim());
                p.setCorreo(etCorreo.getText().toString().trim());
                p.setPassword(etContraseña.getText().toString().trim());
                databaseReference.child("Persona").child(p.getUid()).setValue(p);
                Toast.makeText(this, "Actualizado Correctamente", Toast.LENGTH_LONG).show();
                limpiar();
            } break;
            case R.id.icon_delete:{
                Persona p = new Persona();
                p.setUid(personaSelected.getUid());
                databaseReference.child("Persona").child(p.getUid()).removeValue();
                Toast.makeText(this, "Eliminado Correctamente", Toast.LENGTH_LONG).show();
                limpiar();
            } break;
        }
        return true;
    }
}