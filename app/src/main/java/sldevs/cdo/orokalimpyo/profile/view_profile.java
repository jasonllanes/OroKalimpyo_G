package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sldevs.cdo.orokalimpyo.R;

public class view_profile extends AppCompatActivity implements View.OnClickListener {

    TextView tvEditName,tvEditBarangay,tvEditLocation,tvEditNumber,tvEditEmail;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        tvEditName = findViewById(R.id.tvEditName);
        tvEditBarangay = findViewById(R.id.tvEditBarangay);
        tvEditLocation = findViewById(R.id.tvEditLocation);
        tvEditNumber = findViewById(R.id.tvEditNumber);
        tvEditEmail = findViewById(R.id.tvEditEmail);


        btnBack = findViewById(R.id.btnBack);



        tvEditName.setOnClickListener(this);
        tvEditBarangay.setOnClickListener(this);
        tvEditLocation.setOnClickListener(this);
        tvEditNumber.setOnClickListener(this);
        tvEditEmail.setOnClickListener(this);

        btnBack.setOnClickListener(this);





    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.tvEditName){
            Intent i = new Intent(view_profile.this, edit_name.class);
            startActivity(i);
        } else if (id == R.id.tvEditBarangay) {
            Intent i = new Intent(view_profile.this, edit_barangay.class);
            startActivity(i);
        } else if (id == R.id.tvEditLocation) {
            Intent i = new Intent(view_profile.this, edit_location.class);
            startActivity(i);
        } else if (id == R.id.tvEditNumber) {
            Intent i = new Intent(view_profile.this, edit_number.class);
            startActivity(i);
        } else if (id == R.id.btnBack) {

        }
    }
}