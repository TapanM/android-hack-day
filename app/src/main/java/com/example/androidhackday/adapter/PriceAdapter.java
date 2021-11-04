package com.example.androidhackday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhackday.R;
import com.example.androidhackday.databinding.ItemPriceAdapterBinding;
import com.example.androidhackday.model.SupportedCoins;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {

    private final Context context;
    private final List<SupportedCoins> coinsList;

    public PriceAdapter(Context context, List<SupportedCoins> coinsList) {
        this.context = context;
        this.coinsList = coinsList;
    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PriceAdapter.ViewHolder holder, final int position) {

        SupportedCoins coin = coinsList.get(position);

        if(Objects.equals(coin.getId(), SupportedCoins.BTC.getId())) {
            holder.itemPriceAdapterBinding.ivIcon.setImageResource(R.drawable.ic_btc);
        } else if(Objects.equals(coin.getId(), SupportedCoins.ETH.getId())) {
            holder.itemPriceAdapterBinding.ivIcon.setImageResource(R.drawable.ic_eth);
        } else if(Objects.equals(coin.getId(), SupportedCoins.BNB.getId())) {
            holder.itemPriceAdapterBinding.ivIcon.setImageResource(R.drawable.ic_bnb);
        } else if(Objects.equals(coin.getId(), SupportedCoins.BAT.getId())) {
            holder.itemPriceAdapterBinding.ivIcon.setImageResource(R.drawable.ic_bat);
        }

        holder.itemPriceAdapterBinding.tvName.setText(coin.getName());
        if(coin.getUsd()!=null) {
            holder.itemPriceAdapterBinding.tvPriceUsd.setText(context.getString(R.string.usd_price, coin.getUsd()));
        }
        if(coin.getBtc()!=null && !Objects.equals(coin.getId(), SupportedCoins.BTC.getId())) {
            holder.itemPriceAdapterBinding.tvPriceBtc.setText(context.getString(R.string.btc_price, coin.getBtc()));
        }
    }

    @NonNull
    @Override
    public PriceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPriceAdapterBinding viewBinding = ItemPriceAdapterBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(viewBinding);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemPriceAdapterBinding itemPriceAdapterBinding;

        public ViewHolder(ItemPriceAdapterBinding viewBinding) {
            super(viewBinding.getRoot());
            this.itemPriceAdapterBinding = viewBinding;
        }
    }
}