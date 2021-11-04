package com.example.androidhackday.model;

import java.math.BigDecimal;

public class Price {

    private BigDecimal usd;
    private BigDecimal btc;

    public BigDecimal getUsd() {
        return usd;
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    public BigDecimal getBtc() {
        return btc;
    }

    public void setBtc(BigDecimal btc) {
        this.btc = btc;
    }
}
