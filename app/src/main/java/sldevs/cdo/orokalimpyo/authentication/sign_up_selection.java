package sldevs.cdo.orokalimpyo.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sldevs.cdo.orokalimpyo.R;

public class sign_up_selection extends AppCompatActivity implements View.OnClickListener {

    Button btnCreate,btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_selection);

        btnCreate = findViewById(R.id.btnCreate);
        btnLogIn = findViewById(R.id.btnLogIn);


        btnCreate.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnCreate){
            Intent i = new Intent(sign_up_selection.this, sign_up_note.class);
            startActivity(i);
        } else if (id == R.id.btnLogIn){
            finish();
        }
    }
}