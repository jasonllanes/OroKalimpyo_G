package sldevs.cdo.orokalimpyo.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import sldevs.cdo.orokalimpyo.R;

public class home_frag extends Fragment {

    Button btnGame1, btnGame2;
    LinearLayout llContributions, llAnnouncements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home_frag, container, false);

        btnGame1 = v.findViewById(R.id.btnGame1);
        btnGame2 = v.findViewById(R.id.btnGame2);

        llContributions = v.findViewById(R.id.llContributions);
        llAnnouncements = v.findViewById(R.id.llAnnouncements);


        btnGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), guess_the_waste_game.class);
                startActivity(i);
            }
        });

        btnGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), guess_the_brand_game.class);
                startActivity(i);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fast_anim);
        llContributions.startAnimation(animation);

        Animation animation2 = AnimationUtils.loadAnimation(getContext(),R.anim.fast_anim_right);
        llAnnouncements.startAnimation(animation2);

        return v;
    }
}