package sldevs.cdo.orokalimpyo.redeem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.cutelibs.cutedialog.CuteDialog;
//import com.saadahmedsoft.popupdialog.PopupDialog;
//import com.saadahmedsoft.popupdialog.Styles;
//import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.cutelibs.cutedialog.CuteDialog;
import sldevs.cdo.orokalimpyo.GlideApp;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.data_fetch.News_Details;
import sldevs.cdo.orokalimpyo.data_fetch.Rewards_Details;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class RewardsAdapter extends FirestoreRecyclerAdapter<Rewards_Details, sldevs.cdo.orokalimpyo.redeem.RewardsAdapter.RewardsHolder> {
    StorageReference storageReference;
    TextView currentPoints;

    ImageView ivRewards;
    Context context;
    FirestoreRecyclerOptions<Rewards_Details> rewards;
    Rewards_Details rewardsDetails;
    Button btnYes,btnNo;
    String user_id;
    SimpleDateFormat month,day,year,week,date,hours,minutes,seconds,time;
    String currentMonth,currentDay,currentYear,currentWeek,currentDate,currentHour,currentMinute,currentSeconds,currentTime;
    firebase_crud fc;

    private String searchQuery = "";
    public RewardsAdapter(Context context, FirestoreRecyclerOptions<Rewards_Details> rewards,String searchQuery,TextView currentPoints,String user_id) {
        super(rewards);
        this.context = context;
        this.rewards = rewards;
        this.searchQuery = searchQuery;
        this.currentPoints = currentPoints;
        this.user_id = user_id;
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
        rewardsDetails = rewards.getSnapshots().get(position);



        if (model != null) {
            // Check if the model's field is not null
            String modelField = model.getRewardTitle();
            if (modelField != null) {
                if (searchQuery == null || searchQuery.isEmpty() || modelField.toLowerCase().contains(searchQuery.toLowerCase())) {
                    holder.tvTitle.setText(rewardsDetails.getRewardTitle());
                    holder.tvPoints.setText(String.valueOf(rewardsDetails.getPoints()));
                    holder.tvDescription.setText(rewardsDetails.getDescription());
                    holder.tvRewardCode.setText(rewardsDetails.getRewardCode());
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

        TextView tvRewardCode;

        public RewardsHolder(@NonNull View itemView) {
            super(itemView);

            fc = new firebase_crud();
            tvRewardCode = itemView.findViewById(R.id.tvRewardCode);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivRewards = itemView.findViewById(R.id.ivReward);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CuteDialog.withIcon(context)
                            .setIcon(R.drawable.star)
                            .setTitle("Redeem Reward").setTitleTextColor(R.color.green)
                            .setDescription("Are you sure you want to redeem this?").setPositiveButtonColor(R.color.green)
                            .setPositiveButtonText("Yes", v2 -> {
                                retrieveDate();
                                String id = user_id.substring(0,5) + currentMonth + currentDay + currentYear + currentHour + currentMinute + currentSeconds;
                                if(Double.parseDouble(currentPoints.getText().toString()) >= Double.parseDouble(tvPoints.getText().toString())){
                                    fc.updatePoints(context,Double.parseDouble(currentPoints.getText().toString()) - Double.parseDouble(tvPoints.getText().toString()),id,rewardsDetails.getImageName(),tvRewardCode.getText().toString(),tvTitle.getText().toString());
//                                Intent i = new Intent(context, success_redeem.class);
//                                i.putExtra("rewardTitle", tvTitle.getText().toString());
//                                context.startActivity(i);
                                }else{
                                    PopupDialog.getInstance(context)
                                            .setStyle(Styles.FAILED)
                                            .setHeading("Sorry!" + "\n" + "Redeem Unsuccessful.")
                                            .setDescription("Insufficient Balance.")
                                            .setCancelable(false)
                                            .showDialog(new OnDialogButtonClickListener() {
                                                @Override
                                                public void onDismissClicked(Dialog dialog) {
                                                    super.onDismissClicked(dialog);
                                                }
                                            });
                                }

                            })
                            .setNegativeButtonText("No", v2 -> {
                                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                            })
                            .show();

//
                }
            });

        }
    }

    public void retrieveDate(){
        month = new SimpleDateFormat("MM");
        day = new SimpleDateFormat("dd");
        year = new SimpleDateFormat("yy");

        week = new SimpleDateFormat("W");

        date = new SimpleDateFormat("MM/dd/yy");

        hours = new SimpleDateFormat("hh");
        minutes = new SimpleDateFormat("mm");
        seconds = new SimpleDateFormat("ss");

        time = new SimpleDateFormat("hh:mm:ss a");

        currentMonth = month.format(new Date());
        currentDay = day.format(new Date());
        currentYear = year.format(new Date());

        currentWeek = week.format(new Date());

        currentDate = date.format(new Date());

        currentHour = hours.format(new Date());
        currentMinute = minutes.format(new Date());
        currentSeconds = seconds.format(new Date());

        currentTime = time.format(new Date());
    }

}