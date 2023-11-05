package sldevs.cdo.orokalimpyo.redeem;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import sldevs.cdo.orokalimpyo.R;

public class branch extends Fragment {

    WebView wvBranch;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_branch, container, false);

        wvBranch = v.findViewById(R.id.wvBranch);
        progressBar = v.findViewById(R.id.progressBar);

        WebSettings webSettings = wvBranch.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wvBranch.setVisibility(View.GONE);
        wvBranch.loadUrl("http://maps.google.com/maps?q=8.4688048,124.6459148");
        wvBranch.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(view.VISIBLE);
                wvBranch.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(view.GONE);
                wvBranch.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }

        });

        return v;
    }

}