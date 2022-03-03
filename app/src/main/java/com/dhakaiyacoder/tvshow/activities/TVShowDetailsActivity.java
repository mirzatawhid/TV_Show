package com.dhakaiyacoder.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dhakaiyacoder.tvshow.R;
import com.dhakaiyacoder.tvshow.adapters.ImageSliderAdapter;
import com.dhakaiyacoder.tvshow.databinding.ActivityTvshowDetailsBinding;
import com.dhakaiyacoder.tvshow.viewmodels.TVShowDetailsViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTvshowDetailsBinding activityTvshowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvshowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tvshow_details);
        doInitialization();
    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        getTvShowDetails();
    }

    private void getTvShowDetails() {
        activityTvshowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id", -1));
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(this,
                tvShowDetailsResponse -> {
                    activityTvshowDetailsBinding.setIsLoading(false);
                    if (tvShowDetailsResponse.getTvShowDetails() != null){
                        if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null){
                            loadSliderImage(tvShowDetailsResponse.getTvShowDetails().getPictures());
                        }
                    }
                }
        );
    }

    private void loadSliderImage(String[] sliderImages){

        activityTvshowDetailsBinding.viewPagerID.setOffscreenPageLimit(1);
        activityTvshowDetailsBinding.viewPagerID.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTvshowDetailsBinding.viewPagerID.setVisibility(View.VISIBLE);
        activityTvshowDetailsBinding.showFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicator(sliderImages.length);
        activityTvshowDetailsBinding.viewPagerID.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSlideIndicator(position);
            }
        });
    }

    private void setupSliderIndicator(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i< indicators.length;i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            activityTvshowDetailsBinding.layoutSliderIndicator.addView(indicators[i]);
        }

        activityTvshowDetailsBinding.layoutSliderIndicator.setVisibility(View.VISIBLE);
        setCurrentSlideIndicator(0);

    }

    private void setCurrentSlideIndicator(int position){
        int childCount = activityTvshowDetailsBinding.layoutSliderIndicator.getChildCount();
        for (int i = 0 ; i<childCount ; i++){
            ImageView imageView = (ImageView) activityTvshowDetailsBinding.layoutSliderIndicator.getChildAt(i);
            if (i == position){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_active));
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));
            }
        }
    }

}