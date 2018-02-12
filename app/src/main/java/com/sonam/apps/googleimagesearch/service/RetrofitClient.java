package com.sonam.apps.googleimagesearch.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sonam.apps.googleimagesearch.BuildConfig;
import com.sonam.apps.googleimagesearch.utils.CheckNetwork;
import com.sonam.apps.googleimagesearch.CustomApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by sonam on 2/11/2018.
 */

public class RetrofitClient {

    private static String BASE_URL = "https://www.googleapis.com/";
    private static String API_KEY = "AIzaSyDO4VRY7aTLod6Y8ywgAqJoI3jlxxy7uPA";
    private static String CX = "004384860389036192757:ufstntnjgn0";
    private static String SEARCH_TYPE = "image";
    private static String IMAGE_SIZE = "small";

    private static final String CACHE_CONTROL = "Cache-Control";


    private static Retrofit retrofit = null;

    public static CustomSearchEngineService getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build();
        }
        return retrofit.create(CustomSearchEngineService.class);
    }

    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .addInterceptor(provideParameterInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache())
                .build();
    }

    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(CustomApplication.getAppContext().getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e("cache", "Could not create Cache!");
        }
        return cache;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("logging", message.toString());
                    }
                });
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);
        return httpLoggingInterceptor;
    }


    public static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder().removeHeader("pragma")
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!CheckNetwork.isAvailable(CustomApplication.getAppContext())) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    private static Interceptor provideParameterInterceptor() {

        // Define the interceptor, add authentication headers
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl requestHttpUrl = request.url();

                HttpUrl url = requestHttpUrl.newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .addQueryParameter("cx", CX)
                        .addQueryParameter("searchType", SEARCH_TYPE)
                        .addQueryParameter("imageSize", IMAGE_SIZE)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = request.newBuilder()
                        .url(url);
                return chain.proceed(requestBuilder.build());
            }
        };
    }

}
