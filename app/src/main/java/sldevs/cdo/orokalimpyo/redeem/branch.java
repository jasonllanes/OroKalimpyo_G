package sldevs.cdo.orokalimpyo.redeem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.log_in;
import sldevs.cdo.orokalimpyo.data_fetch.Branches_Details;
import sldevs.cdo.orokalimpyo.data_fetch.Scanned_Contributions;
import sldevs.cdo.orokalimpyo.profile.ContributionAdapter;
import sldevs.cdo.orokalimpyo.profile.view_contribution;


public class branch extends Fragment {

    WebView wvBranch;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    ArrayList<Branches_Details> branches_details;
    BranchAdapter adapter;
    String searchQuery;
    SearchView searchView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        // Initialize your FirestoreRecyclerOptions and adapter here
        Query query = db.collection("Branches").orderBy("locationName",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Branches_Details> options = new FirestoreRecyclerOptions.Builder<Branches_Details>()
                .setQuery(query, Branches_Details.class)
                .build();

        adapter = new BranchAdapter(getContext(), options, searchQuery);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_branch, container, false);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //if task is successful
                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser() == null){
                        Intent intent = new Intent(getContext(), log_in.class);
                        startActivity(intent);
                        getActivity().finish();
                    }else{

                    }
                }else {
                    //if task is not successful
                    Toast.makeText(getContext(), "Please log in again.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), log_in.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        recyclerView = v.findViewById(R.id.lvContributions);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.slide_up_anim);
        recyclerView.startAnimation(animation);

        //        branches_details = new ArrayList<Branches_Details>();



//        EventChangeListener();


//        wvBranch = v.findViewById(R.id.wvBranch);
//        progressBar = v.findViewById(R.id.progressBar);
//
//        WebSettings webSettings = wvBranch.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        wvBranch.setVisibility(View.GONE);
//        wvBranch.loadUrl("http://maps.google.com/maps?q=8.4688048,124.6459148");
//        wvBranch.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                progressBar.setVisibility(view.VISIBLE);
//                wvBranch.setVisibility(View.GONE);
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                progressBar.setVisibility(view.GONE);
//                wvBranch.setVisibility(View.VISIBLE);
//                super.onPageFinished(view, url);
//            }
//
//        });

        searchView = v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                // Update the Firestore query based on the search input
                Query newQuery = db.collection("Branches").whereEqualTo("name", newText);
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