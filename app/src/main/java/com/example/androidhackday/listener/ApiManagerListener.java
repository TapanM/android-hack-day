package com.example.androidhackday.listener;

public interface ApiManagerListener {
    void onSuccess(Object response);
    void onError(Object error);
}
