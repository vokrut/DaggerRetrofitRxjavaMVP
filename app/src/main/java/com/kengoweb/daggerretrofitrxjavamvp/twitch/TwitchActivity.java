package com.kengoweb.daggerretrofitrxjavamvp.twitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kengoweb.daggerretrofitrxjavamvp.MyApplication;
import com.kengoweb.daggerretrofitrxjavamvp.R;
import com.kengoweb.daggerretrofitrxjavamvp.http.twitch.TwitchAPI;
import com.kengoweb.daggerretrofitrxjavamvp.http.twitch.apimodel.Top;
import com.kengoweb.daggerretrofitrxjavamvp.http.twitch.apimodel.Twitch;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwitchActivity extends AppCompatActivity {

    private static final String TAG = TwitchActivity.class.getSimpleName();

    private static final String TWITCH_CLIENT_ID = "iunfgd5jxxf2hb4um2in67dqkfp8qp";
    private static final String TWITCH_CLIENT_SECRET = "l2355uy73a2yckdc0sgyzgfyhkhe3x";

    @Inject
    TwitchAPI twitchAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitch);

        ((MyApplication) getApplication()).getComponent().inject(this);

        Call<Twitch> call = twitchAPI.getTopGames(TWITCH_CLIENT_ID);

        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Top> gameList = response.body().getTop();
                Log.d(TAG, "getTopGames: " + gameList.size());
                for (Top top : gameList) {
                    Log.d(TAG, "getTopGames: " + top.getGame().getName());
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
