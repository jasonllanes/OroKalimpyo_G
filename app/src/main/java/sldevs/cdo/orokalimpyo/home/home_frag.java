package sldevs.cdo.orokalimpyo.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.log_in;
import sldevs.cdo.orokalimpyo.data_fetch.Branches_Details;
import sldevs.cdo.orokalimpyo.data_fetch.News_Details;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.redeem.BranchAdapter;

public class home_frag extends Fragment {

    Button btnGame1, btnGame2;
    LinearLayout llContributions, llAnnouncements;
    RecyclerView lvNews;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    ArrayList<News_Details> news_details;
    NewsAdapter adapter;
    String searchQuery;
    SearchView searchView;
    TextView viewProfileTextView,tvResidual, tvBiodegradable, tvRecyclable, tvSpecialWaste;

    SwipeRefreshLayout swipeRefreshLayout;
    firebase_crud fc;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        // Initialize your FirestoreRecyclerOptions and adapter here
        Query query = db.collection("News").orderBy("title",Query.Direction.DESCENDING).limit(5);
        FirestoreRecyclerOptions<News_Details> options = new FirestoreRecyclerOptions.Builder<News_Details>()
                .setQuery(query, News_Details.class)
                .build();

        adapter = new NewsAdapter(getContext(), options, searchQuery);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home_frag, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fc = new firebase_crud();



        btnGame1 = v.findViewById(R.id.btnGame1);
        btnGame2 = v.findViewById(R.id.btnGame2);
        tvResidual = v.findViewById(R.id.tvResidual);
        tvBiodegradable = v.findViewById(R.id.tvBiodegradable);
        tvRecyclable = v.findViewById(R.id.tvRecyclable);
        tvSpecialWaste = v.findViewById(R.id.tvSpecialWaste);

        llContributions = v.findViewById(R.id.llContributions);
        viewProfileTextView = v.findViewById(R.id.viewProfileTextView);

        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){
                mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //if task is successful
                        if(task.isSuccessful()){
                            if(mAuth.getCurrentUser() == null){
                                Toast.makeText(getContext(), "Please log in again.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext(), log_in.class);
                                startActivity(intent);
                                getActivity().finish();
                                swipeRefreshLayout.setRefreshing(false);
                            }else{
                                fc.retrieveTotalContribution(getActivity(),getContext(), mAuth.getUid(), tvResidual,tvRecyclable,tvBiodegradable,tvSpecialWaste);
                                fc.retrieveName(getActivity(),getContext(),mAuth.getUid(),viewProfileTextView);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }else {

                            //if task is not successful
                            Toast.makeText(getContext(), "Please log in again.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(), log_in.class);
                            startActivity(intent);
                            getActivity().finish();
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    }
                });


            }
        });

        fc.retrieveTotalContribution(getActivity(),getContext(), mAuth.getUid(), tvResidual,tvRecyclable,tvBiodegradable,tvSpecialWaste);
        fc.retrieveName(getActivity(),getContext(),mAuth.getUid(),viewProfileTextView);



        lvNews = v.findViewById(R.id.lvNews);
        lvNews.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        lvNews.setAdapter(adapter);







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
        lvNews.startAnimation(animation2);




//        recyclerView.setHasFixedSize(true);


        // Initialize your FirestoreRecyclerOptions and adapter here



        searchView = v.findViewById(R.id.searchView);
        searchView.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                // Update the Firestore query based on the search input
                Query newQuery = db.collection("News").whereEqualTo("title", newText);
                FirestoreRecyclerOptions<Branches_Details> newOptions =
                        new FirestoreRecyclerOptions.Builder<Branches_Details>()
                                .setQuery(newQuery, Branches_Details.class)
                                .build();

                adapter.updateSearchQuery(newText);

                return true;
            }
        });

        return v;
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