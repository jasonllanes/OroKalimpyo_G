package sldevs.cdo.orokalimpyo.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.game_levels.waste_game;

public class guess_the_waste_game extends AppCompatActivity implements View.OnClickListener {

    Button btnWaste1,btnWaste2,btnWaste3,btnWaste4,btnWaste5;
    Button btnBack;
    LottieAnimationView lStar1,lStar2,lStar3,lStar4,lStar5;

    TextView tvStars;

    firebase_crud fc;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_waste_game);

        mAuth = FirebaseAuth.getInstance();
        fc = new firebase_crud();

        btnWaste1 = findViewById(R.id.btnWaste1);
        btnWaste2 = findViewById(R.id.btnWaste2);
        btnWaste3 = findViewById(R.id.btnWaste3);
        btnWaste4 = findViewById(R.id.btnWaste4);
        btnWaste5 = findViewById(R.id.btnWaste5);

        btnBack = findViewById(R.id.btnBack);


        tvStars = findViewById(R.id.tvStars);

        lStar1 = findViewById(R.id.lStar1);
        lStar2 = findViewById(R.id.lStar2);
        lStar3 = findViewById(R.id.lStar3);
        lStar4 = findViewById(R.id.lStar4);
        lStar5 = findViewById(R.id.lStar5);

        btnWaste1.setOnClickListener(this);
        btnWaste2.setOnClickListener(this);
        btnWaste3.setOnClickListener(this);
        btnWaste4.setOnClickListener(this);
        btnWaste5.setOnClickListener(this);

        btnBack.setOnClickListener(this);


        fc.wasteGame(guess_the_waste_game.this,getApplicationContext(),mAuth.getUid(),tvStars,lStar1);




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
                } else if (Integer.parseInt(tvStars.getText().toString()) >= 5) {
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
        Intent i = new Intent(guess_the_waste_game.this, home.class);
        startActivity(i);



    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btnWaste1){
            Intent i = new Intent(guess_the_waste_game.this, waste_game.class);
            i.putExtra("game_level","Level 1");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnWaste2) {
            Intent i = new Intent(guess_the_waste_game.this, waste_game.class);
            i.putExtra("game_level","Level 2");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnWaste3) {
            Intent i = new Intent(guess_the_waste_game.this, waste_game.class);
            i.putExtra("game_level","Level 3");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnWaste4) {
            Intent i = new Intent(guess_the_waste_game.this, waste_game.class);
            i.putExtra("game_level","Level 4");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnWaste5) {
            Intent i = new Intent(guess_the_waste_game.this, waste_game.class);
            i.putExtra("game_level","Level 5");
            i.putExtra("stars_collected", tvStars.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnBack) {
            Intent i = new Intent(guess_the_waste_game.this, home.class);
            startActivity(i);
            finish();
        }

    }
}