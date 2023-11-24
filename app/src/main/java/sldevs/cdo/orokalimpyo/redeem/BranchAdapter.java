package sldevs.cdo.orokalimpyo.redeem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.data_fetch.Branches_Details;

public class BranchAdapter extends FirestoreRecyclerAdapter<Branches_Details, BranchAdapter.BranchHolder> {
    StorageReference storageReference;

    ImageView ivQR;
    Context context;
    FirestoreRecyclerOptions<Branches_Details> branches;

    private String searchQuery = "";


    String url;
    public BranchAdapter(Context context, FirestoreRecyclerOptions<Branches_Details> branches,String searchQuery) {
        super(branches);
        this.context = context;
        this.branches = branches;
        this.searchQuery = searchQuery;
    }

    public void updateOptions(@NonNull FirestoreRecyclerOptions<Branches_Details> options) {
        this.searchQuery = "";  // Reset search query when updating options
        super.updateOptions(options);
    }
    public void updateSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public sldevs.cdo.orokalimpyo.redeem.BranchAdapter.BranchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.branches_item_layout,parent,false);
        return new BranchHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull BranchHolder holder, int position, @NonNull Branches_Details model) {
        Branches_Details branches_details = branches.getSnapshots().get(position);
        url = branches_details.getUrl();
//
//        holder.tvName.setText(branches_details.getName());
//        holder.tvDescription.setText(branches_details.getDescription());
//        holder.tvLocation.setText(branches_details.getLongitude() + ", " + branches_details.getLatitude());
        if (model != null) {
            // Check if the model's field is not null
            String modelField = model.getLocationName();
            if (modelField != null) {
                if (searchQuery == null || searchQuery.isEmpty() || modelField.toLowerCase().contains(searchQuery.toLowerCase())) {
                    holder.tvName.setText(branches_details.getLocationName());
                    holder.tvLocation.setText(branches_details.getLongitude() + ", " + branches_details.getLatitude());
                    holder.tvShared.setText(branches_details.getUrl());
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    return; // Exit the method after binding data
                }
            }
        }

        // If any condition fails or model is null, hide the ViewHolder
        holder.itemView.setVisibility(View.GONE);
        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
    }

    @Override
    public int getItemCount() {
        return branches.getSnapshots().size();
    }


    public class BranchHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        TextView tvShared;
        TextView tvLocation;
        CardView cvBranch;

        public BranchHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
//            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLocation = itemView.findViewById(R.id.tvLocation);
//            ivQR = itemView.findViewById(R.id.ivQR);
            tvShared = itemView.findViewById(R.id.tvShared);
            cvBranch = itemView.findViewById(R.id.cvBranch);


            cvBranch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(context,external_branch_view.class);
                    browserIntent.putExtra("title",tvName.getText().toString());
                    browserIntent.putExtra("link",tvShared.getText().toString());
                    context.startActivity(browserIntent);

                }
            });



        }
    }



}