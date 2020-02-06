package com.saaty.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private  static ApiServiceInterface retrofit=null;

    public static ApiServiceInterface getClientService() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();

           Retrofit retrofit1 = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(urls.base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
           retrofit=retrofit1.create(ApiServiceInterface.class);

        }
        return retrofit;
    }

}
