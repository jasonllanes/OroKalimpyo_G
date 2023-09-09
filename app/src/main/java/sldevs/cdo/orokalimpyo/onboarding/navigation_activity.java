package sldevs.cdo.orokalimpyo.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.log_in;

public class navigation_activity extends AppCompatActivity {


    ViewPager slideViewPager;
    LinearLayout dotIndicator;
    MaterialButton backButton, nextButton, skipButton;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            if (position > 0) {

            }else {

            }
            if (position == 4){
                skipButton.setVisibility(View.GONE);
                nextButton.setText("Finish");
            } else {
                nextButton.setText("Next");
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        nextButton = findViewById(R.id.btnNext);
        skipButton = findViewById(R.id.btnSkip);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 4)
                    slideViewPager.setCurrentItem(getItem(1), true);
                else {
                    Intent i = new Intent(navigation_activity.this, log_in.class);
                    SharedPreferences sharedPref = getSharedPreferences("getting_started", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("was_launched", "true");
                    editor.commit();
                    startActivity(i);
                    finish();
                }
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(navigation_activity.this, log_in.class);
                SharedPreferences sharedPref = getSharedPreferences("getting_started",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("was_launched", "true");
                editor.commit();
                startActivity(i);
                finish();
            }
        });
        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotIndicator = (LinearLayout) findViewById(R.id.dotIndicator);
        viewPagerAdapter = new ViewPagerAdapter(this);
        slideViewPager.setAdapter(viewPagerAdapter);
        setDotIndicator(0);
        slideViewPager.addOnPageChangeListener(viewPagerListener);
    }
    public void setDotIndicator(int position) {
        dots = new TextView[5];
        dotIndicator.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));

            }
            dots[i].setTextSize(45);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getResources().getColor(R.color.grey, getApplicationContext().getTheme()));
            }
            dotIndicator.addView(dots[i]);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dots[position].setTextColor(getResources().getColor(R.color.green, getApplicationContext().getTheme()));
        }
    }
    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }
}