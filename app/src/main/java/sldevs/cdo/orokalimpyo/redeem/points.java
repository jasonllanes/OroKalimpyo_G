package sldevs.cdo.orokalimpyo.redeem;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import sldevs.cdo.orokalimpyo.R;

public class points extends Fragment {

    CardView cPoints;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_points, container, false);

        cPoints = v.findViewById(R.id.cPoints);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fast_anim);
        cPoints.startAnimation(animation);


        return v;
    }
}