package com.example.pigalev;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("user/login")
    Call<MaskUser> createUser(@Body ModelSendUser modelSendUser);
}
