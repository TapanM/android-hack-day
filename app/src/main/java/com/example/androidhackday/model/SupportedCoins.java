package com.example.androidhackday.model;

public enum SupportedCoins {

    BTC("bitcoin", "btc", "Bitcoin"),
    ETH("ethereum", "eth", "Ethereum"),
    BNB("binancecoin", "bnb", "Binance Coin"),
    BAT("basic-attention-token", "bat", "Basic Attention Token");

    private final String id;
    private final String symbol;
    private final String name;
    private String usd;
    private String btc;

    SupportedCoins(String id, String symbol, String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getUsd() {
        return usd;
    }

    public String getBtc() {
        return btc;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public void setBtc(String btc) {
        this.btc = btc;
    }
}
