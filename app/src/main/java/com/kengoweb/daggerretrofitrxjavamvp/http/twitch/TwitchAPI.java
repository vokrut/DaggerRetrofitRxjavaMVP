package com.kengoweb.daggerretrofitrxjavamvp.http.twitch;

import com.kengoweb.daggerretrofitrxjavamvp.http.twitch.apimodel.Twitch;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by vokrut on 2019-04-19.
 */

public interface TwitchAPI {

    @GET("games/top")
    Call<Twitch> getTopGames(@Header("Client-Id") String clientId);

    @GET("games/top")
    Observable<Twitch> getTopGamesObservable(@Header("Client-Id") String clientId);
}
