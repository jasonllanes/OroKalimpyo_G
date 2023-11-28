package sldevs.cdo.orokalimpyo.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.log_in;
import sldevs.cdo.orokalimpyo.databinding.ActivityHomeBinding;
import sldevs.cdo.orokalimpyo.profile.profile_fragment;
import sldevs.cdo.orokalimpyo.profile.show_qr;
import sldevs.cdo.orokalimpyo.records.user_records;
import sldevs.cdo.orokalimpyo.redeem.BranchAdapter;
import sldevs.cdo.orokalimpyo.redeem.branch;
import sldevs.cdo.orokalimpyo.redeem.points;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;
    boolean isConnected;
    LinearLayout tvConnection;
    Dialog builder;
    FloatingActionButton btnShowQR;

    BranchAdapter adapter;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();


        replaceFragment(new home_frag());
        binding.btmNavBarView.setBackground(null);

        btnShowQR = findViewById(R.id.btnShowQR);
        btnShowQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home.this,show_qr.class);
                startActivity(i);
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth.getCurrentUser().reload();
                if(mAuth.getCurrentUser() == null){
                    Intent intent = new Intent(home.this, log_in.class);
                    startActivity(intent);
                    finish();
                }
            }
        },500);



        binding.btmNavBarView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new home_frag());
            } else if (itemId == R.id.points) {
                replaceFragment(new points());
            }  else if (itemId == R.id.record) {
                Intent i = new Intent(home.this, show_qr.class);
                startActivity(i);
            } else if (itemId == R.id.branch) {
                replaceFragment(new branch());
            }   else if (itemId == R.id.profile) {
                replaceFragment(new profile_fragment());
            }
            return true;
        });

    }

    
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }



}