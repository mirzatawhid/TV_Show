package com.dhakaiyacoder.tvshow.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dhakaiyacoder.tvshow.Response.TvShowResponse;
import com.dhakaiyacoder.tvshow.network.ApiClient;
import com.dhakaiyacoder.tvshow.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowsRepository {

    private ApiService apiService;

    public MostPopularTvShowsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowResponse> getMostPopularTVShows(int page){
        MutableLiveData<TvShowResponse> data = new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call,@NonNull Response<TvShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}

