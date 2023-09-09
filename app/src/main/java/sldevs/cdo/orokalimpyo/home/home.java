package sldevs.cdo.orokalimpyo.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.databinding.ActivityHomeBinding;
import sldevs.cdo.orokalimpyo.profile.profile_fragment;
import sldevs.cdo.orokalimpyo.profile.show_qr;
import sldevs.cdo.orokalimpyo.records.user_records;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;
    boolean isConnected;
    LinearLayout tvConnection;
    Dialog builder;
    FloatingActionButton btnShowQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        replaceFragment(new home_fragment());
        binding.btmNavBarView.setBackground(null);

        btnShowQR = findViewById(R.id.btnShowQR);
        btnShowQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home.this,show_qr.class);
                startActivity(i);
            }
        });

        binding.btmNavBarView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new home_fragment());
            } else if (itemId == R.id.record) {
                Intent i = new Intent(home.this, show_qr.class);
                startActivity(i);
            } else if (itemId == R.id.profile) {
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