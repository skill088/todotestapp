package com.projects.vkotov.todotestapp.model;

import com.projects.vkotov.todotestapp.model.dto.LoginDTO;

import io.reactivex.Observable;

/**
 * Created by skill on 27.02.2018.
 */

public interface Model {

    Observable<LoginDTO> login(String login, String password);
}
