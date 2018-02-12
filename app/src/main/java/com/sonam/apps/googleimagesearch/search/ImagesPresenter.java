package com.sonam.apps.googleimagesearch.search;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sonam.apps.googleimagesearch.model.Response;
import com.sonam.apps.googleimagesearch.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sonam on 2/11/2018.
 */

class ImagesPresenter {

    private final ImagesContractor.View imagesView;

    ImagesPresenter(ImagesContractor.View imagesView) {
        this.imagesView = imagesView;
    }


    void getImages(String query, int page) {
        RetrofitClient.getApi().getImages(query, page, 10).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                try {

                    if (response.raw().cacheResponse() != null) {
                        Log.d("cache", response.raw().cacheResponse().toString());
                    }

                    if (response.raw().networkResponse() != null) {
                        Log.d("network", response.raw().networkResponse().toString());
                    }
                    Response result = response.body();
                    if (result != null) {
                        imagesView.showImages(result.getImages());
                    }
                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                try {
                    throw new InterruptedException("Error");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
