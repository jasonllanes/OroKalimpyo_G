package sldevs.cdo.orokalimpyo.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import sldevs.cdo.orokalimpyo.R;

public class sign_up_note extends AppCompatActivity {

    ImageView ivBack;
    Button btnOkay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_note);


        ivBack = findViewById(R.id.ivBack);

        btnOkay = findViewById(R.id.btnOkay);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(sign_up_note.this, choose_user_type.class);
                startActivity(i);
                finish();
            }
        });

    }
}