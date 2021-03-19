package com.example.helloworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Final extends AppCompatActivity {
    Button btnCamara, btnCreditos, btnSalir;
    ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        //Button que sale de la Aplicación.
        btnSalir = findViewById(R.id.btnFinalizar);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        btnCreditos=findViewById(R.id.btnCreditos);
        btnCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        btnCamara=findViewById(R.id.btnCamara);
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaptura(v);
            }
        });
        iv1=findViewById(R.id.ivFoto);
    }

    static final int REQUEST_IMAGE_CAPTURE=1;
    public void mCaptura(View v){
        Intent pictureIntenet = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (pictureIntenet.resolveActivity(getPackageManager())!=null){
            startActivityForResult(pictureIntenet,REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap= (Bitmap) extras.get("data");
            iv1.setImageBitmap(imageBitmap);
            try {
                createFileImage();
                galleryAddpic();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(Final.this,"Falló captura imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    String mCurrentPhotoPath;
    private File createFileImage() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFilename= "JPEG" +timeStamp+ "_";
        File storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image =File.createTempFile(imageFilename,".jpg",storageDir);
        mCurrentPhotoPath="file"+image.getAbsolutePath();
        Toast.makeText(Final.this,mCurrentPhotoPath,Toast.LENGTH_LONG).show();
        return image;
    }

    private void galleryAddpic() {
        Intent mediaintent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File savef = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(savef);
        mediaintent.setData(contentUri);
        this.sendBroadcast(mediaintent);
    }

    public void showAlertDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Acerca de")
                .setMessage("Alumno: Joel Dominguez Merino\n"+
                        "Materia: Desarrollo Móvil\n"+
                        "Profesora: Rocio Elizabeth Pulido Alba\n"+
                        "Febrero-Junio 2021").setPositiveButton("Aceptar",null).show();
    }

}