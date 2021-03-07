package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView lv1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv1 = (ListView)findViewById(R.id.lvElementos);
        tv1 = (TextView)findViewById(R.id.txtMensaje);

        //Creando arreglos
        String nombres [] = {"Joel", "Julieta", "Anahí", "Samuel", "Roberto", "Susana", "Daniela"};
        int edades [] = {21, 22, 15, 34, 39, 19, 21};

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.item_view_elementos, nombres);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1.setText(lv1.getItemAtPosition(position) +" tiene "+edades[position]+" años!");
            }
        });
    }
}