package com.example.androidhackday.model;

import com.google.gson.annotations.SerializedName;

public class Crypto {

    @SerializedName("bitcoin")
    private Price bitcoin;

    @SerializedName("ethereum")
    private Price ethereum;

    @SerializedName("binancecoin")
    private Price binanceCoin;

    @SerializedName("basic-attention-token")
    private Price basicAttentionToken;

    public Price getBasicAttentionToken() {
        return basicAttentionToken;
    }

    public void setBasicAttentionToken(Price basicAttentionToken) {
        this.basicAttentionToken = basicAttentionToken;
    }

    public Price getBinanceCoin() {
        return binanceCoin;
    }

    public void setBinanceCoin(Price binanceCoin) {
        this.binanceCoin = binanceCoin;
    }

    public Price getEthereum() {
        return ethereum;
    }

    public void setEthereum(Price ethereum) {
        this.ethereum = ethereum;
    }

    public Price getBitcoin() {
        return bitcoin;
    }

    public void setBitcoin(Price bitcoin) {
        this.bitcoin = bitcoin;
    }
}
