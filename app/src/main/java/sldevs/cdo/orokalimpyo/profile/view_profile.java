package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import io.github.cutelibs.cutedialog.CuteDialog;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class view_profile extends AppCompatActivity implements View.OnClickListener {

    TextView tvEditName, tvEditBarangay, tvEditLocation, tvEditNumber, tvEditEmail;
    TextView tvType, tvName, tvHouseholdType, tvEstablishmentType, tvBarangay, tvLocation, tvNumber, tvEmail;
    TextView tvEstablishmentTypeL;
    Button btnBack,btnDeleteAccount;
    String user_type, household_type, establishment_type, others, name, barangay, location, number, email;

    FirebaseAuth mAuth;
    firebase_crud fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        mAuth = FirebaseAuth.getInstance();
        fc = new firebase_crud();

        tvEditName = findViewById(R.id.tvEditName);
        tvEditBarangay = findViewById(R.id.tvEditBarangay);
        tvEditLocation = findViewById(R.id.tvEditLocation);
        tvEditNumber = findViewById(R.id.tvEditNumber);
        tvEditEmail = findViewById(R.id.tvEditEmail);

        tvType = findViewById(R.id.tvType);
        tvName = findViewById(R.id.tvName);
        tvHouseholdType = findViewById(R.id.tvHouseholdType);
        tvEstablishmentType = findViewById(R.id.tvEstablishmentType);
        tvBarangay = findViewById(R.id.tvBarangay);
        tvLocation = findViewById(R.id.tvLocation);
        tvNumber = findViewById(R.id.tvNumber);
        tvEmail = findViewById(R.id.tvEmail);
        tvEstablishmentTypeL = findViewById(R.id.tvEstablishmentTypeL);

        user_type = getIntent().getStringExtra("user_type");
        household_type = getIntent().getStringExtra("household_type");
        establishment_type = getIntent().getStringExtra("establishment_type");
        others = getIntent().getStringExtra("others");
        name = getIntent().getStringExtra("name");
        barangay = getIntent().getStringExtra("barangay");
        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");


        btnBack = findViewById(R.id.btnBack);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);




        fc.retrieveProfile(view_profile.this, view_profile.this, mAuth.getUid(), tvName, tvType, tvHouseholdType, tvEstablishmentType, tvBarangay, tvLocation, tvNumber, tvEstablishmentTypeL, tvEmail);


        tvEditName.setOnClickListener(this);
        tvEditBarangay.setOnClickListener(this);
        tvEditLocation.setOnClickListener(this);
        tvEditNumber.setOnClickListener(this);
        tvEditEmail.setOnClickListener(this);


        btnBack.setOnClickListener(this);
        btnDeleteAccount.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.tvEditName) {
            Intent i = new Intent(view_profile.this, edit_name.class);
            i.putExtra("name", tvName.getText().toString());
            startActivity(i);
        } else if (id == R.id.tvEditBarangay) {
            Intent i = new Intent(view_profile.this, edit_barangay.class);
            i.putExtra("barangay", tvBarangay.getText().toString());
            startActivity(i);
        } else if (id == R.id.tvEditLocation) {
            Intent i = new Intent(view_profile.this, edit_location.class);
            i.putExtra("location", tvLocation.getText().toString());
            startActivity(i);
        } else if (id == R.id.tvEditNumber) {
            Intent i = new Intent(view_profile.this, edit_number.class);
            i.putExtra("number", tvNumber.getText().toString());
            startActivity(i);
        } else if (id == R.id.tvEditEmail) {
            Intent i = new Intent(view_profile.this, edit_email.class);
            i.putExtra("email", tvEmail.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnDeleteAccount) {
            new CuteDialog.withIcon(view_profile.this)
                    .setIcon(R.drawable.garbage_red)
                    .setTitle("Delete Account").setTitleTextColor(R.color.red)
                    .setDescription("Are you sure you want to delete this account?").setPositiveButtonColor(R.color.green)
                    .setPositiveButtonText("Yes", v2 -> {
                        fc.deleteAccount(view_profile.this, mAuth.getUid());

                    })
                    .setPositiveButtonColor(R.color.red)
                    .setNegativeButtonText("No", v2 -> {
                        Toast.makeText(view_profile.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    })
                    .show();

        } else if (id == R.id.btnBack) {
            finish();
        }
    }
}