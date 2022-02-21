package com.dhakaiyacoder.tvshow.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dhakaiyacoder.tvshow.Response.TvShowResponse;
import com.dhakaiyacoder.tvshow.repositories.MostPopularTvShowsRepository;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTvShowsRepository mostPopularTvShowsRepository;

    public MostPopularTVShowsViewModel() {
        mostPopularTvShowsRepository = new MostPopularTvShowsRepository();
    }

    public LiveData<TvShowResponse> getMostPopularTVShows(int page){
        return mostPopularTvShowsRepository.getMostPopularTVShows(page);
    }

}
