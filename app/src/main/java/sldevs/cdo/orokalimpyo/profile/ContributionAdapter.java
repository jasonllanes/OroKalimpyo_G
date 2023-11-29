package sldevs.cdo.orokalimpyo.profile;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import io.github.cutelibs.cutedialog.CuteDialog;
import sldevs.cdo.orokalimpyo.GlideApp;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.data_fetch.Scanned_Contributions;

public class ContributionAdapter extends RecyclerView.Adapter<ContributionAdapter.ContributionHolder> {
    StorageReference storageReference;


    Context context;
    ArrayList<Scanned_Contributions> scanned_contributionsArrayList;

    public ContributionAdapter(Context context, ArrayList<Scanned_Contributions> scanned_contributionsArrayList) {
        this.context = context;
        this.scanned_contributionsArrayList = scanned_contributionsArrayList;
    }

    @NonNull
    @Override
    public ContributionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ContributionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContributionHolder holder, int position) {

        Scanned_Contributions scanned_contributions = scanned_contributionsArrayList.get(position);

        holder.tvContributionId.setText("Contribution ID: \n" +scanned_contributions.contribution_id);
        holder.tvDateTime.setText("Date and Time: \n" +scanned_contributions.date + " " + scanned_contributions.time);
        holder.tvName.setText("Name: " + scanned_contributions.name);
        holder.tvBarangay.setText("Barangay: " + scanned_contributions.barangay);
        holder.tvWasteType.setText("Waste Type: " +scanned_contributions.waste_type);
        holder.tvTotalKilo.setText("Kilo: " + scanned_contributions.kilo);





        storageReference = FirebaseStorage.getInstance().getReference("Waste Contribution Proof/").child(scanned_contributions.contribution_id+".png");
        GlideApp.with(context).load(storageReference).into(holder.ivProof);

        holder.ivProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.image_pop);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);
                ImageView ivProof = dialog.findViewById(R.id.ivImage);
                ivProof.setImageDrawable(holder.ivProof.getDrawable());
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return scanned_contributionsArrayList.size();
    }

    public class ContributionHolder extends RecyclerView.ViewHolder {

        TextView tvContributionId;

        TextView tvDateTime;
        TextView tvName;

        TextView tvBarangay;

        TextView tvWasteType;

        TextView tvTotalKilo;

        ImageView ivProof;


        public ContributionHolder(@NonNull View itemView) {
            super(itemView);

            tvContributionId = itemView.findViewById(R.id.tvID);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvName = itemView.findViewById(R.id.tvName);
            tvBarangay = itemView.findViewById(R.id.tvBarangay);
            tvWasteType = itemView.findViewById(R.id.tvWasteType);
            tvTotalKilo = itemView.findViewById(R.id.tvKilo);
            ivProof = itemView.findViewById(R.id.ivProof);




        }
    }


}
