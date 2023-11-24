package sldevs.cdo.orokalimpyo.redeem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.home.home;

public class external_branch_view extends AppCompatActivity {
    WebView wvExternalLink;

    ImageView ivBack;
    TextView tvTitle,tvLink;

    String link,title;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_branch_view);

        wvExternalLink = findViewById(R.id.wvExternalLink);
        tvTitle = findViewById(R.id.tvTitle);
        progressBar = findViewById(R.id.progressBar);



        ivBack = findViewById(R.id.ivBack);


        title = getIntent().getStringExtra("title");
        link = getIntent().getStringExtra("link");

        tvTitle.setText(title);

        wvExternalLink.getSettings().setJavaScriptEnabled(true);
        wvExternalLink.loadUrl(link);

        wvExternalLink.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);

                if(progress == 100){
                    progressBar.setVisibility(View.GONE);

                }

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(external_branch_view.this, home.class);
                startActivity(i);
                finish();
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(external_branch_view.this, home.class);
        startActivity(i);
        finish();

    }
}