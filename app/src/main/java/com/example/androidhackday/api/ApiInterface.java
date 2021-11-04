package com.example.androidhackday.api;

import com.example.androidhackday.model.Crypto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("simple/price")
    Call<Crypto> getCryptoPrices(@Query("ids") String cryptoIds, @Query("vs_currencies") String vsCurrencies);
}
