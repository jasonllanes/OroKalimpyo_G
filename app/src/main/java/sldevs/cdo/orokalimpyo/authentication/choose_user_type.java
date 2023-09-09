package sldevs.cdo.orokalimpyo.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import sldevs.cdo.orokalimpyo.R;

public class choose_user_type extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    Button btnGenerator,btnCollector,btnConsolidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);

        ivBack = findViewById(R.id.ivBack);

        btnGenerator = findViewById(R.id.btnGenerator);
        btnCollector = findViewById(R.id.btnCollector);
        btnConsolidator = findViewById(R.id.btnConsolidator);


        ivBack.setOnClickListener(this);
        btnGenerator.setOnClickListener(this);
        btnCollector.setOnClickListener(this);
        btnConsolidator.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnGenerator){
            Intent i = new Intent(choose_user_type.this,choose_household.class);
            i.putExtra("user_type","Waste Generator");
            startActivity(i);
        } else if(id == R.id.btnCollector){
            Intent i = new Intent(choose_user_type.this,collector_sign_up_details.class);
            i.putExtra("user_type","Waste Collector");
            startActivity(i);
        } else if(id == R.id.btnConsolidator){
            Intent i = new Intent(choose_user_type.this,consolidator_sign_up_details.class);
            i.putExtra("user_type","Waste Consolidator");
            startActivity(i);
        } else if (id == R.id.ivBack) {
            finish();
        }

    }
}