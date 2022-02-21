package com.dhakaiyacoder.tvshow.network;

import com.dhakaiyacoder.tvshow.Response.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvShowResponse> getMostPopularTVShows(@Query("page") int page);

}
