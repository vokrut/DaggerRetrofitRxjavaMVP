package com.kengoweb.daggerretrofitrxjavamvp.twitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.kengoweb.daggerretrofitrxjavamvp.MyApplication;
import com.kengoweb.daggerretrofitrxjavamvp.R;
import com.kengoweb.daggerretrofitrxjavamvp.billing.BillingManager;
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

    private BillingManager mBillingManager;
    private BillingUpdateListener mBillingUpdateListener;
    private SkuDetails mSkuDetailsNoadsItem;
    private Purchase mTestPurchase;

    @Inject
    TwitchAPI twitchAPI;

    private class BillingUpdateListener implements BillingManager.BillingUpdatesListener {
        @Override
        public void onBillingClientSetupFinished() {
        }

        @Override
        public void onConsumeFinished() {
        }

        @Override
        public void onPurchasesUpdated() {
        }

        @Override
        public void onGetSkuDetailsTestItem(SkuDetails skuDetailsTestItem) {
            Log.d(TAG, "BillingUpdateListener:onGetSkuDetailsTestItem: price=" + skuDetailsTestItem.getOriginalPrice());
            mSkuDetailsNoadsItem = skuDetailsTestItem;
        }

        @Override
        public void updateUserPurchase(Purchase purchase) {
            Log.d(TAG, "BillingUpdateListener:updateUserPurchase: purchase=" + purchase.getSku());
            mTestPurchase = purchase;
        }
    }

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

        mBillingUpdateListener = new BillingUpdateListener();
        mBillingManager = new BillingManager(this, mBillingUpdateListener);

        Button btnBuyNoAds = findViewById(R.id.btn_buy_noads);
        btnBuyNoAds.setOnClickListener(v -> {
            if (mSkuDetailsNoadsItem != null) {
                mBillingManager.buyItem(mSkuDetailsNoadsItem);
            }
        });

        Button btnConsumeNoAds = findViewById(R.id.btn_consume_noads);
        btnConsumeNoAds.setOnClickListener(v -> {
            if (mTestPurchase != null) {
                mBillingManager.consumeItem(mTestPurchase);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBillingManager.startConnectionToBilling();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBillingManager.destroy();
    }
}
