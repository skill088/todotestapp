package com.projects.vkotov.todotestapp.model.api;

import android.content.Context;

import com.projects.vkotov.todotestapp.model.dto.ApiResponse;
import com.projects.vkotov.todotestapp.model.dto.LoginDTO;
import com.projects.vkotov.todotestapp.model.dto.TodoListDTO;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by skill on 28.02.2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("todo.php?action=sign_in")
    Observable<LoginDTO> login(@Field("login") String email, @Field("password") String pass);

    @GET("todo.php?action=get_todo_list")
    Observable<TodoListDTO> getTodoList(@Query("page") int page, @Query("token") String token);

    @GET("todo.php?action=sign_out")
    Observable<ApiResponse> signOut(@Query("token") String token);
}
