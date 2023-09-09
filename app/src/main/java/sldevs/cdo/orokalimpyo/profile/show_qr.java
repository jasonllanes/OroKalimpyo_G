package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class show_qr extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ImageView ivQR;

    Button btnBack;

    firebase_crud fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);


        ivQR = findViewById(R.id.ivQR);
        btnBack = findViewById(R.id.btnBack);


        fc = new firebase_crud();

        fc.retrieveQRCode(this,getApplicationContext(),mAuth.getUid(),ivQR);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}