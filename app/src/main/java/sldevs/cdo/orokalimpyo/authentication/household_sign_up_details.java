package sldevs.cdo.orokalimpyo.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
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
import com.jaredrummler.materialspinner.MaterialSpinner;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.functions.other_functions;

public class household_sign_up_details extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    EditText etName, etLocation, etNumber;
    MaterialSpinner sBarangay;
    Button btnLocation,btnNext;

    ProgressBar pbLocationLoading;

    private LocationRequest locationRequest;

    other_functions of;

    String user_type, household_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_sign_up_details);

        of = new other_functions();

        ivBack = findViewById(R.id.ivBack);

        etName = findViewById(R.id.etName);
        etLocation = findViewById(R.id.etLocation);
        etNumber = findViewById(R.id.etNumber);

        sBarangay = findViewById(R.id.sBarangay);

        pbLocationLoading = findViewById(R.id.pbLocationLoading);

        btnLocation = findViewById(R.id.btnLocation);
        btnNext = findViewById(R.id.btnNext);


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        //Get intent values from recent activity
        user_type = getIntent().getStringExtra("user_type");
        household_type = getIntent().getStringExtra("household_type");

        sBarangay.setItems(of.populateUserTypeBarangay());
        ivBack.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnNext.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.ivBack) {
            finish();
        } else if (id == R.id.btnNext) {
            if(etName.getText().toString().isEmpty()){
                etName.setError("Please enter your name.");
            }else if (etNumber.getText().toString().isEmpty()) {
                etNumber.setError("Please enter your number.");
            } else if (etNumber.getText().toString().length() <= 10 || etNumber.getText().toString().length() > 11) {
                etNumber.setError("Number should be 11-digit.");
            } else if (etName.getText().toString().isEmpty() && sBarangay.getText().toString().isEmpty() && etNumber.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill up the form.", Toast.LENGTH_SHORT).show();
            }else {
                Intent i = new Intent(household_sign_up_details.this, final_sign_up.class);
                i.putExtra("user_type", user_type);
                i.putExtra("household_type", household_type);
                i.putExtra("name", etName.getText().toString());
                i.putExtra("barangay", sBarangay.getText().toString());
                i.putExtra("location", etLocation.getText().toString());
                i.putExtra("number", etNumber.getText().toString());
                startActivity(i);
            }

        } else if (id == R.id.btnLocation) {
            pbLocationLoading.setVisibility(View.VISIBLE);
            btnLocation.setVisibility(View.GONE);
            getCurrentLocation();
        }

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
            if (ActivityCompat.checkSelfPermission(household_sign_up_details.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(household_sign_up_details.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(household_sign_up_details.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        etLocation.setText(latitude + "," +longitude);
                                        pbLocationLoading.setVisibility(View.GONE);
                                        btnLocation.setVisibility(View.VISIBLE);
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                    pbLocationLoading.setVisibility(View.GONE);
                    btnLocation.setVisibility(View.VISIBLE);
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                pbLocationLoading.setVisibility(View.GONE);
                btnLocation.setVisibility(View.VISIBLE);
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
                    Toast.makeText(household_sign_up_details.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(household_sign_up_details.this, 2);
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

}