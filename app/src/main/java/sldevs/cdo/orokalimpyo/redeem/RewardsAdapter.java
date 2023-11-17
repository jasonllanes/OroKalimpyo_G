package sldevs.cdo.orokalimpyo.redeem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;
import sldevs.cdo.orokalimpyo.GlideApp;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.data_fetch.News_Details;
import sldevs.cdo.orokalimpyo.data_fetch.Rewards_Details;

public class RewardsAdapter extends FirestoreRecyclerAdapter<Rewards_Details, sldevs.cdo.orokalimpyo.redeem.RewardsAdapter.RewardsHolder> {
    StorageReference storageReference;

    ImageView ivRewards;
    Context context;
    FirestoreRecyclerOptions<Rewards_Details> rewards;


    private String searchQuery = "";
    public RewardsAdapter(Context context, FirestoreRecyclerOptions<Rewards_Details> rewards,String searchQuery) {
        super(rewards);
        this.context = context;
        this.rewards = rewards;
        this.searchQuery = searchQuery;

    }

    public void updateOptions(@NonNull FirestoreRecyclerOptions<Rewards_Details> options) {
        this.searchQuery = "";  // Reset search query when updating options
        super.updateOptions(options);
    }
    public void updateSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RewardsAdapter.RewardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rewards_item_layout,parent,false);
        return new sldevs.cdo.orokalimpyo.redeem.RewardsAdapter.RewardsHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull RewardsAdapter.RewardsHolder holder, int position, @NonNull Rewards_Details model) {
        Rewards_Details rewardsDetails = rewards.getSnapshots().get(position);



        if (model != null) {
            // Check if the model's field is not null
            String modelField = model.getRewardTitle();
            if (modelField != null) {
                if (searchQuery == null || searchQuery.isEmpty() || modelField.toLowerCase().contains(searchQuery.toLowerCase())) {
                    holder.tvTitle.setText(rewardsDetails.getRewardTitle());
                    holder.tvPoints.setText(String.valueOf(rewardsDetails.getPoints()) + " pt/s");
                    holder.tvDescription.setText(rewardsDetails.getDescription());
                    storageReference = FirebaseStorage.getInstance().getReference("Rewards/").child(rewardsDetails.getImageName());
                    GlideApp.with(context).load(storageReference).into(holder.ivRewards);
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
        return rewards.getSnapshots().size();
    }


    public class RewardsHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        TextView tvPoints;

        TextView tvDescription;

        ImageView ivRewards;


        public RewardsHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivRewards = itemView.findViewById(R.id.ivReward);


        }
    }



}