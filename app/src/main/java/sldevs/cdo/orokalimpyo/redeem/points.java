package sldevs.cdo.orokalimpyo.redeem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.data_fetch.Branches_Details;
import sldevs.cdo.orokalimpyo.data_fetch.News_Details;
import sldevs.cdo.orokalimpyo.data_fetch.Rewards_Details;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.home.NewsAdapter;

public class points extends Fragment {

    CardView cPoints;
    firebase_crud fc;
    FirebaseAuth mAuth;

    RecyclerView lvRewards;
    FirebaseFirestore db;
    RewardsAdapter adapter;
    String searchQuery;
    SearchView searchView;
    TextView tvPoints,tvResidual, tvBiodegradable, tvRecyclable, tvSpecialWaste;

    Button btnRedeem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_points, container, false);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fc = new firebase_crud();

        tvPoints = v.findViewById(R.id.tvPoints);
        tvResidual = v.findViewById(R.id.tvResidual);
        tvBiodegradable = v.findViewById(R.id.tvBiodegradable);
        tvRecyclable = v.findViewById(R.id.tvRecyclable);
        tvSpecialWaste = v.findViewById(R.id.tvSpecialWaste);

        cPoints = v.findViewById(R.id.cPoints);

        btnRedeem = v.findViewById(R.id.btnRedeem);



        fc.retrieveTotalContribution(getActivity(),getContext(), mAuth.getUid(), tvRecyclable,tvBiodegradable,tvResidual,tvSpecialWaste);
//        fc.retrievePoints(getActivity(),getContext(), mAuth.getUid(), tvPoints);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.opacity_anim);
        cPoints.startAnimation(animation);


        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), redeem_rewards.class);
                startActivity(intent);
            }
        });



        return v;
    }

}