package com.example.nearplace.apiInterface;


import com.example.nearplace.model.loginResponse.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> getUser(@Field("post_email") String email, @Field("post_pass") String pass);

    @FormUrlEncoded
    @POST("add_user.php")
    Call<String> addUser(@Field("email") String email, @Field("pass") String pass,@Field("name") String name);


}
