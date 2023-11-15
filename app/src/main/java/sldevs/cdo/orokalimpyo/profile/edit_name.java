package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class edit_name extends AppCompatActivity implements View.OnClickListener {


    ImageView ivBack;
    EditText etName;
    String user_type, houshold_type, establishment_type, others, name, barangay, number, email;
    Button btnUpdate;
    firebase_crud fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        fc = new firebase_crud();

        ivBack = findViewById(R.id.ivBack);
        etName = findViewById(R.id.etName);
        btnUpdate = findViewById(R.id.btnUpdate);

        name = getIntent().getStringExtra("name");

        ivBack.setOnClickListener(this);

        etName.setText(email);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fc.updateName(edit_name.this,etName.getText().toString());
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.ivBack) {
            finish();
        }
    }
}