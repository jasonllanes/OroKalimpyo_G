package sldevs.cdo.orokalimpyo.home;

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
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import sldevs.cdo.orokalimpyo.GlideApp;
import sldevs.cdo.orokalimpyo.R;

import sldevs.cdo.orokalimpyo.data_fetch.News_Details;

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
        holder.tvDate.setText(news_details.getTimeStamp().toDate().toString());
        storageReference = FirebaseStorage.getInstance().getReference("News/").child(news_details.getImageName());
        GlideApp.with(context).load(storageReference).into(holder.ivNews);

    }

    @Override
    public int getItemCount() {
        return news.getSnapshots().size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        TextView tvDate;

        ImageView ivNews;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivNews = itemView.findViewById(R.id.ivNews);


        }
    }



}