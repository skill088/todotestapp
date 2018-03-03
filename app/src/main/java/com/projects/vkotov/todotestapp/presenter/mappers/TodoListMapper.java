package com.projects.vkotov.todotestapp.presenter.mappers;

import com.projects.vkotov.todotestapp.model.dto.TodoDTO;
import com.projects.vkotov.todotestapp.presenter.vo.Todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by skill on 01.03.2018.
 */

public class TodoListMapper implements Function<List<TodoDTO>, List<Todo>> {

    @Inject
    public TodoListMapper() {}

    @Override
    public List<Todo> apply(List<TodoDTO> todoDTO) throws Exception {

        if (todoDTO == null)
            return null;

        return Observable.fromIterable(todoDTO)
                .map(todoDTO1 -> {
                    Todo todo = new Todo();
                    todo.setId(todoDTO1.getId());
                    todo.setContent(todoDTO1.getContent());
                    todo.setIsCompleted(todoDTO1.getIsCompleted());
                    String[] temp = todoDTO1.getCreatedAt().split("-");
                    todo.setCreatedAt(Long.parseLong(temp[0])*31536000000L +
                            Long.parseLong(temp[1])*2592000000L + Long.parseLong(temp[2])*86400000L);
                    temp = todoDTO1.getUpdatedAt().split("-");
                    todo.setUpdatedAt(Long.parseLong(temp[0])*31536000000L +
                            Long.parseLong(temp[1])*2592000000L + Long.parseLong(temp[2])*86400000L);
                    todo.setUserId(todoDTO1.getUserId());
                    return todo;
                })
                .toList()
                .toObservable()
                .map(ArrayList::new)
                .blockingFirst();
    }
}
