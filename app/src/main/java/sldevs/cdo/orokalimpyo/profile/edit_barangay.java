package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.functions.other_functions;

public class edit_barangay extends AppCompatActivity implements View.OnClickListener {


    MaterialSpinner sBarangay;
    other_functions of;

    ImageView ivBack;

    String user_type, houshold_type, establishment_type, others, name, barangay, number, email;
    Button btnUpdate;
    firebase_crud fc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_barangay);

        fc = new firebase_crud();

        of = new other_functions();
        sBarangay = findViewById(R.id.sBarangay);
        ivBack = findViewById(R.id.ivBack);
        btnUpdate = findViewById(R.id.btnUpdate);


        barangay = getIntent().getStringExtra("barangay");
        sBarangay.setItems(of.populateUserTypeBarangay());


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fc.updateBarangay(edit_barangay.this,sBarangay.getText().toString());
            }
        });



    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.ivBack){
            finish();
        }

    }
}