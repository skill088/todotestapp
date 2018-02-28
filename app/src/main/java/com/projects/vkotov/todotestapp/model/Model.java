package com.projects.vkotov.todotestapp.model;

import com.projects.vkotov.todotestapp.model.data.Login;

import io.reactivex.Observable;

/**
 * Created by skill on 27.02.2018.
 */

public interface Model {

    Observable<Login> login(String login, String password);
}
