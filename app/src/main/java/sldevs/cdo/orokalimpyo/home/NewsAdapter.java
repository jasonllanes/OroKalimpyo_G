package sldevs.cdo.orokalimpyo.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import sldevs.cdo.orokalimpyo.GlideApp;
import sldevs.cdo.orokalimpyo.R;

import sldevs.cdo.orokalimpyo.data_fetch.News_Details;
import sldevs.cdo.orokalimpyo.redeem.external_branch_view;

public class NewsAdapter extends FirestoreRecyclerAdapter<News_Details, sldevs.cdo.orokalimpyo.home.NewsAdapter.NewsHolder> {
    StorageReference storageReference;

    ImageView ivNews;
    Context context;
    FirestoreRecyclerOptions<News_Details> news;

    private String searchQuery = "";
    public NewsAdapter(Context context, FirestoreRecyclerOptions<News_Details> news,String searchQuery) {
        super(news);
        this.context = context;
        this.searchQuery = searchQuery;
        this.news = news;

    }

    public void updateOptions(@NonNull FirestoreRecyclerOptions<News_Details> options) {
        this.searchQuery = "";  // Reset search query when updating options
        super.updateOptions(options);
    }
    public void updateSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public sldevs.cdo.orokalimpyo.home.NewsAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_item_layout,parent,false);
        return new NewsAdapter.NewsHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull NewsAdapter.NewsHolder holder, int position, @NonNull News_Details model) {
        News_Details news_details = news.getSnapshots().get(position);

        holder.tvTitle.setText(news_details.getTitle());
        holder.tvDescription.setText(news_details.getDescription());
        holder.tvDate.setText(news_details.getTimeStamp().toDate().toString());
        holder.tvNewsLink.setText(news_details.getNewsLink());
        storageReference = FirebaseStorage.getInstance().getReference("News/").child(news_details.getImageName());
        GlideApp.with(context).load(storageReference).into(holder.ivNews);

    }

    @Override
    public int getItemCount() {
        return news.getSnapshots().size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDescription;

        TextView tvDate;

        TextView tvNewsLink;

        ImageView ivNews;

        CardView cardView;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvNewsLink = itemView.findViewById(R.id.tvNewsLink);
            ivNews = itemView.findViewById(R.id.ivNews);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(context, external_branch_view.class);
                    browserIntent.putExtra("title",tvTitle.getText().toString());
                    browserIntent.putExtra("link",tvNewsLink.getText().toString());
                    context.startActivity(browserIntent);

                }
            });


        }
    }



}