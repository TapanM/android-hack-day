package com.example.androidhackday.api;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.example.androidhackday.listener.ApiManagerListener;
import com.example.androidhackday.model.Crypto;
import com.example.androidhackday.model.SupportedCurrencies;
import com.example.androidhackday.utils.AppConstants;
import com.example.androidhackday.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ApiManager {

    private ApiInterface mApiInterface = null;

    private ApiManager() { }

    public static ApiManager getInstance() {
        return ApiManagerSingleton.INSTANCE;
    }

    private final String mSupportedCurrencies = TextUtils.join(",", AppUtils.getNames(SupportedCurrencies.class));

    private ApiInterface getService() {

        if (mApiInterface == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mApiInterface = retrofit.create(ApiInterface.class);
        }

        return mApiInterface;
    }

    private static class ApiManagerSingleton {
        private static final ApiManager INSTANCE = new ApiManager();
    }

    public void getCryptoPrices(@NotNull String cryptoIds, @NotNull ApiManagerListener apiManagerListener) {
        Call<Crypto> call = getService().getCryptoPrices(cryptoIds, mSupportedCurrencies);
        call.enqueue(new Callback<Crypto>() {
            @Override
            public void onResponse(@NotNull Call<Crypto> call, @NotNull Response<Crypto> response) {

                if (response.isSuccessful()) apiManagerListener.onSuccess(response.body());
                else apiManagerListener.onError(response);
            }

            @Override
            public void onFailure(@NotNull Call<Crypto> call, @NotNull Throwable throwable) {
                apiManagerListener.onError(throwable);
            }
        });
    }
}
