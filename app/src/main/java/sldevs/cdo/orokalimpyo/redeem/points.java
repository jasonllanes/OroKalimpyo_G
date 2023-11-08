package sldevs.cdo.orokalimpyo.redeem;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class points extends Fragment {

    CardView cPoints;
    firebase_crud fc;
    FirebaseAuth mAuth;
    TextView tvResidual, tvBiodegradable, tvRecyclable, tvSpecialWaste;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_points, container, false);
        mAuth = FirebaseAuth.getInstance();
        fc = new firebase_crud();
        tvResidual = v.findViewById(R.id.tvResidual);
        tvBiodegradable = v.findViewById(R.id.tvBiodegradable);
        tvRecyclable = v.findViewById(R.id.tvRecyclable);
        tvSpecialWaste = v.findViewById(R.id.tvSpecialWaste);

        cPoints = v.findViewById(R.id.cPoints);

        fc.retrieveTotalContribution(getActivity(),getContext(), mAuth.getUid(), tvRecyclable,tvBiodegradable,tvResidual,tvSpecialWaste);


        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.opacity_anim);
        cPoints.startAnimation(animation);


        return v;
    }
}