package com.devblocks42.vaultchat.repositories;

import com.devblocks42.vaultchat.dto.RegisterRequest;
import com.devblocks42.vaultchat.dto.RegisterResponse;
import com.devblocks42.vaultchat.network.ApiClient;
import com.devblocks42.vaultchat.network.ApiService;

import retrofit2.Callback;

public class AuthRepository {

    private final ApiService api;

    public AuthRepository() {
        api = ApiClient.getClient().create(ApiService.class);
    }

    public void register(RegisterRequest request, Callback<RegisterResponse> callback) {
        api.register(request).enqueue(callback);
    }
}