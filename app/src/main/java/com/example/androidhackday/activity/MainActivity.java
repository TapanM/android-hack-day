package com.example.androidhackday.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.androidhackday.R;
import com.example.androidhackday.adapter.PriceAdapter;
import com.example.androidhackday.api.ApiManager;
import com.example.androidhackday.databinding.ActivityMainBinding;
import com.example.androidhackday.listener.ApiManagerListener;
import com.example.androidhackday.model.SupportedCoins;
import com.example.androidhackday.model.Crypto;
import com.example.androidhackday.utils.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("cryptoPrices");
    }

    public native String getLogsData(String symbol, String priceUsd);

    private ActivityMainBinding mBinding;
    private final Handler mHandler = new Handler();
    private final List<SupportedCoins> mCryptoSupportedCoinsList = new ArrayList<>();
    private String mCryptoCoinIds = "";
    private PriceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        setToolbar();
        setRecyclerView();
        getCryptoCoins();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setToolbar() {

        mBinding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorTitanWhite));
        mBinding.toolbar.setTitle(getResources().getString(R.string.crypto));
        mBinding.toolbar.setOverflowIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_more_vert_24));
        setSupportActionBar(mBinding.toolbar);
    }

    private void setRecyclerView() {

        mCryptoSupportedCoinsList.addAll(Arrays.asList(SupportedCoins.values()));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recyclerView.setLayoutManager(llm);

        mAdapter = new PriceAdapter(this, mCryptoSupportedCoinsList);
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void getCryptoCoins() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            if (mCryptoSupportedCoinsList != null) {

                mCryptoCoinIds = mCryptoSupportedCoinsList.stream()
                        .map(SupportedCoins::getId)
                        .collect(Collectors.joining(","));
            }
        } else {
            StringBuilder idStringBuilder = new StringBuilder();
            String SEPARATOR = ",";
            for (SupportedCoins coin : mCryptoSupportedCoinsList) {
                idStringBuilder.append(coin.getId());
                idStringBuilder.append(SEPARATOR);
            }
            mCryptoCoinIds = idStringBuilder.toString();
            mCryptoCoinIds = mCryptoCoinIds.substring(0, mCryptoCoinIds.length() - SEPARATOR.length());
        }
    }

    private void getCryptoPricesApi() {

        ApiManager.getInstance().getCryptoPrices(mCryptoCoinIds, new ApiManagerListener() {
            @Override
            public void onSuccess(Object response) {
                if (response instanceof Crypto) {
                    Crypto crypto = (Crypto) response;
                    for(SupportedCoins coin : mCryptoSupportedCoinsList) {

                        if(Objects.equals(coin.getId(), SupportedCoins.BTC.getId())) {

                            String latestPriceUsd = AppUtils.getFormattedPrice(crypto.getBitcoin().getUsd());
                            if(isAnyPriceChanged(coin, latestPriceUsd, null)) {
                                mAdapter.notifyItemChanged(0);
                            }

                            sendToNative(coin);

                        } else if(Objects.equals(coin.getId(), SupportedCoins.ETH.getId())) {

                            String latestPriceUsd = AppUtils.getFormattedPrice(crypto.getEthereum().getUsd());
                            String latestPriceBtc = AppUtils.getFormattedPrice(crypto.getEthereum().getBtc());
                            if(isAnyPriceChanged(coin, latestPriceUsd, latestPriceBtc)) {
                                mAdapter.notifyItemChanged(1);
                            }

                            sendToNative(coin);

                        } else if(Objects.equals(coin.getId(), SupportedCoins.BNB.getId())) {

                            String latestPriceUsd = AppUtils.getFormattedPrice(crypto.getBinanceCoin().getUsd());
                            String latestPriceBtc = AppUtils.getFormattedPrice(crypto.getBinanceCoin().getBtc());
                            if(isAnyPriceChanged(coin, latestPriceUsd, latestPriceBtc)) {
                                mAdapter.notifyItemChanged(2);
                            }

                            sendToNative(coin);

                        } else if(Objects.equals(coin.getId(), SupportedCoins.BAT.getId())) {

                            String latestPriceUsd = AppUtils.getFormattedPrice(crypto.getBasicAttentionToken().getUsd());
                            String latestPriceBtc = AppUtils.getFormattedPrice(crypto.getBasicAttentionToken().getBtc());
                            if(isAnyPriceChanged(coin, latestPriceUsd, latestPriceBtc)) {
                                mAdapter.notifyItemChanged(3);
                            }
                            sendToNative(coin);
                        }
                    }
                }
                periodicallyApiCall();
            }

            @Override
            public void onError(Object error) {
                periodicallyApiCall();
            }
        });
    }

    private boolean isAnyPriceChanged(SupportedCoins coin, String latestPriceUsd, String latestPriceBtc) {
        boolean isAnyPriceChanged = false;
        if(!latestPriceUsd.equalsIgnoreCase(coin.getUsd())) {
            coin.setUsd(latestPriceUsd);
            isAnyPriceChanged = true;
        }
        if(latestPriceBtc!=null && !latestPriceBtc.equalsIgnoreCase(coin.getBtc())) {
            coin.setBtc(latestPriceBtc);
            isAnyPriceChanged = true;
        }
        return isAnyPriceChanged;
    }

    private void sendToNative(SupportedCoins coin) {
        String logData = getLogsData(coin.getSymbol(), coin.getUsd());
        AppUtils.writeToFile(MainActivity.this, logData);
    }

    private void periodicallyApiCall() {

        mHandler.postDelayed(this::getCryptoPricesApi, 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCryptoPricesApi();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHandler.removeCallbacksAndMessages(null);
    }
}