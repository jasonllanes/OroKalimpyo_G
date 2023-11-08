package sldevs.cdo.orokalimpyo.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import sldevs.cdo.orokalimpyo.R;

public class guess_the_brand_game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_brand_game);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(guess_the_brand_game.this, home.class);
        startActivity(i);
    }
}