package com.andoiddevop.face2face.network;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static  Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()                                                        //i did mistake in http its http's'
                    .baseUrl("https://fcm.googleapis.com/fcm/")                                     //used to pass messages from your app server to client apps
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
