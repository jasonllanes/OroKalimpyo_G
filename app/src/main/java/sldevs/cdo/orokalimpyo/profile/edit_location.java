package sldevs.cdo.orokalimpyo.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.final_sign_up;
import sldevs.cdo.orokalimpyo.authentication.household_sign_up_details;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class edit_location extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ImageView ivBack;
    EditText etLocation;
    Button btnFind,btnUpdate;
    ProgressBar pbLocationLoading;
    Bitmap bitmap;
    private LocationRequest locationRequest;
    Map<String, Object> user_details;
    String user_type,houshold_type,establishment_type,others,name,barangay,location,number,email,all_data;
    firebase_crud fc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fc = new firebase_crud();

        ivBack = findViewById(R.id.ivBack);
        etLocation = findViewById(R.id.etLocation);
        btnFind = findViewById(R.id.btnFind);
        btnUpdate = findViewById(R.id.btnUpdate);
        pbLocationLoading = findViewById(R.id.pbLocationLoading);


        user_type = getIntent().getStringExtra("user_type");
        houshold_type = getIntent().getStringExtra("household_type");
        establishment_type = getIntent().getStringExtra("establishment_type");
        others = getIntent().getStringExtra("others");
        name = getIntent().getStringExtra("name");
        barangay = getIntent().getStringExtra("barangay");
        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");

        location = getIntent().getStringExtra("location");
        etLocation.setText(location);



        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLocationLoading.setVisibility(View.VISIBLE);
                btnFind.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);
                getCurrentLocation();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLocationLoading.setVisibility(View.VISIBLE);
                btnFind.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);
                fc.updateLocation(edit_location.this,etLocation.getText().toString().trim());

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }


    }

    public void getCurrentLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(edit_location.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(edit_location.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(edit_location.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        etLocation.setText(latitude + "," +longitude);
                                        pbLocationLoading.setVisibility(View.GONE);
                                        btnFind.setVisibility(View.VISIBLE);
                                        btnUpdate.setVisibility(View.VISIBLE);
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                    pbLocationLoading.setVisibility(View.GONE);
                    btnFind.setVisibility(View.VISIBLE);
                    btnUpdate.setVisibility(View.VISIBLE);
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                pbLocationLoading.setVisibility(View.GONE);
                btnFind.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
            }
        }
    }

    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(edit_location.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(edit_location.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }




    public void generateQRCode(){
        QRGEncoder qrgEncoder = new QRGEncoder(all_data, null, QRGContents.Type.TEXT, 800);
        qrgEncoder.setColorBlack(Color.rgb(10,147,81));
        qrgEncoder.setColorWhite(Color.rgb(255,255,255));
        // Getting QR-Code as Bitmap
        bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
//            imageView.setImageBitmap(bitmap);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,name + "_QR Code" , "A QR Code that is generated base on the data given");
        } else {
            ActivityCompat.requestPermissions(edit_location.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

    }

}