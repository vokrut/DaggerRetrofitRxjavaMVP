package com.kengoweb.daggerretrofitrxjavamvp.twitch;

import com.kengoweb.daggerretrofitrxjavamvp.http.twitch.TwitchAPI;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vokrut on 2019-04-19.
 */

@Module
public class TwitchModule {

    public final String BASE_URL = "https://api.twitch.tv/kraken/";

    @Provides
    public OkHttpClient provideClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    public Retrofit provideRetrofit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public TwitchAPI provideTwitchApiService() {
        return provideRetrofit(BASE_URL, provideClient()).create(TwitchAPI.class);
    }

}
