package com.example.examenu1_desarrollomvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class ratingBar extends AppCompatActivity {

    RatingBar rb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);

        rb1 = (RatingBar)findViewById(R.id.ratingB1);

        rb1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating <= 1){
                    Toast.makeText(ratingBar.this, "Lamento que no te haya gustado la app :(, votación: "+rating, Toast.LENGTH_SHORT).show();
                }else if(rating> 1 && rating <4){
                    Toast.makeText(ratingBar.this, "Gracias por votar, puedes sugerir mejoras! :), votación: "+rating, Toast.LENGTH_SHORT).show();
                }else if(rating>=4 || rating<=5){
                    Toast.makeText(ratingBar.this, "Gracias por evaluar bien a la aplicación :D, votación: "+rating, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void regresar(View view){
        Intent i = new Intent(this, Opciones.class);
        startActivity(i);
    }
}