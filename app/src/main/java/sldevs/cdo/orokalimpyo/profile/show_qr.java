package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.final_sign_up;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.home.home;
import sldevs.cdo.orokalimpyo.redeem.external_branch_view;

public class show_qr extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ImageView ivQR;

    Bitmap bitmap;
    Button btnBack;

    firebase_crud fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);


        ivQR = findViewById(R.id.ivQR);
        btnBack = findViewById(R.id.btnBack);


        fc = new firebase_crud();

//        fc.retrieveQRCode(this,getApplicationContext(),mAuth.getUid(),ivQR);

        generateQRCode();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(show_qr.this, home.class);
                startActivity(i);
            }
        });


    }

    public void generateQRCode(){
        QRGEncoder qrgEncoder = new QRGEncoder(mAuth.getUid(), null, QRGContents.Type.TEXT, 800);
        qrgEncoder.setColorBlack(Color.rgb(10,147,81));
        qrgEncoder.setColorWhite(Color.rgb(255,255,255));
        // Getting QR-Code as Bitmap
        bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        ivQR.setImageBitmap(bitmap);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(show_qr.this, home.class);
        startActivity(i);
        finish();

    }
}