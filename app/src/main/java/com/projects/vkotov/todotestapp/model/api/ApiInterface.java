package com.projects.vkotov.todotestapp.model.api;

import com.projects.vkotov.todotestapp.model.dto.LoginDTO;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by skill on 28.02.2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("todo.php?action=sign_in")
    Observable<LoginDTO> login(@Field("login") String email, @Field("password") String pass);
}
