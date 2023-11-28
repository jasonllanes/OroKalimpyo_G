package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class edit_email extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    firebase_crud fc;
    String user_type, houshold_type, establishment_type, others, name, barangay, number, email;

    EditText etCurrentEmail,etPassword,etEmail;

    Button btnUpdateEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        fc = new firebase_crud();



        ivBack = findViewById(R.id.ivBack);
        etCurrentEmail = findViewById(R.id.etCurrentEmail);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnUpdateEmail = findViewById(R.id.btnUpdate);



        email = getIntent().getStringExtra("email");

        etCurrentEmail.setText(email);

        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fc.updateEmail(edit_email.this,etCurrentEmail.getText().toString(),etPassword.getText().toString(), etEmail.getText().toString());

            }
        });




        ivBack.setOnClickListener(this);

    }

    public void onClick(View view) {

        if (view.getId() == R.id.ivBack) {
            finish();
        }
    }
}