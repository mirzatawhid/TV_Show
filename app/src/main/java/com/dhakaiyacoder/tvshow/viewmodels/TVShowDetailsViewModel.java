package com.dhakaiyacoder.tvshow.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dhakaiyacoder.tvshow.Response.TVShowDetailsResponse;
import com.dhakaiyacoder.tvshow.Response.TvShowResponse;
import com.dhakaiyacoder.tvshow.repositories.TVShowDetailsRepository;

public class TVShowDetailsViewModel extends ViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;

    public TVShowDetailsViewModel() {
        tvShowDetailsRepository = new TVShowDetailsRepository();
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
    }

}
