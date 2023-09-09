package sldevs.cdo.orokalimpyo.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sldevs.cdo.orokalimpyo.R;

public class getting_started extends AppCompatActivity {


    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        }else{
            ActivityCompat.requestPermissions(getting_started.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        }else{
            ActivityCompat.requestPermissions(getting_started.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

        btnStart = findViewById(R.id.startButton);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getting_started.this, navigation_activity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}