package com.dhakaiyacoder.tvshow.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.dhakaiyacoder.tvshow.R;
import com.dhakaiyacoder.tvshow.adapters.EpisodeAdapter;
import com.dhakaiyacoder.tvshow.adapters.ImageSliderAdapter;
import com.dhakaiyacoder.tvshow.databinding.ActivityTvshowDetailsBinding;
import com.dhakaiyacoder.tvshow.databinding.LayoutEpisodesBottomSheetBinding;
import com.dhakaiyacoder.tvshow.viewmodels.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTvshowDetailsBinding activityTvshowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private BottomSheetDialog episodeBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvshowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tvshow_details);
        doInitialization();
    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        activityTvshowDetailsBinding.imageBackID.setOnClickListener(view -> onBackPressed());
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
                        activityTvshowDetailsBinding.setTvShowImageURl(
                                tvShowDetailsResponse.getTvShowDetails().getImagePath()
                        );
                        activityTvshowDetailsBinding.tvShowImageID.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.setDescription(
                                String.valueOf(HtmlCompat.fromHtml(tvShowDetailsResponse.getTvShowDetails().getDescription(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY))
                        );
                        activityTvshowDetailsBinding.textDesc.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.textReadMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (activityTvshowDetailsBinding.textReadMore.getText().toString().equals("Read More")){
                                    activityTvshowDetailsBinding.textDesc.setMaxLines(Integer.MAX_VALUE);
                                    activityTvshowDetailsBinding.textDesc.setEllipsize(null);
                                    activityTvshowDetailsBinding.textReadMore.setText(R.string.read_less);
                                }else {
                                    activityTvshowDetailsBinding.textDesc.setMaxLines(4);
                                    activityTvshowDetailsBinding.textDesc.setEllipsize(TextUtils.TruncateAt.END);
                                    activityTvshowDetailsBinding.textReadMore.setText(R.string.read_more);
                                }
                            }
                        });
                        activityTvshowDetailsBinding.setRating(
                                String.format(Locale.getDefault(),"%.2f",
                                        Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating()))
                        );
                        if (tvShowDetailsResponse.getTvShowDetails().getGenres()!=null){
                            activityTvshowDetailsBinding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres().get(0));
                        }else {
                            activityTvshowDetailsBinding.setGenre("N/A");
                        }
                        activityTvshowDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShowDetails().getRuntime() + "Min");
                        activityTvshowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                                startActivity(intent);
                            }
                        });
                        activityTvshowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.buttonEpisode.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.buttonEpisode.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (episodeBottomSheetDialog == null){
                                    episodeBottomSheetDialog = new BottomSheetDialog(TVShowDetailsActivity.this);
                                    layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
                                            LayoutInflater.from(TVShowDetailsActivity.this),
                                            R.layout.layout_episodes_bottom_sheet,
                                            findViewById(R.id.episodeContainer),false
                                    );
                                    episodeBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                                    layoutEpisodesBottomSheetBinding.episodeRecyclerView.setAdapter(new EpisodeAdapter(tvShowDetailsResponse.getTvShowDetails().getEpisodes()));
                                    layoutEpisodesBottomSheetBinding.textTitle.setText(String.format("Episode | %s",getIntent().getStringExtra("name")));
                                    layoutEpisodesBottomSheetBinding.imageCLose.setOnClickListener(view1 -> episodeBottomSheetDialog.dismiss());

                                }

                                // --------- Optional Section Start ------- //
                                FrameLayout frameLayout = episodeBottomSheetDialog.findViewById(
                                        com.google.android.material.R.id.design_bottom_sheet
                                );
                                if (frameLayout != null){
                                    BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                                    bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }

                                // ------- Optional Section End --------//
                                episodeBottomSheetDialog.show();

                            }
                        });
                        loadBasicTVShowDetail();
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

    private void loadBasicTVShowDetail(){
        activityTvshowDetailsBinding.setTvShowName(getIntent().getStringExtra("name"));
        activityTvshowDetailsBinding.setNetworkCountry(getIntent().getStringExtra("network") +
                "(" + getIntent().getStringExtra("country") + ")");
        activityTvshowDetailsBinding.setStatus(getIntent().getStringExtra("status"));
        activityTvshowDetailsBinding.setStartedDate(getIntent().getStringExtra("startDate"));
        activityTvshowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityTvshowDetailsBinding.textNetwokCountry.setVisibility(View.VISIBLE);
        activityTvshowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityTvshowDetailsBinding.textStartedDate.setVisibility(View.VISIBLE);
    }

}