package sldevs.cdo.orokalimpyo.redeem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import sldevs.cdo.orokalimpyo.R;

public class success_redeem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_redeem);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(success_redeem.this, redeem_rewards.class);
        startActivity(i);
        finish();
    }
}