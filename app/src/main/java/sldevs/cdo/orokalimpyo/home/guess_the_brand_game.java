package sldevs.cdo.orokalimpyo.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.game_levels.brand_game;
import sldevs.cdo.orokalimpyo.game_levels.waste_game;

public class guess_the_brand_game extends AppCompatActivity implements View.OnClickListener {

    Button btnBrand1,btnBrand2,btnBrand3,btnBrand4,btnBrand5;
    Button btnBack;
    LottieAnimationView lStar1,lStar2,lStar3,lStar4,lStar5;

    TextView tvStars;
    firebase_crud fc;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_brand_game);

        mAuth = FirebaseAuth.getInstance();
        fc = new firebase_crud();

        btnBrand1 = findViewById(R.id.btnBrand1);
        btnBrand2 = findViewById(R.id.btnBrand2);
        btnBrand3 = findViewById(R.id.btnBrand3);
        btnBrand4 = findViewById(R.id.btnBrand4);
        btnBrand5 = findViewById(R.id.btnBrand5);

        tvStars = findViewById(R.id.tvStars);

        lStar1 = findViewById(R.id.lStar1);
        lStar2 = findViewById(R.id.lStar2);
        lStar3 = findViewById(R.id.lStar3);
        lStar4 = findViewById(R.id.lStar4);
        lStar5 = findViewById(R.id.lStar5);

        btnBack = findViewById(R.id.btnBack);

        btnBrand1.setOnClickListener(this);
        btnBrand2.setOnClickListener(this);
        btnBrand3.setOnClickListener(this);
        btnBrand4.setOnClickListener(this);
        btnBrand5.setOnClickListener(this);

        btnBack.setOnClickListener(this);



        fc.brandGame(guess_the_brand_game.this,getApplicationContext(),mAuth.getUid(),tvStars,lStar1);

        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                // data request
                if(Integer.parseInt(tvStars.getText().toString()) == 1){
                    lStar1.setAnimation(R.raw.lottie_star);
                    lStar1.playAnimation();
                } else if (Integer.parseInt(tvStars.getText().toString()) == 2) {
                    lStar1.setAnimation(R.raw.lottie_star);
                    lStar2.setAnimation(R.raw.lottie_star);

                    lStar1.playAnimation();
                    lStar2.playAnimation();
                } else if (Integer.parseInt(tvStars.getText().toString()) == 3) {
                    lStar1.setAnimation(R.raw.lottie_star);
                    lStar2.setAnimation(R.raw.lottie_star);
                    lStar3.setAnimation(R.raw.lottie_star);

                    lStar1.playAnimation();
                    lStar2.playAnimation();
                    lStar3.playAnimation();
                } else if (Integer.parseInt(tvStars.getText().toString()) == 4) {
                    lStar1.setAnimation(R.raw.lottie_star);
                    lStar2.setAnimation(R.raw.lottie_star);
                    lStar3.setAnimation(R.raw.lottie_star);
                    lStar4.setAnimation(R.raw.lottie_star);

                    lStar1.playAnimation();
                    lStar2.playAnimation();
                    lStar3.playAnimation();
                    lStar4.playAnimation();
                } else if (Integer.parseInt(tvStars.getText().toString()) == 5) {
                    lStar1.setAnimation(R.raw.lottie_star);
                    lStar2.setAnimation(R.raw.lottie_star);
                    lStar3.setAnimation(R.raw.lottie_star);
                    lStar4.setAnimation(R.raw.lottie_star);
                    lStar5.setAnimation(R.raw.lottie_star);

                    lStar1.playAnimation();
                    lStar2.playAnimation();
                    lStar3.playAnimation();
                    lStar4.playAnimation();
                    lStar5.playAnimation();

                }
                handler.postDelayed(this, 500);
            }
        };

        handler.postDelayed(refresh, 500);

    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(guess_the_brand_game.this, home.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btnBrand1){
            Intent i = new Intent(guess_the_brand_game.this, brand_game.class);
            i.putExtra("game_level","Level 1");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnBrand2) {
            Intent i = new Intent(guess_the_brand_game.this, brand_game.class);
            i.putExtra("game_level","Level 2");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnBrand3) {
            Intent i = new Intent(guess_the_brand_game.this, brand_game.class);
            i.putExtra("game_level","Level 3");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnBrand4) {
            Intent i = new Intent(guess_the_brand_game.this, brand_game.class);
            i.putExtra("game_level","Level 4");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnBrand5) {
            Intent i = new Intent(guess_the_brand_game.this, brand_game.class);
            i.putExtra("game_level","Level 5");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnBack) {
            Intent i = new Intent(guess_the_brand_game.this, home.class);
            startActivity(i);
        }


    }


}