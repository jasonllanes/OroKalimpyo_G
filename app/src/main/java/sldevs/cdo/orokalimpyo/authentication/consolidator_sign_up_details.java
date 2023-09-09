package sldevs.cdo.orokalimpyo.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.functions.other_functions;

public class consolidator_sign_up_details extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    EditText etName,etContactPerson,etNumber;
    MaterialSpinner sUserType;
    Button btnNext;

    other_functions of;

    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidator_sign_up_details);

        of = new other_functions();

        ivBack = findViewById(R.id.ivBack);

        etName = findViewById(R.id.etName);
        etContactPerson = findViewById(R.id.etContactPerson);
        etNumber = findViewById(R.id.etNumber);

        sUserType = findViewById(R.id.sUserType);

        btnNext = findViewById(R.id.btnNext);


        //Get intent values from recent activity
        user_type = getIntent().getStringExtra("user_type");


        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);


        sUserType.setItems(of.populateUserTypeConsolidator());
//        sUserType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//
//                if(view.getText().toString().trim().equalsIgnoreCase("Private Collector")){
//                    etAccreditation.setVisibility(View.VISIBLE);
//                    Snackbar customSnackBar = Snackbar.make(view, "Please input accreditation number. ", Snackbar.LENGTH_LONG);
//                    customSnackBar.setTextColor(ContextCompat.getColor(consolidator_sign_up_details.this,R.color.white));
//                    customSnackBar.setBackgroundTint(ContextCompat.getColor(consolidator_sign_up_details.this,R.color.green));
//                    customSnackBar.show();
//                }else{
//                    etAccreditation.setVisibility(View.GONE);
//                }
//
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.ivBack){
            finish();
        } else if(id == R.id.btnNext){
            if(etName.getText().toString().isEmpty()){
                etName.setError("Please enter your name.");
            } else if (etContactPerson.getText().toString().isEmpty()) {
                etContactPerson.setError("Please enter the name.");
            } else if (etNumber.getText().toString().isEmpty()) {
                etNumber.setError("Please enter your number.");
            } else if (etNumber.getText().toString().length() <= 10 || etNumber.getText().toString().length() > 11) {
                etNumber.setError("Number should be 11-digit.");
            }else{
                Intent i = new Intent(consolidator_sign_up_details.this, final_sign_up_2.class);
                i.putExtra("user_type", user_type);
                i.putExtra("consolidator_type", sUserType.getText().toString().trim());
                i.putExtra("name", etName.getText().toString());
                i.putExtra("contact_person", etContactPerson.getText().toString());
                i.putExtra("number", etNumber.getText().toString().trim());
                startActivity(i);
            }

        }
    }
}