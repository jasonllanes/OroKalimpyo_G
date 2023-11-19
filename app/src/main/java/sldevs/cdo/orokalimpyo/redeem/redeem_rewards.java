package sldevs.cdo.orokalimpyo.redeem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.data_fetch.Rewards_Details;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class redeem_rewards extends AppCompatActivity {
    CardView cPoints;
    firebase_crud fc;
    FirebaseAuth mAuth;

    RecyclerView lvRewards;
    FirebaseFirestore db;
    RewardsAdapter adapter;
    String searchQuery;
    SearchView searchView;
    TextView tvPoints,tvResidual, tvBiodegradable, tvRecyclable, tvSpecialWaste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_rewards);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fc = new firebase_crud();

        db = FirebaseFirestore.getInstance();

        tvPoints = findViewById(R.id.tvPoints);

        // Initialize your FirestoreRecyclerOptions and adapter here
        Query query = db.collection("Rewards").orderBy("rewardTitle",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Rewards_Details> options = new FirestoreRecyclerOptions.Builder<Rewards_Details>()
                .setQuery(query, Rewards_Details.class)
                .build();

        adapter = new RewardsAdapter(redeem_rewards.this, options, searchQuery);

        fc.retrievePoints(redeem_rewards.this,redeem_rewards.this,mAuth.getUid(),tvPoints);


        lvRewards = findViewById(R.id.lvRewards);
        lvRewards.setLayoutManager(new LinearLayoutManager(redeem_rewards.this,LinearLayoutManager.VERTICAL,false));
        lvRewards.setAdapter(adapter);

//        fc.retrieveTotalContribution(this,redeem_rewards.this, mAuth.getUid(), tvRecyclable,tvBiodegradable,tvResidual,tvSpecialWaste);
//        fc.retrievePoints(getActivity(),getContext(), mAuth.getUid(), tvPoints);

//        Animation animation = AnimationUtils.loadAnimation(redeem_rewards.this,R.anim.opacity_anim);
//        cPoints.startAnimation(animation);


        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                // Update the Firestore query based on the search input
                Query newQuery = db.collection("Rewards").whereEqualTo("rewardTitle", newText);
                FirestoreRecyclerOptions<Rewards_Details> newOptions =
                        new FirestoreRecyclerOptions.Builder<Rewards_Details>()
                                .setQuery(newQuery, Rewards_Details.class)
                                .build();

                adapter.updateSearchQuery(newText);

                return true;
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}