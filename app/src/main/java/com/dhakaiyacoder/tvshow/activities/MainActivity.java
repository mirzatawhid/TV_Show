package com.dhakaiyacoder.tvshow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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
    private int currentPage=1;
    private int totalAvailablePages = 1;

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
        activityMainBinding.tvshowRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvshowRecyclerview.canScrollVertically(1)){
                    if (currentPage<=totalAvailablePages){
                        currentPage=currentPage+1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        getMostPopularTVShows();

    }

    private void getMostPopularTVShows(){
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this,mostPopularTVShowsResponse->{
            toggleLoading();
            if (mostPopularTVShowsResponse != null){
                if (mostPopularTVShowsResponse.getTvShows() != null){
                    int oldCount = tvShows.size();
                    totalAvailablePages = mostPopularTVShowsResponse.getPages();
                    tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                    adapter.notifyItemRangeInserted(oldCount,tvShows.size());
                }
            }
        });
    }

    private void toggleLoading(){
        if (currentPage == 1){
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                    activityMainBinding.setIsLoading(false);
            }else{
                activityMainBinding.setIsLoading(true);
            }
        }else{
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            }else{
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

}