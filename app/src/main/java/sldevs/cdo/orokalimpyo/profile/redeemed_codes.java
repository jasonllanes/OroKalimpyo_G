package sldevs.cdo.orokalimpyo.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.data_fetch.Redeemed_Details;
import sldevs.cdo.orokalimpyo.data_fetch.Rewards_Details;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.redeem.RedeemedAdapter;
import sldevs.cdo.orokalimpyo.redeem.RewardsAdapter;
import sldevs.cdo.orokalimpyo.redeem.redeem_rewards;

public class redeemed_codes extends AppCompatActivity {
    CardView cPoints;
    firebase_crud fc;
    FirebaseAuth mAuth;
    ImageView btnBack;

    RecyclerView lvRedeemedCodes;
    FirebaseFirestore db;
    RedeemedAdapter adapter;
    String searchQuery;
    SearchView searchView;
    TextView tvPoints,tvResidual, tvBiodegradable, tvRecyclable, tvSpecialWaste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeemed_codes);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fc = new firebase_crud();


        btnBack = findViewById(R.id.btnBack);


        // Initialize your FirestoreRecyclerOptions and adapter here
        Query query = db.collection("Redeemed Codes").whereEqualTo("user_id",mAuth.getUid()).orderBy("reward_title",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Redeemed_Details> options = new FirestoreRecyclerOptions.Builder<Redeemed_Details>()
                .setQuery(query, Redeemed_Details.class)
                .build();

        adapter = new RedeemedAdapter(redeemed_codes.this, options, searchQuery,mAuth.getUid());



        lvRedeemedCodes = findViewById(R.id.lvRedeemed);
        lvRedeemedCodes.setLayoutManager(new LinearLayoutManager(redeemed_codes.this,LinearLayoutManager.VERTICAL,false));
        lvRedeemedCodes.setAdapter(adapter);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)  {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                // Update the Firestore query based on the search input
                Query newQuery = db.collection("Redeemed Codes").whereEqualTo("reward_title", newText).whereEqualTo("user_id",mAuth.getUid());
                FirestoreRecyclerOptions<Redeemed_Details> newOptions =
                        new FirestoreRecyclerOptions.Builder<Redeemed_Details>()
                                .setQuery(newQuery, Redeemed_Details.class)
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