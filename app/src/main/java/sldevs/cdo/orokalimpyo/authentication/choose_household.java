package sldevs.cdo.orokalimpyo.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import sldevs.cdo.orokalimpyo.R;

public class choose_household extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    Button btnHousehold,btnNonHousehold;

    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_household);

        ivBack = findViewById(R.id.ivBack);

        btnHousehold = findViewById(R.id.btnHousehold);
        btnNonHousehold = findViewById(R.id.btnNonHousehold);

        ivBack.setOnClickListener(this);
        btnHousehold.setOnClickListener(this);
        btnNonHousehold.setOnClickListener(this);

        //Get Value of user_type

        user_type = getIntent().getStringExtra("user_type");


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnHousehold){
            Intent i = new Intent(choose_household.this, household_sign_up_details.class);
            i.putExtra("user_type",user_type);
            i.putExtra("household_type","Household");
            startActivity(i);
        } else if(id == R.id.btnNonHousehold){
            Intent i = new Intent(choose_household.this, non_household_sign_up_details.class);
            i.putExtra("user_type",user_type);
            i.putExtra("household_type","Non-Household");
            startActivity(i);
        } else if(id == R.id.ivBack){
            finish();
        }
    }
}