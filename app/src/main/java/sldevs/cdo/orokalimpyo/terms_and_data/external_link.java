package sldevs.cdo.orokalimpyo.terms_and_data;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import sldevs.cdo.orokalimpyo.R;

public class external_link extends AppCompatActivity {


    WebView wvExternalLink;

    ImageView ivBack;
    TextView tvLink,tvTitle;

    String link,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_policy);

        wvExternalLink = findViewById(R.id.wvExternalLink);

        ivBack = findViewById(R.id.ivBack);

        tvTitle = findViewById(R.id.tvTitle);


        title = getIntent().getStringExtra("title");
        link = getIntent().getStringExtra("external_link");

//        tvLink.setText(title);

        tvTitle.setText(title);

        wvExternalLink.getSettings().setJavaScriptEnabled(true);
        wvExternalLink.loadUrl(link);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}