package com.kengoweb.daggerretrofitrxjavamvp.billing;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient.BillingResponseCode;
import com.android.billingclient.api.BillingClient.SkuType;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by vokrut on 2019-07-31.
 */

public class BillingManager implements PurchasesUpdatedListener {

    private static final String TAG = BillingManager.class.getSimpleName();

    private static final String SKU_TEST_ITEM_ID = "kengoweb.com.test.noads";

    private BillingClient mBillingClient;

    private SkuDetails mSkuDetailsTestItem = null;
    private final BillingUpdatesListener mBillingUpdatesListener;

    private final Activity mActivity;

    private final List<Purchase> mPurchases = new ArrayList<>();

    public interface BillingUpdatesListener {
        void onBillingClientSetupFinished();
        void onConsumeFinished();
        void onPurchasesUpdated();
        void onGetSkuDetailsTestItem(SkuDetails mSkuDetailsTestItem);
        void updateUserPurchase(Purchase purchase);
    }

    public BillingManager(Activity activity, final BillingUpdatesListener updatesListener) {
        Log.d(TAG, "Creating Billing client.");
        mActivity = activity;
        mBillingUpdatesListener = updatesListener;
        mBillingClient = BillingClient.newBuilder(mActivity).enablePendingPurchases().setListener(this).build();
    }

    public void startConnectionToBilling() {
        Log.d(TAG, "startConnectionToBilling: ");
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                Log.d(TAG, "startConnectionToBilling: onBillingSetupFinished: billingResponseCode=" + billingResult.getResponseCode());
                if (billingResult.getResponseCode() == BillingResponseCode.OK) {
                    Log.d(TAG, "startConnectionToBilling: startConnectionToBilling: The billing client is ready. ");
                    mBillingUpdatesListener.onBillingClientSetupFinished();
                    getSkuList();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.d(TAG, "startConnectionToBilling: onBillingServiceDisconnected: ");
            }
        });
    }

    public void getSkuList() {
        Log.d(TAG, "getSkuList: ");
        List<String> skuList = new ArrayList<>();
        skuList.add(SKU_TEST_ITEM_ID);
        SkuDetailsParams.Builder skuDetailsParams = SkuDetailsParams.newBuilder();
        skuDetailsParams.setSkusList(skuList).setType(SkuType.INAPP);
        mBillingClient.querySkuDetailsAsync(skuDetailsParams.build(),
                (billingResult, skuDetailsList) -> {
                    Log.d(TAG, "getSkuList: billingResponseCode=" + billingResult.getResponseCode());
                    if (skuDetailsList != null) {
                        Log.d(TAG, "getSkuList: skuDetailsList length=" + skuDetailsList.size());
                        if (billingResult.getResponseCode() == BillingResponseCode.OK) {
                            for (SkuDetails skuDetails : skuDetailsList) {
                                Log.d(TAG, "getSkuList: skuDetails=" + skuDetails);
                                String sku = skuDetails.getSku();
                                if (SKU_TEST_ITEM_ID.equals(sku)) {
                                    mSkuDetailsTestItem = skuDetails;
                                    mBillingUpdatesListener.onGetSkuDetailsTestItem(mSkuDetailsTestItem);
                                }
                            }
                        }
                    }
                    getUserPurchases();
                });
    }

    private void getUserPurchases() {
        Log.d(TAG, "getUserPurchases: ");
        List<Purchase> purchasesList = mBillingClient.queryPurchases(SkuType.INAPP).getPurchasesList();
        for (Purchase purchase : purchasesList) {
            if (purchase.getSku().equals(mSkuDetailsTestItem.getSku())) {
                Log.d(TAG, "getUserPurchases: test item is purchased");
                mBillingUpdatesListener.updateUserPurchase(purchase);
            }
        }
        Log.d(TAG, "getUserPurchases: purchasesList=" + purchasesList);
    }

    public void buyItem(SkuDetails mSkuDetailsItem) {
        Log.d(TAG, "buyItem: mSkuDetailsItem=" + mSkuDetailsItem);
        if (mSkuDetailsItem == null) {
            return;
        }
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(mSkuDetailsItem)
                .build();
        BillingResult billingResult = mBillingClient.launchBillingFlow(mActivity, billingFlowParams);
        Log.d(TAG, "buyItem: billingResult=" + billingResult.getResponseCode());
    }

    public void consumeItem(Purchase purchase) {
        Log.d(TAG, "consumeItem: purchase=" + purchase.getSku());
        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .setDeveloperPayload(purchase.getDeveloperPayload())
                .build();
        mBillingClient.consumeAsync(consumeParams, (billingResult, purchaseToken) -> {
            Log.d(TAG, "consumeItem: onConsumeResponse: billingResult=" + billingResult.getResponseCode() + " purchaseToken=" + purchaseToken);
            if (billingResult.getResponseCode() == BillingResponseCode.ITEM_NOT_OWNED) {
                Log.d(TAG, "consumeItem: onConsumeResponse: Failure to consume since item is not owned");
                Toast.makeText(mActivity, "Failure to consume since item is not owned", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        Log.d(TAG, "onPurchasesUpdated: billingResult=" + billingResult.getResponseCode());
        int responseCode = billingResult.getResponseCode();
        if (responseCode == BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (responseCode == BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "onPurchasesUpdated: Handle an error caused by a user cancelling the purchase flow.");
            Toast.makeText(mActivity, "Subscription not completed. You've canceled your subscription's process.", Toast.LENGTH_SHORT).show();
        } else if (responseCode == BillingResponseCode.ITEM_ALREADY_OWNED) {
            Log.d(TAG, "onPurchasesUpdated: Item already owned.");
            Toast.makeText(mActivity, "Subscription not completed. Item already owned.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "onPurchasesUpdated: Handle any other error codes.");
            Toast.makeText(mActivity, "Subscription not completed. Something goes wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePurchase(Purchase purchase) {
        Log.d(TAG, "handlePurchase: purchase=" + purchase.getSku());
    }

    public void destroy() {
        Log.d(TAG, "Destroying the manager.");

        if (mBillingClient != null && mBillingClient.isReady()) {
            mBillingClient.endConnection();
            mBillingClient = null;
        }
    }
}
