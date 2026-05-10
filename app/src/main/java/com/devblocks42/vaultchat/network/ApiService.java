package com.devblocks42.vaultchat.network;

import com.devblocks42.vaultchat.dto.RegisterRequest;
import com.devblocks42.vaultchat.dto.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/users/register")
    Call<RegisterResponse> register(
            @Body RegisterRequest request
    );

}
