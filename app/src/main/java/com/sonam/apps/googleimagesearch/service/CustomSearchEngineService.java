package com.sonam.apps.googleimagesearch.service;

import com.sonam.apps.googleimagesearch.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sonam on 2/11/2018.
 */

public interface CustomSearchEngineService {

    @GET("customsearch/v1")
    Call<Response> getImages(@Query("q") String q, @Query("start") int page, @Query("num") int num);
}
