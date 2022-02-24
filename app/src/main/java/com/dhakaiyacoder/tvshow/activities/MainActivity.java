package com.dhakaiyacoder.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.dhakaiyacoder.tvshow.R;
import com.dhakaiyacoder.tvshow.adapters.TVShowAdapter;
import com.dhakaiyacoder.tvshow.databinding.ActivityMainBinding;
import com.dhakaiyacoder.tvshow.models.TvShow;
import com.dhakaiyacoder.tvshow.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MostPopularTVShowsViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    private List<TvShow> tvShows = new ArrayList<>();
    private TVShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.tvshowRecyclerview.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        adapter = new TVShowAdapter(tvShows);
        activityMainBinding.tvshowRecyclerview.setAdapter(adapter);
        getMostPopularTVShows();

    }

    private void getMostPopularTVShows(){
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTVShows(0).observe(this,mostPopularTVShowsResponse->{
            activityMainBinding.setIsLoading(false);
            if (mostPopularTVShowsResponse != null){
                if (mostPopularTVShowsResponse.getTvShows() != null){
                    tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                    adapter.notifyDataSetChanged();
                }
            }


        });
    }

}