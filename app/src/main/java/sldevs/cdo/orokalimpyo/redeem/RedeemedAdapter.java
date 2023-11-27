package sldevs.cdo.orokalimpyo.redeem;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import sldevs.cdo.orokalimpyo.GlideApp;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.data_fetch.Redeemed_Details;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class RedeemedAdapter extends FirestoreRecyclerAdapter<Redeemed_Details, RedeemedAdapter.RedeemedHolder> {
    StorageReference storageReference;
    TextView currentPoints;

    ImageView ivRewards;
    Context context;
    FirestoreRecyclerOptions<Redeemed_Details> rewards;
    Button btnYes,btnNo;
    String user_id;
    SimpleDateFormat month,day,year,week,date,hours,minutes,seconds,time;
    String currentMonth,currentDay,currentYear,currentWeek,currentDate,currentHour,currentMinute,currentSeconds,currentTime;
    firebase_crud fc;
    boolean codeShowed = false;

    private String searchQuery = "";
    public RedeemedAdapter(Context context, FirestoreRecyclerOptions<Redeemed_Details> rewards,String searchQuery,String user_id) {
        super(rewards);
        this.context = context;
        this.rewards = rewards;
        this.searchQuery = searchQuery;
        this.user_id = user_id;
    }

    public void updateOptions(@NonNull FirestoreRecyclerOptions<Redeemed_Details> options) {
        this.searchQuery = "";  // Reset search query when updating options
        super.updateOptions(options);
    }
    public void updateSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RedeemedAdapter.RedeemedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.redeem_item_layout,parent,false);
        return new sldevs.cdo.orokalimpyo.redeem.RedeemedAdapter.RedeemedHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull RedeemedAdapter.RedeemedHolder holder, int position, @NonNull Redeemed_Details model) {
        Redeemed_Details redeemedDetails = rewards.getSnapshots().get(position);


        holder.ivShowCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!codeShowed){
                    codeShowed = true;
                    holder.tvCode.setVisibility(View.VISIBLE);
                    holder.tvHideCode.setVisibility(View.GONE);
                    holder.ivShowCode.setImageResource(R.drawable.hide_password);
                }else {
                    codeShowed = false;
                    holder.tvCode.setVisibility(View.GONE);
                    holder.tvHideCode.setVisibility(View.VISIBLE);
                    holder.ivShowCode.setImageResource(R.drawable.show_password);
                }

            }
        });

        if (model != null) {
            // Check if the model's field is not null
            String modelField = model.getReward_title();
            if (modelField != null) {
                if (searchQuery == null || searchQuery.isEmpty() || modelField.toLowerCase().contains(searchQuery.toLowerCase())) {
                    holder.tvTitle.setText(redeemedDetails.getReward_title());
                    holder.tvCode.setText(redeemedDetails.getReward_code());
                    holder.tvDate.setText(redeemedDetails.getRedeemed_date().toDate().toString());
                    storageReference = FirebaseStorage.getInstance().getReference("Rewards/").child(redeemedDetails.getImageName());
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


    public class RedeemedHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        TextView tvCode;
        TextView tvHideCode;

        TextView tvDate;

        ImageView ivRewards;

        ImageView ivShowCode;




        public RedeemedHolder(@NonNull View itemView) {
            super(itemView);

            fc = new firebase_crud();
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCode = itemView.findViewById(R.id.tvRedeemCode);
            tvHideCode = itemView.findViewById(R.id.tvCoverCode);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivRewards = itemView.findViewById(R.id.ivReward);

            ivShowCode = itemView.findViewById(R.id.ivShowCode);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


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
