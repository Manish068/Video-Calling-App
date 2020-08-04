package com.andoiddevop.face2face.network;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiService {

    @POST("send")
    Call<String> sendRemoteMessages(
            @HeaderMap HashMap<String, String> header,
            @Body String remoteBody
            );
}
