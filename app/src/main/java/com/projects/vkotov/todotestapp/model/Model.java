package com.projects.vkotov.todotestapp.model;

import com.projects.vkotov.todotestapp.model.dto.LoginDTO;
import com.projects.vkotov.todotestapp.model.dto.TodoListDTO;

import io.reactivex.Observable;

/**
 * Created by skill on 27.02.2018.
 */

public interface Model {

    Observable<LoginDTO> login(String login, String password);

    Observable<TodoListDTO> getTodoList(int page);
}
