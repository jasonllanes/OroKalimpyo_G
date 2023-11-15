package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class edit_number extends AppCompatActivity implements View.OnClickListener {


    ImageView ivBack;
    String user_type, houshold_type, establishment_type, others, name, barangay, number, email;
    EditText etNumber;

    Button btnUpdateNumber;
    firebase_crud fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_number);

        fc = new firebase_crud();

        ivBack = findViewById(R.id.ivBack);
        etNumber = findViewById(R.id.etNumber);
        btnUpdateNumber = findViewById(R.id.btnUpdate);

        number = getIntent().getStringExtra("number");

        etNumber.setText(number);

        btnUpdateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fc.updateNumber(edit_number.this, etNumber.getText().toString());

            }
        });


        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.ivBack) {
            finish();
        }
    }
}