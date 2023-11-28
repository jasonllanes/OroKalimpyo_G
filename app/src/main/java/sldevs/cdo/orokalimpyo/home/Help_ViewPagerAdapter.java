package sldevs.cdo.orokalimpyo.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import sldevs.cdo.orokalimpyo.R;

public class Help_ViewPagerAdapter extends PagerAdapter {
    Context context;
    int sliderAllImages[] = {
            R.drawable.guide_0,
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3,
            R.drawable.logo,
    };
    int sliderAllTitle[] = {
            R.string.guide_0,
            R.string.guide_1,
            R.string.guide_2,
            R.string.guide_3,
            R.string.guide_4,
    };
    int sliderAllDesc[] = {
            R.string.guide_desc_0,
            R.string.guide_desc_1,
            R.string.guide_desc_2,
            R.string.guide_desc_3,
            R.string.guide_desc_4,
    };
    public Help_ViewPagerAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return sliderAllTitle.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_screen,container,false);
        ImageView sliderImage = (ImageView) view.findViewById(R.id.sliderImage);
        TextView sliderTitle = (TextView) view.findViewById(R.id.sliderTitle);
        TextView sliderDesc = (TextView) view.findViewById(R.id.sliderDesc);
        sliderImage.setImageResource(sliderAllImages[position]);
        sliderTitle.setText(this.sliderAllTitle[position]);
        sliderDesc.setText(this.sliderAllDesc[position]);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}

